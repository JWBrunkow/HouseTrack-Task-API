package com.example.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public List<Task> getTasks() {
       return taskService.getTasks();
    }

    @PostMapping
    public void addTask(@RequestBody Task task){taskService.addNewTask(task); }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId){
        taskService.deleteTask(taskId);
    }
    @PutMapping(path = "{taskId}")
    public void updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestParam(required = false) String taskName,
            @RequestParam(required = false) String taskDesc,
            @RequestParam(required = false)Timestamp dueTime,
            @RequestParam(required = false)Timestamp completedTime
            ){taskService.updateTask(taskId, taskName, taskDesc, dueTime, completedTime);

    }
}