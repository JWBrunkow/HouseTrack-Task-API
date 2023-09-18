package spring.task.api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring.task.exception.custom.InvalidRecurrenceFormatException;
import spring.task.orm.entity.Task;
import spring.task.orm.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecurrenceService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Transactional
    public void updateRecurrence() {
        List<Task> allTasks = taskRepository.findAll();
        for (Task task : allTasks) {
            String recurrence = task.getRecurrence();
            if (recurrence != null && !recurrence.isBlank()) {
                String[] values = recurrence.split(",");
                if (values.length != 2) {
                    throw new InvalidRecurrenceFormatException("Recurrence format is invalid. Expected format: x,x");
                }
                int firstX, secondX;
                try {
                    firstX = Integer.parseInt(values[0]);
                    secondX = Integer.parseInt(values[1]);
                } catch (NumberFormatException e) {
                    throw new InvalidRecurrenceFormatException("Recurrence format is invalid. The values should be integers.", e);
                }

                secondX--;

                if (secondX == 0) {
                    Task newTask = new Task(
                            task.getTaskName(),
                            task.getTaskDesc(),
                            task.getTaskId(),
                            (LocalDateTime) task.getDueTime(),
                            (LocalDateTime) task.getCompletedTime(),
                            firstX + "," + firstX,
                            task.getUserId());
                    taskService.addNewTask(newTask);
                    task.setRecurrence(firstX + "," + firstX);
                } else {
                    task.setRecurrence(firstX + "," + secondX);
                }
                taskRepository.save(task);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // this runs daily at midnight
    public void dailyTaskUpdate() {updateRecurrence();}
}
