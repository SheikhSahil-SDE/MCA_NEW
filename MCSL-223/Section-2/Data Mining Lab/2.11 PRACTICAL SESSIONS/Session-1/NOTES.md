# Session-01

# 1. Download and install WEKA. Navigate the various options available in WEKA. Explore the available datasets in WEKA. Load various datasets and observe the following”
    
a. List the attribute names and their types
b. No. of records in each dataset
c. Identify the class attribute(if any)
d. Plot Histogram
e. Determine the no. of records for each class.
f. Visualize the data in different dimensions.

**🔬 DATA MINING LAB – EXPERIMENT 1**

Objective:To download and install WEKA, explore its interface, load datasets, and analyze dataset characteristics using visualization tools.

**🔧 Software Required**
- WEKA (Waikato Environment for Knowledge Analysis)
  
- Java (comes bundled with WEKA)

**1️⃣ Download and Install WEKA**

Steps:

1. Open browser → go to https://www.cs.waikato.ac.nz/ml/weka/
2. Click Download
3. Select:
  - Windows → .exe
  - Linux → .zip
4. Install using default settings
5. Launch Weka GUI Chooser


**2️⃣ Navigate WEKA Interface** <br> <br>

<img src ="" alt="Pic_1">
<img src ="" alt="Pic_2">
<img src ="" alt="Pic_3">


After opening WEKA, you’ll see WEKA GUI Chooser with options:
| Option         | Purpose                                                                |
| -------------- | ---------------------------------------------------------------------- |
| **Explorer**   | Main tool for preprocessing, classification, clustering, visualization |
| Experimenter   | Performance comparison                                                 |
| Knowledge Flow | Visual workflow                                                        |
| Simple CLI     | Command line                                                           |



**👉 We use: EXPLORER**
**3️⃣ Explore Available Datasets in WEKA**
Steps:
1. Open Explorer
2. Click Open file
3. Navigate to:

```kotlin```
```
weka → data
```

**Common datasets:**
- iris.arff
- weather.nominal.arff
- diabetes.arff
- vote.arff
- glass.arff


4️⃣ Load Dataset and Observe Details
(Example: iris.arff)

# a. List Attribute Names & Types

After loading dataset → **Preprocess tab**

| Attribute Name | Type    |
| -------------- | ------- |
| sepallength    | Numeric |
| sepalwidth     | Numeric |
| petallength    | Numeric |
| petalwidth     | Numeric |
| class          | Nominal |

# b. Number of Records

Instances: 150

📌 (Shown at top right in Preprocess tab)

# c. Identify Class Attribute

- Class Attribute: class
- Type: Nominal
- Classes:
    - Iris-setosa
    - Iris-versicolor
    - Iris-virginica
    


# d️. Plot Histogram

Steps:

1. Select an attribute (e.g., petallength)
2. Histogram appears in right panel
3. Different colors represent different classes

📌 Observation:
Petal length clearly separates Iris-setosa from other classes.

# e. Number of Records for Each Class

Under Class attribute statistics:
| Class           | No. of Records |
| --------------- | -------------- |
| Iris-setosa     | 50             |
| Iris-versicolor | 50             |
| Iris-virginica  | 50             |



# f️. Visualize Data in Different Dimensions

Steps:

1. Go to Visualize tab
2. Select:
    - X-axis: petallength
    - Y-axis: petalwidth
3. Observe scatter plot

📌 Observation:

- Iris-setosa forms a clearly separable cluster
- Other two classes partially overlap
You can change axes to visualize multiple attribute combinations.

✅ Result

WEKA was successfully installed. Various datasets were loaded and analyzed. Attributes, class labels, instance distribution, histograms, and multi-dimensional visualizations were observed.























