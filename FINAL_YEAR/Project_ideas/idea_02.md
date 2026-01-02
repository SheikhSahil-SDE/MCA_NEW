## 2. Multi-Cloud Kubernetes Cluster with Disaster Recovery (DR)

Focus on "High Availability" and "Business Continuity," which are critical in production environments.<br>
**The Project**: Deploy a production-ready application across multiple AWS regions or even a hybrid cloud (Ubuntu on-prem + AWS EKS) with automated failover.<br>
**Workflow**:<br>

* **Infrastructure**: Provision VPCs and EKS clusters using Terraform (IaC).<br>
* **Orchestration**: Use a Service Mesh like Istio to manage traffic between regions.<br>
* **State Management**: Sync persistent data across regions using AWS Aurora Global Database or Velero for Kubernetes backups.<br>
* **Key Challenge**: Designing a global AWS Route 53 health check that automatically reroutes traffic to a healthy region during a simulated outage.<br><br>
