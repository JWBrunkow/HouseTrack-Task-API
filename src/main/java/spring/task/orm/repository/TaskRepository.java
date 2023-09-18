package spring.task.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.task.orm.entity.Task;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  Optional<Task> findTaskByTaskNameContainsIgnoreCase(String taskName);
//  @Query("SELECT t.taskId FROM Task t ORDER BY t.taskId DESC")
//  Optional<Long> findTopByOrderByTaskIdDesc();

  Optional<Task> findFirstByOrderByTaskIdDesc();
  List<Task> findByCompletedTimeNotNullAndRecurrenceIsNotNull();
  List<Task> findByCompletedTimeIsNull();
  List<Task> findByCompletedTimeIsNotNullAndRecurrenceIsNull();
  //List<Task> findByCompletedTimeIsNotNull();

}
