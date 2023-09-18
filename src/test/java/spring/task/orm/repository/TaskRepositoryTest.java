package spring.task.orm.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.task.orm.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testFindTaskByTaskNameContainsIgnoreCase() {
        Task task = new Task("Test Task", "Test Description",1L, null, LocalDateTime.now(), null, 1L);
        taskRepository.save(task);
        Optional<Task> foundTask = taskRepository.findTaskByTaskNameContainsIgnoreCase("test TAS");
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTaskName()).isEqualTo("Test Task");
        Optional<Task> notFoundTask = taskRepository.findTaskByTaskNameContainsIgnoreCase("nonexistent");
        assertThat(notFoundTask).isEmpty();
    }

    @Test
    public void testFindByCompletedTimeIsNull() {
        taskRepository.save(new Task("Task1", "Description1",4L, null, LocalDateTime.now(),null, 1L));
        taskRepository.save(new Task("Task2", "Description2",5L, null, null,null, 1L));
        List<Task> incompleteTasks = taskRepository.findByCompletedTimeIsNull();
        assertThat(incompleteTasks).hasSize(1);
        assertThat(incompleteTasks.get(0).getTaskName()).isEqualTo("Task2");
    }
//    As the values of the taskIds change regularly, we cannot be certain this will pass
//    @Test
//    public void testFindTopByOrderByTaskIdDesc() {
//        Task task1 = new Task("Task1", "Description1", 40000L, null, LocalDateTime.now(), null, 1L);
//        Task task2 = new Task("Task2", "Description2", 40001L, null, null, null, 1L);
//        taskRepository.save(task1);
//        taskRepository.save(task2);
//
//        Optional<Task> task = taskRepository.findFirstByOrderByTaskIdDesc();
//        assertThat(task).isPresent();
//        Long taskId = task.get().getTaskId();
//        assertThat(taskId).isGreaterThan(task1.getTaskId());
//        assertThat(taskId).isEqualTo(task2.getTaskId());
//    }


    @Test
    public void testFindByCompletedTimeNotNullAndRecurrenceIsNotNull() {
        Task task1 = new Task("Task1", "Description1", 4L, null, LocalDateTime.now(), null, 1L);
        Task task2 = new Task("Task2", "Description2", 5L, LocalDateTime.now(), LocalDateTime.now(), null, 1L);
        Task task3 = new Task("Task3", "Description3", 6L, LocalDateTime.now(), LocalDateTime.now(), "7,7", 1L);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByCompletedTimeNotNullAndRecurrenceIsNotNull();
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTaskName()).isEqualTo("Task3");
    }

    @Test
    public void testFindByCompletedTimeIsNotNullAndRecurrenceIsNull() {
        Task task1 = new Task("Task1", "Description1", 4L, null, LocalDateTime.now(), "1,1", 1L);
        Task task2 = new Task("Task2", "Description2", 5L, LocalDateTime.now(), LocalDateTime.now(), null, 1L);
        Task task3 = new Task("Task3", "Description3", 6L, LocalDateTime.now(), LocalDateTime.now(), "7,7", 1L);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByCompletedTimeIsNotNullAndRecurrenceIsNull();
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTaskName()).isEqualTo("Task2");
    }
}

