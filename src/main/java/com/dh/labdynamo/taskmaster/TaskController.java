package com.dh.labdynamo.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class TaskController {

    @Autowired
    S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    // Help: https://stackoverflow.com/questions/30895286/spring-mvc-how-to-return-simple-string-as-json-in-rest-controller
    @GetMapping({"/", "/tasks"})
    public ResponseEntity<Task> getAllTasks(){
        Iterable<Task> tasks = taskRepository.findAll();
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createNewTask(String title, String description, String assignee){
        Task task = new Task(title, description, assignee);
        taskRepository.save(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}/state")
    public void changeStateOfTask(@PathVariable UUID id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            if(task.get().getStatus().equals("Available")){
                task.get().setStatus("Assigned");
            } else if (task.get().getStatus().equals("Assigned")) {
                task.get().setStatus("Accepted");
            } else if (task.get().getStatus().equals("Accepted")){
                task.get().setStatus("Finished");
            }
            taskRepository.save(task.get());
        } else {
            System.out.println("No tasks in the database");
        }
    }

    @GetMapping("/users/{name}/tasks")
    public ResponseEntity<Task> getAllUserTask(@PathVariable String name){
        List<Task> tasks = taskRepository.findAllByAssignee(name);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public void assignTask(@PathVariable UUID id, @PathVariable String assignee){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            if(task.get().getAssignee() != assignee){
                task.get().setStatus("Assigned");
                task.get().setAssignee(assignee);
                taskRepository.save(task.get());
            } else {
                System.out.println("Unable to assign task");
            }
        } else {
            System.out.println("No tasks in the database");
        }
    }

    @PostMapping("/tasks/{id}/images")
    public ResponseEntity<Task> addImages(@PathVariable UUID id, @RequestPart(value = "file") MultipartFile file){
        // grab task from db
        Optional<Task> task = taskRepository.findById(id);
        // upload to s3, update task and save to db
        if(task.isPresent()){
            String pic = s3Client.uploadFile(file);
            String[] fileName = pic.split("/");
           task.get().setImageURL(pic);
           task.get().setImageResizedURL("https://tasksuploadbucketresized.s3.us-east-2.amazonaws.com/resized-" + fileName[fileName.length-1]);
           taskRepository.save(task.get());
        } else {
            System.out.println("No tasks in the database");
            return new ResponseEntity(task, HttpStatus.BAD_REQUEST);
        }

        // return task back to client
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id){
        Optional<Task> task = taskRepository.findById(id);
        // upload to s3, update task and save to db
        if(task.isPresent()){
            return new ResponseEntity(task, HttpStatus.OK);
        } else {
            return new ResponseEntity(task, HttpStatus.BAD_REQUEST);
        }
    }

}
