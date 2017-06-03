# [Controller documentation Here !](https://github.com/Cherry-project/wiki/wiki/Controller)

# MERGE review
This is a document where every components was reviewed

## Package Cherry
Files are the same

## Package Cherry.apphandlers.web
Files are new

## Package Cherry.chatterbot.web / service
Files are the same

## Package Cherry.crmhandlers.web / service
Files are the same

## Package Cherry.gamehandlers.web / service
Files are the same

## Package Cherry.robothandlers.service
Files diff

### HttpConnection.java

#### Components Added

##### sendGetCode(String url) :

- Fork of existing methods : sendGet(String url)

- Same as sendGet but return response code http

- Use in Components added like startBehaviorPrimitive in LaunchPrimitive.java

##### sendPostJson(String url,JSONObject json) :

- Fork of existing methods : sendPost(String url,String urlParameters)

- Same as sendPost but take JSONObject argument and send a Json response

- Use in Components added like startSpeakPrimitive in LaunchPrimitive.java

### LaunchPrimitive.java

#### Components Added

##### startBehaviorPrimitive(String behavior, String url) :

- Fork of existing methods : startBehaviorPrimitive(String behavior)

- Method to start "behave" primitives with a robot targeted

- Use in Components like appBehave in AppController.java

- Use in Components like behaveFinished in RobotController.java

##### stopCurrentPrimitive(String url) :

- Fork of existing methods : stopPrimitive(String behavior)

- Method to stop "behave" primitives with a robot targeted

- Not use but intended to be used in AppController.java

##### startSpeakPrimitive(String txt, String url) :

- Fork of existing methods startBehaviorPrimitive(String behavior, String url)

- Method to start "speak" primitive with a robot targeted

- Use in Components like appSpeech in AppController.java

- Use in Components like speakFinished in RobotController.java

##### getPrimitiveList(String url) :

- Fork of existing methods : getRunningPrimitiveList()

- Method get all primitives not only running ones with a robot targeted

- Use in Components like appPrimitives in AppController.java

### Robot.java

#### Components Added

##### isTaken

- Fork of existing components : isMoving

- Method that is true if a CherryApp is using the robot

- Use in Components like appIsTaken in AppController.java

##### primList

- Method that store a String List of primitives to execute

- Use in Components like appChore in AppController.java

##### speechList

- Method that store a String List of phrases to execute

- Use in Components like appSpeech in AppController.java

##### toString

## Package Cherry.robothandlers.web
Files diff

### RobotController.java

#### Components Modified

##### speakFinished :

##### behaveFinished :

#### Components Added

##### robotTaken :

- set the isTaken to true in Robot.java

##### robotRelease :

- set the isTaken to false in Robot.java

### SetupController.java

#### Components Modified

##### setupRobot(String name, int port, String ip, request, response)

- previous declaration : setupRobot(String name, request, response)

- In case of the server can't reach the robot by finding by himself the good ip, this method will use the given arguments (ip and port) to ping the robot.

- This method is used when both the controller and the robot are on a local network and accessible without Firewall

#### Components Added

##### setupSSHRobot(String name, request, response) :

- Fork of existing components : setupRobot

- Method that get an available port, add a robot to the list on the local port and respond the port to the robot

- Then after this response the robot start on this port and establish an ssh tunnel connection

##### removeRobot(String name, request, response) :

- Fork of new component : getRobot

- Method that remove a robot when he is shuting down

##### getRobot(String name) :

- Go throw the list of robot (robotList Component) to find the good one

##### getAvailablePort() :

- Method return the first port available used by no application

- This method opens a tcp connection on every port, if there is no error it means that the port isn't used

# MERGE review
This is a document where every components was reviewed

## Package Cherry
Files are the same

## Package Cherry.apphandlers.web
Files are new

## Package Cherry.chatterbot.service
Files are the same

## Package Cherry.chatterbot.web
Files are the same

## Package Cherry.crmhandlers.web
Files are the same

## Package Cherry.crmhandlers.service
Files are the same

## Package Cherry.gamehandlers.web
Files are the same

## Package Cherry.gamehandlers.web
Files are the same

## Package Cherry.robothandlers.service
Files diff

### HttpConnection.java

#### Components Added

##### sendGetCode(String url) :

- Fork of existing methods : sendGet(String url)

- Same as sendGet but return response code http

- Use in Components added like startBehaviorPrimitive in LaunchPrimitive.java

##### sendPostJson(String url,JSONObject json) :

- Fork of existing methods : sendPost(String url,String urlParameters)

- Same as sendPost but take JSONObject argument and send a Json response

- Use in Components added like startSpeakPrimitive in LaunchPrimitive.java

### LaunchPrimitive.java

#### Components Added

##### startBehaviorPrimitive(String behavior, String url) :

- Fork of existing methods : startBehaviorPrimitive(String behavior)

- Method to start "behave" primitives with a robot targeted

- Use in Components like appBehave in AppController.java

- Use in Components like behaveFinished in RobotController.java

##### stopCurrentPrimitive(String url) :

- Fork of existing methods : stopPrimitive(String behavior)

- Method to stop "behave" primitives with a robot targeted

- Not use but intended to be used in AppController.java

##### startSpeakPrimitive(String txt, String url) :

- Fork of existing methods startBehaviorPrimitive(String behavior, String url)

- Method to start "speak" primitive with a robot targeted

- Use in Components like appSpeech in AppController.java

- Use in Components like speakFinished in RobotController.java

##### getPrimitiveList(String url) :

- Fork of existing methods : getRunningPrimitiveList()

- Method get all primitives not only running ones with a robot targeted

- Use in Components like appPrimitives in AppController.java

### Robot.java

#### Components Added

##### isTaken

- Fork of existing components : isMoving

- Method that is true if a CherryApp is using the robot

- Use in Components like appIsTaken in AppController.java

##### primList

- Method that store a String List of primitives to execute

- Use in Components like appChore in AppController.java

##### speechList

- Method that store a String List of phrases to execute

- Use in Components like appSpeech in AppController.java

##### toString

## Package Cherry.robothandlers.web
Files diff

### RobotController.java

#### Components Modified

##### speakFinished :

##### behaveFinished :

#### Components Added

##### robotTaken :

- set the isTaken to true in Robot.java

##### robotRelease :

- set the isTaken to false in Robot.java

### SetupController.java

#### Components Modified

##### setupRobot(String name, int port, String ip, request, response)

- previous declaration : setupRobot(String name, request, response)

- In case of the server can't reach the robot by finding by himself the good ip, this method will use the given arguments (ip and port) to ping the robot.

- This method is used when both the controller and the robot are on a local network and accessible without Firewall

#### Components Added

##### setupSSHRobot(String name, request, response) :

- Fork of existing components : setupRobot

- Method that get an available port, add a robot to the list on the local port and respond the port to the robot

- Then after this response the robot start on this port and establish an ssh tunnel connection

##### removeRobot(String name, request, response) :

- Fork of new component : getRobot

- Method that remove a robot when he is shuting down

##### getRobot(String name) :

- Go throw the list of robot (robotList Component) to find the good one

##### getAvailablePort() :

- Method return the first port available used by no application

- This method opens a tcp connection on every port, if there is no error it means that the port isn't used
