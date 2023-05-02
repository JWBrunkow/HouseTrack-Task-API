package com.example.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;

    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void addNewTask(Task task) {
        Optional<Task> taskbyName = taskRepository.findTaskByTaskNameContainsIgnoreCase(task.getTaskName());
        if(taskbyName.isPresent()){
            throw new IllegalStateException("A task with this name already exists");
        }
        taskRepository.save(task);
    }
    public void deleteTask(Long taskId){
        boolean exists = taskRepository.existsById(String.valueOf(taskId));
        if(!exists){
            throw new IllegalStateException("Task with id " + taskId + "does not exist");
        }
        taskRepository.deleteById(String.valueOf(taskId));
    }
// transactional annotation uses all methods in managed state
    @Transactional
    public void updateTask(Long taskId,
                           String taskName,
                           String taskDesc,
                           Timestamp dueTime,
                           Timestamp completedTime) {
        Task task = taskRepository.findById(String.valueOf(taskId))
                                    .orElseThrow(() -> new IllegalStateException
                                            ("task with id" + taskId + "does not exist"));
        if (taskName != null
                && taskName.length() > 0
                && !Objects.equals(task.getTaskName(), taskName)){
            task.setTaskName(taskName);
        }
        if (taskDesc != null
                && taskDesc.length() > 0
                && !Objects.equals(task.getTaskDesc(), taskDesc)){
            task.setTaskDesc(taskDesc);
        }
        if (dueTime != null){
            task.setDueTime(dueTime.toLocalDateTime());
        }
        if (completedTime != null){
            task.setCompletedTime(completedTime.toLocalDateTime());
        }

    }
}
