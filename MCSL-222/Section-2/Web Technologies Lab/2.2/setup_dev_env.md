For the Setup development environment, we need to set up a compiler, builder, editor (mainly JDK and IDE). Before installation of any Idea, Compiler and builder are
required. That is why we need to install the latest version of JDK in our system. The best thing about IDE is that its intelligence provides code completion hints during
coding in IDE. It also provides easy code, file and folder navigation. In this unit Windows 10 based installation and environment setup will be shown. Official
documents for Linux and Mac-based environment setup are provided in the reference section.

# Step 1: Installation of Java Development Kit (JDK)

    1. First, check the java version if already installed by executing the     command “java –version” in the terminal or command prompt as shown in Figure-2.1.
    
    2. If Java is not installed in your system, then, first of all, download the latest version of JDK from its official website
    (https://www.oracle.com/in/java/technologies/javase-downloads.html). For development practice, you can choose any latest version of JDK, but
    it is strongly recommended to choose the LTS (Long Term Support) version only for the production environment. Choose the JDK
    package as per your system/platform.
    
    3. Install the JDK in your system and recheck the installed version.
    
# Step 2: System Environment variable setup for Java in Windows 10
        
        1. For quick open Click on start menu and type System Environment, Control panel Option for Edit the System Environment Variables will show, click on it to open as shown in figure- 2.2

        2. System Property wizard will open as shown in figure- 2.3.

        3. Click on Environment Variables as marked in figure- 3. The Wizard for Environment Variables will be open as shown in figure – 5.

        4. Click on the New button if the classpath for JAVA_HOME is not set or click on the Edit Button to update with your latest JDK version as shown in figure- 2.4.

        5. Click Ok in all Wizard opens to save the JAVA_HOME variable as shown in figure – 2.5. 
        
        6.  Check the java verion as shown in figure- 1.1.


# Setup JAVA_HOME, CLASSPATH and PATH for Linux
        
```● Execute following command in terminal to edit /etc/profile file vi /etc/profile```<br>
```● Add following lines in last line in profile file.```

```export JAVA_HOME=/usr/jdk16.0.1``` <br>
```export CLASSPATH=$CLASSPATH:/home/vinay/LOG4J_HOME/log4j-2.2.16.jar``` <br>
```export PATH=$PATH:/usr/ jdk16.0.1/bin```

```
● Execute the command “/etc/profile” to activate these settings.
● Execute the following command in the terminal to verify whether the settings are correct or not.
```     
```$ java -version``` <br>
```
Now the class path for JDK is set successfully now you are ready to install IDE
(NetBeans/Eclipse/IntelliJ Idea)
