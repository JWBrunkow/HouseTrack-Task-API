package spring.task.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.task.exception.custom.TaskAlreadyExistsException;
import spring.task.exception.custom.TaskNotFoundException;
import spring.task.orm.entity.Task;
import spring.task.orm.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplementationTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImplementation taskService;  // <-- Change to concrete implementation

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task("Test Task", "This is a test", 1L, LocalDateTime.now(), null, null, 1L);
    }
    @Test
    public void testGetTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        assertEquals(1, taskService.getTasks().size());
        assertEquals(task, taskService.getTasks().get(0));
    }
    @Test
    public void testAddNewTask_Valid() {
        when(taskRepository.findTaskByTaskNameContainsIgnoreCase(task.getTaskName())).thenReturn(Optional.empty());
        taskService.addNewTask(task);
        verify(taskRepository, times(1)).save(task);
    }
    @Test
    public void testAddNewTask_AlreadyExists() {
        when(taskRepository.findTaskByTaskNameContainsIgnoreCase(task.getTaskName())).thenReturn(Optional.of(task));
        assertThrows(TaskAlreadyExistsException.class, () -> taskService.addNewTask(task));
    }
    @Test
    public void testGetIncompleteTasks() {
        when(taskRepository.findByCompletedTimeIsNull()).thenReturn(Collections.singletonList(task));
        assertEquals(1, taskService.getIncompleteTasks().size());
        assertEquals(task, taskService.getIncompleteTasks().get(0));
    }
    @Test
    public void testDeleteTask_Valid() {
        when(taskRepository.existsById(task.getTaskId())).thenReturn(true);
        taskService.deleteTask(task.getTaskId());
        verify(taskRepository, times(1)).deleteById(task.getTaskId());
    }
    @Test
    public void testDeleteTask_NotFound() {
        when(taskRepository.existsById(task.getTaskId())).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(task.getTaskId()));
    }
    @Test
    public void testUpdateTask_Valid() {
        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));
        taskService.updateTask(task.getTaskId(), "New Task", "New Description", LocalDateTime.now(), null, null);
        assertEquals("New Task", task.getTaskName());
        assertEquals("New Description", task.getTaskDesc());
    }
    @Test
    public void testUpdateTask_NotFound() {
        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () ->
                taskService.updateTask(task.getTaskId(), "New Task", "New Description", LocalDateTime.now(), null , null)
        );
    }
}
