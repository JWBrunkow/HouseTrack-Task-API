package com.example.demo.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

  //  @Query()
    Optional<Task> findTaskByTaskNameContainsIgnoreCase(String taskName);

   // Optional<Task> findTaskById(Long taskId);
}
