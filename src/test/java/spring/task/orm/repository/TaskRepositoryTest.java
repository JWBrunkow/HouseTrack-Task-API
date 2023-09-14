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
        Task task = new Task("Test Task", "Test Description",1L, null, LocalDateTime.now(), 1L);
        taskRepository.save(task);
        Optional<Task> foundTask = taskRepository.findTaskByTaskNameContainsIgnoreCase("test TAS");
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getTaskName()).isEqualTo("Test Task");
        Optional<Task> notFoundTask = taskRepository.findTaskByTaskNameContainsIgnoreCase("nonexistent");
        assertThat(notFoundTask).isEmpty();
    }

    @Test
    public void testFindMaxId() {
        taskRepository.save(new Task("Task1", "Description1", 4L, null, LocalDateTime.now(), 1L));
        taskRepository.save(new Task("Task2", "Description2", 5L, null, LocalDateTime.now(), 1L));
        Optional<Long> maxId = taskRepository.findMaxId();
        assertThat(maxId).isPresent();
        assertThat(maxId.get()).isEqualTo(5L);
    }

    @Test
    public void testFindByCompletedTimeIsNull() {
        taskRepository.save(new Task("Task1", "Description1",4L, null, LocalDateTime.now(), 1L));
        taskRepository.save(new Task("Task2", "Description2",5L, null, null, 1L));
        List<Task> incompleteTasks = taskRepository.findByCompletedTimeIsNull();
        assertThat(incompleteTasks).hasSize(1);
        assertThat(incompleteTasks.get(0).getTaskName()).isEqualTo("Task2");
    }
}
