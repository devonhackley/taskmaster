#Lab 26: NoSQL and DynamoDB

 It’s a task-tracking application with the same basic goal as Trello: allow users to keep track of tasks to be done and their status. 

 ## Architecture

#### Main Files
  * [Task.java](./src/main/java/com/dh/labdynamo/taskmaster/Task.java)
  * [TaskController.java](./src/main/java/com/dh/labdynamo/taskmaster/TaskController.java)
 
  
#### Test Files
  * [TaskControllerTest.java](./src/test/java/com/dh/labdynamo/taskmaster/TaskControllerTest.java)

## Usage
-`git clone repo`
- Open terminal, and run this command: `./gradlew bootRun`
  
  #### Routes
  * `/`: will return all of the tasks
  * `/tasks`(GET): will return all of the tasks
  * `/post`(POST): will take user data and generate a new task
  * `/put`: will update the status of the task.
 
  