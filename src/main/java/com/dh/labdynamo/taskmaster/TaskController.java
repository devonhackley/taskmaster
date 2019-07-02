package com.dh.labdynamo.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;


}
