package spring.task.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.task.orm.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  //  @Query()
  Optional<Task> findTaskByTaskNameContainsIgnoreCase(String taskName);
  @Query("SELECT MAX(t.taskId) FROM Task t")
  Optional<Long> findMaxId();
  List<Task> findByCompletedTimeIsNull();
}
