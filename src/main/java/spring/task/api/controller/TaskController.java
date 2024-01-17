package spring.task.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.task.api.service.TaskService;
import spring.task.orm.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {this.taskService = taskService;}
    @GetMapping()
    public List<Task> getTasks() {return taskService.getTasks();}
    @GetMapping("/incomplete")
    public List<Task> getIncompleteTasks() {return taskService.getIncompleteTasks();}
    @GetMapping("/completed")
    public List<Task> getCompletedTasks() {return taskService.getCompletedTasks();}
    @GetMapping("/recurring")
    public List<Task> getCompletedRecurringTasks() {return taskService.getCompletedRecurringTasks();}
    @PostMapping
    public void addTask(@RequestBody Task task) {
        taskService.addNewTask(task);
    }
    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }
    @PutMapping(path = "{taskId}")
    public void updateTask(@PathVariable("taskId") Long taskId, @RequestBody Task updatedTask) {
        taskService.updateTask(taskId,
                updatedTask.getTaskName(),
                updatedTask.getTaskDesc(),
                (LocalDateTime) updatedTask.getDueTime(),
                (LocalDateTime) updatedTask.getCompletedTime(),
                updatedTask.getRecurrence());
    }
}
