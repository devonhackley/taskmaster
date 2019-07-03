#Lab 26: NoSQL and DynamoDB

 It’s a task-tracking application with the same basic goal as Trello: allow users to keep track of tasks to be done and their status. 

  *Day two:*
    Now the application should be able to assign a user to a task and grab all tasks for a given user.

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
  
  *Day Two:*
    * `/users/{name}/tasks`: Will return all the tasks to the assign user that was passed in URL
    * `/tasks/{id}/assign/{assignee}`: Will assign a task to a user as long as the task isn't already assigned to the same user.
 
  