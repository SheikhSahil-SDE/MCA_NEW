## 4. Serverless-Kubernetes Hybrid Event-Driven Architecture

Modern applications often mix serverless for spikes and containers for steady workloads.<br>
**The Project**: A real-time data processing engine (e.g., an IoT dashboard or E-commerce order system).<br>

**Workflow**:<br>
* **Event Source**: High-volume data comes into AWS Kinesis or Kafka.
* **Processing**: AWS Lambda handles initial rapid filtering, then sends complex jobs to a long-running worker microservice on Kubernetes.<br>
* **Autoscaling**: Use KEDA (Kubernetes Event-driven Autoscaling) to scale your K8s pods from zero to hundreds based on the number of messages in the queue.<br>
* **Key Challenge**: Managing state and ensuring "Exactly-once" processing across both serverless and containerized environments.<br>
<br>
