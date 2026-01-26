#  Session-9 
# 21. Implement the following Associations using C++/Java.

<img src="https://github.com/SheikhSahil-SDE/MCA_NEW/blob/main/MCSL-222/Section-1/OOAD%20LAB/1.5/Figure%201.16.jpg" alt="Figure 1.16" width=""/>

📌 Understanding the UML Diagram
Classes:
  1. TrainJourney
  2. Train

Association:
  * One Train can be assigned to 0..* journeys
  * A TrainJourney can have 0..1 Train

👉 This is a simple association, NOT aggregation or composition.

🔗 Association Mapping in Code
    * TrainJourney holds a reference to Train
    * Train maintains a list of TrainJourney objects


**✅ JAVA IMPLEMENTATION**
1️⃣ Train.java
```
import java.util.ArrayList;
import java.util.List;

public class Train {

    private int trainNo;
    private String trainType;
    private float maxSpeed;

    private List<TrainJourney> journeys = new ArrayList<>();

    public Train(int trainNo, String trainType, float maxSpeed) {
        this.trainNo = trainNo;
        this.trainType = trainType;
        this.maxSpeed = maxSpeed;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public float getTrainSpeed() {
        return maxSpeed;
    }

    public void addJourney(TrainJourney journey) {
        journeys.add(journey);
    }
}
```
2️⃣ TrainJourney.java
```
public class TrainJourney {

    private int trainNo;
    private String sourceSt;
    private String destinationSt;
    private float journeyTime;

    // Association (0..1 Train)
    private Train assignedTrain;

    public TrainJourney(int trainNo, String sourceSt,
                         String destinationSt, float journeyTime) {
        this.trainNo = trainNo;
        this.sourceSt = sourceSt;
        this.destinationSt = destinationSt;
        this.journeyTime = journeyTime;
    }

    public void setSourceSt(String source) {
        this.sourceSt = source;
    }

    public void setDestinationSt(String destination) {
        this.destinationSt = destination;
    }

    public String getSourceSt() {
        return sourceSt;
    }

    public float getJourneyTime() {
        return journeyTime;
    }

    // Association method
    public void assignTrain(Train train) {
        this.assignedTrain = train;
        train.addJourney(this);
    }
}
```
**🔁 How UML Is Implemented**
| UML Element   | Java Implementation    |
| ------------- | ---------------------- |
| Train         | `Train` class          |
| TrainJourney  | `TrainJourney` class   |
| 0..* journeys | `List<TrainJourney>`   |
| 0..1 train    | `Train assignedTrain`  |
| assignedTrain | `assignTrain()` method |



_The Train–Journey association is implemented using Java classes. A Train can be associated with multiple TrainJourney objects, while a TrainJourney can be assigned to at most one Train. This association is implemented using object references and collections. The design strictly follows the multiplicity constraints shown in the UML diagram._
