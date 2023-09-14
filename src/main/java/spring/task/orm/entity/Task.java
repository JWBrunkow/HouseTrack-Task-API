package spring.task.orm.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

@Entity
@Table
public class Task {
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )

    private Long taskId;
    private String taskName;
    private String taskDesc;
    private LocalDateTime dueTime;
    private LocalDateTime completedTime;
    private Long userId;

    public Task() {
    }
    public Task(String taskName, String taskDesc, Long taskId, LocalDateTime dueTime, LocalDateTime completedTime, Long userId) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskId = taskId;
        this.dueTime = dueTime;
        this.completedTime = completedTime;
        this.userId = userId;
    }

    public String getTaskName() {return taskName;}
    public void setTaskName(String taskName) {this.taskName = taskName;}
    public String getTaskDesc() {return taskDesc;}
    public void setTaskDesc(String taskDesc) {this.taskDesc = taskDesc;}
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public ChronoLocalDateTime<LocalDate> getDueTime() {return dueTime;}
    public void setDueTime(LocalDateTime dueTime) {this.dueTime = dueTime;}
    public ChronoLocalDateTime<LocalDate> getCompletedTime() {return completedTime;}
    public void setCompletedTime(LocalDateTime completedTime) {this.completedTime = completedTime;}
    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}

    @Override
    public String toString() {
        return "Task{" + "taskName='" + taskName + '\'' + ", taskDesc='" + taskDesc + '\'' + ", TaskId=" + taskId + ", dueTime=" + dueTime + ", completedTime=" + completedTime + ", userId=" + userId + '}';
    }
}
