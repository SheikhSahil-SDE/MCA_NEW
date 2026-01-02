## 3. MLOps: Automated Pipeline for Real-time AI Model Deployment

In 2025, deploying AI models is a major industry need. This project focuses on the "DevOps for AI" (MLOps) lifecycle.<br><br>
**The Project**: A system that automatically retrains, packages, and deploys a machine learning model (e.g., Fraud Detection or Sentiment Analysis) to Kubernetes.<br><br>
**Workflow**:<br><br>
* **Training**: Jenkins triggers a training job on AWS SageMaker when new data is uploaded to S3.<br>
* **Deployment**: The trained model is containerized with Docker and deployed as a microservice on EKS.<br>
* **A/B Testing**: Use Canary Deployments to roll out new model versions to only 10% of users initially.<br>
* **Monitoring**: Use Prometheus and Grafana to monitor "Model Drift" (performance loss over time).<br>
* **Key Challenge**: Automating the decision-making process to rollback a model if its accuracy falls below a certain percentage in production.<br>


