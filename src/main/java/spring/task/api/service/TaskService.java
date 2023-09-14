package spring.task.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.task.exception.custom.TaskAlreadyExistsException;
import spring.task.exception.custom.TaskNotFoundException;
import spring.task.orm.entity.Task;
import spring.task.orm.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicLong taskIdCounter; // A thread-safe counter for task IDs

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        Long maxIdInDb = taskRepository.findMaxId().orElse(0L);
        taskIdCounter = new AtomicLong(maxIdInDb + 1);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void addNewTask(Task task) {
        Optional<Task> taskbyName = taskRepository.findTaskByTaskNameContainsIgnoreCase(task.getTaskName());
        if (taskbyName.isPresent()) {
            throw new TaskAlreadyExistsException("A task with ID " + task.getTaskId() + " already exists.");
        }
        // Assign an ID if the task doesn't have one
        if (task.getTaskId() == null) {task.setTaskId(taskIdCounter.getAndIncrement());} taskRepository.save(task);}

    public List<Task> getIncompleteTasks() {return taskRepository.findByCompletedTimeIsNull();}

    // http://localhost:8080/api/v1/task/(the task)
    public void deleteTask(Long taskId) {
        boolean exists = taskRepository.existsById(taskId);
        if (!exists) {
            throw new TaskNotFoundException("Task with id " + taskId + " not found.");
        }
        taskRepository.deleteById(taskId);
    }

    // Transactional annotation uses all methods in managed state
    @Transactional
    public void updateTask(Long taskId,
                           String taskName,
                           String taskDesc,
                           LocalDateTime dueTime, // Change to LocalDateTime
                           LocalDateTime completedTime) { // Change to LocalDateTime
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found."));

        if (taskName != null && !taskName.isBlank() && !Objects.equals(task.getTaskName(), taskName)) {
            task.setTaskName(taskName);
        }
        if (taskDesc != null && !taskDesc.isBlank() && !Objects.equals(task.getTaskDesc(), taskDesc)) {
            task.setTaskDesc(taskDesc);
        }
        if (dueTime != null) {
            task.setDueTime(dueTime);
        }
        if (completedTime != null) {
            task.setCompletedTime(completedTime);
        }
    }

}
