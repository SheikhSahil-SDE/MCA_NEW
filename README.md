# 1. Create a UDP client on a node n1 and a UDP server on a node n2.
```
#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"

using namespace ns3;

int main(int argc, char *argv[])
{
    // Enable logging (shows packet sends/receives in terminal)
    LogComponentEnable("UdpEchoClientApplication", LOG_LEVEL_INFO);
    LogComponentEnable("UdpEchoServerApplication", LOG_LEVEL_INFO);

    // Create 2 nodes: n1 (client), n2 (server)
    NodeContainer nodes;
    nodes.Create(2);

    // Create a point-to-point link
    PointToPointHelper pointToPoint;
    pointToPoint.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    pointToPoint.SetChannelAttribute("Delay", StringValue("2ms"));

    NetDeviceContainer devices;
    devices = pointToPoint.Install(nodes);

    // Install Internet stack
    InternetStackHelper stack;
    stack.Install(nodes);

    // Assign IP addresses
    Ipv4AddressHelper address;
    address.SetBase("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    // Create and install UDP server on node n2
    uint16_t port = 9;          // UDP port
    UdpEchoServerHelper echoServer(port);

    ApplicationContainer serverApps = echoServer.Install(nodes.Get(1)); // n2
    serverApps.Start(Seconds(1.0));
    serverApps.Stop(Seconds(10.0));

    // Create and install UDP client on node n1
    UdpEchoClientHelper echoClient(interfaces.GetAddress(1), port);
    echoClient.SetAttribute("MaxPackets", UintegerValue(5));
    echoClient.SetAttribute("Interval", TimeValue(Seconds(1.0)));
    echoClient.SetAttribute("PacketSize", UintegerValue(1024));

    ApplicationContainer clientApps = echoClient.Install(nodes.Get(0)); // n1
    clientApps.Start(Seconds(2.0));
    clientApps.Stop(Seconds(10.0));

    // Enable routing
    Ipv4GlobalRoutingHelper::PopulateRoutingTables();

    // Run simulation
    Simulator::Run();
    Simulator::Destroy();

    return 0;
}
```


| Component | Description |
---------------------------
| NodeContainer nodes.Create(2) | Creates n1 and n2 |
------------------------------------------------------
PointToPointHelper | Creates a wired link (5 Mbps, 2 ms delay)
---------------------------------------------------------------
UdpEchoServerHelper | Installs UDP server on n2
----------------------------------------------
UdpEchoClientHelper | Installs UDP client on n1
----------------------------------------------
interfaces.GetAddress(1) | IP of server node (n2)
-----------------------------------------
Simulator::Run() | Starts simulation

Logging | Shows send/receive info in terminal



```
Output:
cd ns-3.xx
./ns3 run scratch/udp_client_server.cc

At time 2s client sent 1024 bytes to 10.1.1.2 port 9
At time 2.003s server received 1024 bytes from 10.1.1.1 port 49153
At time 2.003s server sent 1024 bytes to 10.1.1.1 port 49153
At time 2.006s client received 1024 bytes from 10.1.1.2 port 9
```













# 2. Start a TCP application and monitor the packet flow. Make necessary assumptions.

```
#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include "ns3/flow-monitor-module.h"

using namespace ns3;

int main(int argc, char *argv[])
{
    // Enable TCP logging (optional)
    LogComponentEnable("BulkSendApplication", LOG_LEVEL_INFO);
    LogComponentEnable("PacketSink", LOG_LEVEL_INFO);

    // Create two nodes: n1 (client), n2 (server)
    NodeContainer nodes;
    nodes.Create(2);

    // Create a point-to-point channel between the two nodes
    PointToPointHelper pointToPoint;
    pointToPoint.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    pointToPoint.SetChannelAttribute("Delay", StringValue("2ms"));

    NetDeviceContainer devices;
    devices = pointToPoint.Install(nodes);

    // Install Internet stack (TCP/IP)
    InternetStackHelper stack;
    stack.Install(nodes);

    // Assign IP addresses
    Ipv4AddressHelper address;
    address.SetBase("10.1.1.0", "255.255.255.0");

    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    // Create a TCP server (PacketSink)
    uint16_t port = 9;
    Address sinkAddress(InetSocketAddress(interfaces.GetAddress(1), port));
    PacketSinkHelper sinkHelper("ns3::TcpSocketFactory", InetSocketAddress(Ipv4Address::GetAny(), port));
    ApplicationContainer sinkApp = sinkHelper.Install(nodes.Get(1));
    sinkApp.Start(Seconds(0.0));
    sinkApp.Stop(Seconds(10.0));

    // Create a TCP client (BulkSendApplication)
    BulkSendHelper source("ns3::TcpSocketFactory", sinkAddress);
    source.SetAttribute("MaxBytes", UintegerValue(0)); // Unlimited data
    ApplicationContainer sourceApp = source.Install(nodes.Get(0));
    sourceApp.Start(Seconds(1.0));
    sourceApp.Stop(Seconds(10.0));

    // Enable routing
    Ipv4GlobalRoutingHelper::PopulateRoutingTables();

    // Install FlowMonitor to monitor packet flow
    FlowMonitorHelper flowmon;
    Ptr<FlowMonitor> monitor = flowmon.InstallAll();

    // Run the simulation
    Simulator::Stop(Seconds(11.0));
    Simulator::Run();

    // Print flow statistics
    monitor->CheckForLostPackets();
    Ptr<Ipv4FlowClassifier> classifier = DynamicCast<Ipv4FlowClassifier>(flowmon.GetClassifier());
    std::map<FlowId, FlowMonitor::FlowStats> stats = monitor->GetFlowStats();

    for (auto &flow : stats)
    {
        Ipv4FlowClassifier::FiveTuple t = classifier->FindFlow(flow.first);
        std::cout << "Flow ID: " << flow.first << " (" << t.sourceAddress << " -> " << t.destinationAddress << ")\n";
        std::cout << "  Tx Packets: " << flow.second.txPackets << "\n";
        std::cout << "  Rx Packets: " << flow.second.rxPackets << "\n";
        std::cout << "  Throughput: " 
                  << (flow.second.rxBytes * 8.0 / (flow.second.timeLastRxPacket.GetSeconds() - flow.second.timeFirstTxPacket.GetSeconds())) / 1024
                  << " Kbps\n";
    }

    // Cleanup
    Simulator::Destroy();
    return 0;
}
```

```
Output:
cd ns-3.xx
./ns3 run scratch/tcp_flow_monitor.cc

Flow ID: 1 (10.1.1.1 -> 10.1.1.2)
  Tx Packets: 360
  Rx Packets: 360
  Throughput: 4900.23 Kbps
```


# 3. Create a UDPClient and UDPServer nodes and communicate at a fixed data rate. Make necessary assumptions.

```
#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"

using namespace ns3;

int main(int argc, char *argv[])
{
    // Enable logs (optional)
    LogComponentEnable("OnOffApplication", LOG_LEVEL_INFO);
    LogComponentEnable("PacketSink", LOG_LEVEL_INFO);

    // 1 Create nodes: n1 = client, n2 = server
    NodeContainer nodes;
    nodes.Create(2);

    // 2  Create a point-to-point channel between them
    PointToPointHelper pointToPoint;
    pointToPoint.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    pointToPoint.SetChannelAttribute("Delay", StringValue("2ms"));

    NetDeviceContainer devices;
    devices = pointToPoint.Install(nodes);

    // 3 Install Internet stack
    InternetStackHelper stack;
    stack.Install(nodes);

    // 4 Assign IP addresses
    Ipv4AddressHelper address;
    address.SetBase("10.1.1.0", "255.255.255.0");
    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    // 5 Create a UDP Server (PacketSink) on n2
    uint16_t port = 9;
    Address sinkAddress(InetSocketAddress(Ipv4Address::GetAny(), port));
    PacketSinkHelper packetSinkHelper("ns3::UdpSocketFactory", sinkAddress);
    ApplicationContainer sinkApp = packetSinkHelper.Install(nodes.Get(1)); // n2
    sinkApp.Start(Seconds(0.0));
    sinkApp.Stop(Seconds(10.0));

    // 6 Create a UDP Client (OnOffApplication) on n1
    OnOffHelper onoff("ns3::UdpSocketFactory", InetSocketAddress(interfaces.GetAddress(1), port));
    onoff.SetAttribute("DataRate", StringValue("1Mbps")); // fixed sending rate
    onoff.SetAttribute("PacketSize", UintegerValue(1024)); // 1 KB packets
    onoff.SetAttribute("OnTime", StringValue("ns3::ConstantRandomVariable[Constant=1]"));
    onoff.SetAttribute("OffTime", StringValue("ns3::ConstantRandomVariable[Constant=0]"));

    ApplicationContainer clientApp = onoff.Install(nodes.Get(0)); // n1
    clientApp.Start(Seconds(1.0));
    clientApp.Stop(Seconds(10.0));

    // 7 Enable routing
    Ipv4GlobalRoutingHelper::PopulateRoutingTables();

    // 8. Run the simulation
    Simulator::Stop(Seconds(11.0));
    Simulator::Run();
    Simulator::Destroy();

    return 0;
}
```
```
cd ns-3.xx
./ns3 run scratch/udp_fixed_rate.cc

At time 1s OnOffApplication StartSending
At time 1.002s PacketSink received 1024 bytes
At time 1.004s PacketSink received 1024 bytes
```


# 4. Create a simple point to point network topology using two nodes. Make necessary assumptions


```
#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"

using namespace ns3;

int main(int argc, char *argv[])
{
    // Enable logging (optional)
    LogComponentEnable("PointToPointNetDevice", LOG_LEVEL_INFO);

    // 1 Create two nodes (n0 and n1)
    NodeContainer nodes;
    nodes.Create(2);

    // 2 Create a point-to-point channel between them
    PointToPointHelper pointToPoint;
    pointToPoint.SetDeviceAttribute("DataRate", StringValue("5Mbps"));  // link speed
    pointToPoint.SetChannelAttribute("Delay", StringValue("2ms"));      // propagation delay

    // 3 Install the devices (NICs) on both nodes
    NetDeviceContainer devices;
    devices = pointToPoint.Install(nodes);

    // 4 Install Internet stack (for IP connectivity)
    InternetStackHelper stack;
    stack.Install(nodes);

    // 5 Assign IP addresses to the interfaces
    Ipv4AddressHelper address;
    address.SetBase("10.1.1.0", "255.255.255.0");

    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    // 6 Print the assigned IP addresses
    std::cout << "Node 0 IP Address: " << interfaces.GetAddress(0) << std::endl;
    std::cout << "Node 1 IP Address: " << interfaces.GetAddress(1) << std::endl;

    // 7 Optional) Enable packet tracing to observe traffic in Wireshark
    pointToPoint.EnablePcapAll("simple_p2p_topology");

    // 8 Run the simulation
    Simulator::Run();
    Simulator::Destroy();

    return 0;
}
```
```

cd ns-3.xx
./ns3 run scratch/simple_p2p_topology.cc


Node 0 IP Address: 10.1.1.1
Node 1 IP Address: 10.1.1.2
```
