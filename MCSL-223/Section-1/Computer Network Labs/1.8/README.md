# Session-1
1. Create a simple point to point network topology using two nodes.
2. Create a UdpClient and UdpServer nodes and communicate at a fixed data 
rate.

# Session - 2
3. Measure the throughput (end to end) while varying latency in the network 
created in Session -1.
4. Create a simple network topology having two client node on left side and 
two server nodes on the right side. Both clients are connected with another 
node n1. Similarly, both server node connecting to node n2. Also connect 
node n1 and n2 thus forming a dumbbell shape topology. Use point to point 
link only.

# Session - 3 
5. Install a TCP socket instance connecting either of the client node with either 
of the server node in session 2’s network topology.
6. Install a TCP socket instance connecting other remaining client node with 
the remaining server node in session 2’s network topology.
7. Start TCP application and monitor the packet flow.

# Session - 4 
8. Take three nodes n1, n2 and n3 and create a wireless mobile ad-hoc network.
9. Install the optimized Link State Routing protocol on these nodes.

# Session – 5
10. Create a UDP client on a node n1 and a UDP server on a node n2.
11. Send packets to node n2 from node n1 and plot the number of bytes received 
with respect to time at node n2.
12. Show the pcap traces at node n2’s Wi-Fi interface. 

# Session – 6
13. Use 2 nodes to setup a wireless ad-hoc network where nodes are placed at 
a fixed distance in a 3D plane.
14. Install UDP server and Client at these two nodes.
15. Setup a CBR transmission between these nodes.

# Session – 7
16. Setup 4 nodes, two TCP client and server pair and two UDP client and 
server pair.
17. Send packets to respective clients from both the servers.
18. Monitor the traffic for both the pair and plot the no. of bytes received.

# Session – 8
19. Use the setup made in session 2 and monitor the traffic flow, plot the packets 
received.
20. Start the TCP application at Time 1 second.
21. After 20 seconds, start the UDP application at Rate1 which clogs the half of 
the dumbbell bridge capacity.
22. Using ns-3 tracing mechanism, plot the changes in the TCP window size 
over the time.

# Session – 9
23. In the last session 8, Increase the UDP rate at 30 second to Rate2 such that 
it clogs whole of the dumbbell bridge capacity.
24. Use MatPlotlLib or GNUPlot to visualize cwnd vs time, also mention Rate1 
and Rate2. 

# Session - 10
25. Create a point to point network between two nodes with the following 
parameters. Link bandwidth between the two nodes. Default is 5 Mbps. One way delay of the link. Default is 5 milliseconds. Loss rate of packets on the link. Default is 0.000001. (This covers losses other 
than those that occur due to buffer drops at node0.) 
Queue size of the buffer at node 0. Default is 10 packets. 
Simulation time. Default is 10 seconds. 
Calculate the average TCP throughput at the receiver using Wireshark 
application for packet capturing.