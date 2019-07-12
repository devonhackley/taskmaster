#Lab 26: NoSQL and DynamoDB

* [Deployed Site](http://taskmaster2.us-west-2.elasticbeanstalk.com/)

 It’s a task-tracking application with the same basic goal as Trello: allow users to keep track of tasks to be done and their status. 

  *Day two:*
    Now the application should be able to assign a user to a task and grab all tasks for a given user.
  *Day three:*
    Now the application should be able to attach an image to a task, be able to return a task with an image url attached to it
  *Day four:* Now application should be able to add a thumbnail of an image if that image is added to a task

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
  * `/tasks`(POST): will take user data and generate a new task
  * `/tasks/{id}/assign/{assignee}`: will update the status of the task.
  
  *Day Two:*
    * `/users/{name}/tasks`: Will return all the tasks to the assign user that was passed in URL
    * `/tasks/{id}/assign/{assignee}`: Will assign a task to a user as long as the task isn't already assigned to the same user.
  *Day Three:*
    * `/tasks/{id}/images`: will add a image to a given task
    * `/tasks/{id}`: will grab a single task (should have s3 link)
  *Day Four:*
    * Added lambda function that will resize an images added to s3 

  *Day Five:*
    * Add a lambda warmer that will "re-warm" the function every minute


  