package spring.task.orm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("Test Task", "Test Description", 1L, LocalDateTime.now(), null, null , 2L);
    }

    @Test
    public void testTaskNameGetterAndSetter() {
        task.setTaskName("Updated Task");
        assertEquals("Updated Task", task.getTaskName());
    }

    @Test
    public void testTaskDescGetterAndSetter() {
        task.setTaskDesc("Updated Description");
        assertEquals("Updated Description", task.getTaskDesc());
    }

    @Test
    public void testTaskIdGetterAndSetter() {
        task.setTaskId(2L);
        assertEquals(2L, task.getTaskId());
    }

    @Test
    public void testDueTimeGetterAndSetter() {
        LocalDateTime newTime = LocalDateTime.now().plusDays(1);
        task.setDueTime(newTime);
        assertEquals(newTime, task.getDueTime());
    }

    @Test
    public void testCompletedTimeGetterAndSetter() {
        LocalDateTime newTime = LocalDateTime.now().plusDays(2);
        task.setCompletedTime(newTime);
        assertEquals(newTime, task.getCompletedTime());
    }

    @Test
    public void testUserIdGetterAndSetter() {
        task.setUserId(3L);
        assertEquals(3L, task.getUserId());
    }

    @Test
    public void testToString() {
        String expectedString = "Task{" +
                "taskName='Test Task'" +
                ", taskDesc='Test Description'" +
                ", TaskId=1" +
                ", dueTime=" + task.getDueTime() +
                ", completedTime=" + task.getCompletedTime() +
                ", userId=2" +
                ", recurrence='null'" +
                '}';
        assertEquals(expectedString, task.toString());
    }
}
