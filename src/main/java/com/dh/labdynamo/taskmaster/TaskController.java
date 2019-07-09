package com.dh.labdynamo.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class TaskController {

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

    //TODO: Add in POST /tasks/{id}/images

    //TODO: GET /tasks/{id}


}
