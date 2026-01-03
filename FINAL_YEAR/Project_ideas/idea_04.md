## 4. Serverless-Kubernetes Hybrid Event-Driven Architecture

Modern applications often mix serverless for spikes and containers for steady workloads.<br>
**The Project**: A real-time data processing engine (e.g., an IoT dashboard or E-commerce order system).<br>

**Workflow**:<br>
* **Event Source**: High-volume data comes into AWS Kinesis or Kafka.
* **Processing**: AWS Lambda handles initial rapid filtering, then sends complex jobs to a long-running worker microservice on Kubernetes.<br>
* **Autoscaling**: Use KEDA (Kubernetes Event-driven Autoscaling) to scale your K8s pods from zero to hundreds based on the number of messages in the queue.<br>
* **Key Challenge**: Managing state and ensuring "Exactly-once" processing across both serverless and containerized environments.<br>
<br>



# Phase 1: Environment Setup & Infrastructure

Since you are using a Lenovo laptop with 12GB RAM, I recommend using Minikube or Kind for local Kubernetes testing before deploying to AWS EKS to save on cloud costs.<br>

Cloud Provider: Use your AWS account.
Kubernetes Cluster: Setup an Amazon EKS cluster or a local Minikube cluster.<br>
Messaging Hub: Create an Amazon SQS queue (easier for KEDA integration initially than Kinesis) or an Amazon MSK (Kafka) cluster.<br>
Development Tools: Ensure kubectl, helm, terraform (optional but recommended), and the AWS CLI are configured.<br>


# Phase 2: The Serverless Pre-Processor (AWS Lambda)

The goal here is to handle "bursty" traffic and filter noise before it hits your expensive Kubernetes resources.
Trigger: Set up an S3 bucket or an API Gateway that pushes data into AWS Kinesis.<br>

Lambda Function: Write a Python-based Lambda function that:
Consumes records from Kinesis.<br>
Performs "Lightweight Validation" (checking for missing fields).<br>
If valid, pushes the message into an Amazon SQS queue (which will act as the bridge to Kubernetes).

# Phase 3: The Kubernetes Worker (Microservice)

This is your "Steady State" engine that handles complex business logic (e.g., database writes, heavy computations).<br>
Containerize: Create a Python or Node.js application that polls the SQS queue.<br>
Dockerize: Build the image and push it to Amazon ECR.<br>
Deployment: Create a standard Kubernetes Deployment manifest, but set the replicas to 0 initially.

# Phase 4: Event-Driven Autoscaling with KEDA

This is the **"heart"** of your project. KEDA will monitor SQS and tell Kubernetes when to scale your pods.<br>
Install KEDA: Use Helm to install KEDA into your cluster:```
helm install keda kedacore/keda --namespace keda --create-namespace``` <br>
**Define ScaledObject**: Create a ScaledObject YAML file. This tells KEDA:
**Trigger**: Amazon SQS.<br>
**Threshold**: "If there are more than 10 messages in the queue, spin up a new pod."<br>

Scale to Zero: If the queue is empty, terminate all pods to save resources.

# Phase 5: Solving the "Exactly-Once" Challenge
To address your key challenge, you must implement Idempotency.<br>
**Deduplication ID**: When Lambda sends a message to SQS, attach a unique MessageDeduplicationId (if using SQS FIFO) or a UUID in the metadata.<br>
**State Management**: Use Amazon DynamoDB as a "Claims Check" store.<br>
Before the Kubernetes worker processes a job, it checks DynamoDB: "Has ID '123' been processed?"<br>
If yes, discard the message.
If no, process it and mark it as "Complete" in DynamoDB.

# Phase 6: Monitoring & Visualization

As an MCA project, you need a way to prove it works during your demo.
**Dashboard**: Use CloudWatch to show the Lambda execution spikes.
**K8s Monitoring**: Use Lens or the Kubernetes Dashboard to show pods spinning up and down in real-time as you flood the system with data.
**Frontend**: Build a simple React/MERN dashboard that displays the final "Processed Data" from your database.
Suggested Project Timeline

<br>**Week 1**: Infrastructure setup (EKS/Minikube & SQS).
<br>**Week 2**: Lambda development and Kinesis integration.
<br>**Week 3**: Containerizing the Worker and deploying to K8s.
<br>**Week 4**: Implementing KEDA and fine-tuning the Scaling triggers.
<br>**Week 5**: Idempotency logic with DynamoDB and UI Dashboard.