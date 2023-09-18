package spring.task.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
public class TaskServiceImplementation implements TaskService {
    private final TaskRepository taskRepository;
    private final AtomicLong taskIdCounter;

//    @Autowired
//    public TaskServiceImplementation(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//        Long maxIdInDb = taskRepository.findFirstByOrderByTaskIdDesc().orElse(0L);
//        taskIdCounter = new AtomicLong(maxIdInDb + 1);
//    }
    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        Optional<Task> task = taskRepository.findFirstByOrderByTaskIdDesc();
        Long maxIdInDb = task.map(Task::getTaskId).orElse(0L);
        taskIdCounter = new AtomicLong(maxIdInDb + 1);
    }

    @Override
    public void addNewTask(Task task) {
        Optional<Task> taskByName = taskRepository.findTaskByTaskNameContainsIgnoreCase(task.getTaskName());
        if (taskByName.isPresent()) {
            throw new TaskAlreadyExistsException("A task with the name " + task.getTaskName() + " already exists.");
        }
        if (task.getTaskId() == null) {
            task.setTaskId(taskIdCounter.getAndIncrement());
        }
        taskRepository.save(task);
    }
    @Override
    public List<Task> getTasks() {return taskRepository.findAll();}
    @Override
    public List<Task> getCompletedRecurringTasks() {return taskRepository.findByCompletedTimeNotNullAndRecurrenceIsNotNull();}
    @Override
    public List<Task> getIncompleteTasks() {return taskRepository.findByCompletedTimeIsNull();}
    @Override
    public List<Task> getCompletedTasks() {return taskRepository.findByCompletedTimeIsNotNullAndRecurrenceIsNull();}
    @Override
    public void deleteTask(Long taskId) {
        boolean exists = taskRepository.existsById(taskId);
        if (!exists) {
            throw new TaskNotFoundException("Task with id " + taskId + " not found.");
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional
    public void updateTask(Long taskId,
                           String taskName,
                           String taskDesc,
                           LocalDateTime dueTime,
                           LocalDateTime completedTime,
                           String recurrence) {
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
        if (recurrence != null && !recurrence.isBlank() && !Objects.equals(task.getRecurrence(), recurrence)) {
            task.setRecurrence(recurrence);
        }
    }
}
