package spring.task.api.service;

import spring.task.orm.entity.Task;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    void addNewTask(Task task);
    List<Task> getIncompleteTasks();
    List<Task> getCompletedTasks();
    List<Task> getCompletedRecurringTasks();
    void deleteTask(Long taskId);
    void updateTask(Long taskId,
                    String taskName,
                    String taskDesc,
                    LocalDateTime dueTime,
                    LocalDateTime completedTime,
                    String recurrence);
}
