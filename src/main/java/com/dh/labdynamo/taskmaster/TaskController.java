package com.dh.labdynamo.taskmaster;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
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
    public ResponseEntity<Task> createNewTask(String title, String description){
        Task task = new Task(title, description);
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
}
