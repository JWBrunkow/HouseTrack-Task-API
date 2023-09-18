package spring.task.api.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spring.task.orm.entity.Task;
import spring.task.orm.repository.TaskRepository;
import java.util.Arrays;
import static org.mockito.Mockito.*;

public class RecurrenceServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskService taskService;
    @InjectMocks
    private RecurrenceService recurrenceService;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {closeable = MockitoAnnotations.openMocks(this);}

    @AfterEach
    public void tearDown() throws Exception {closeable.close();}

    @Test
    public void testUpdateRecurrence() {
        Task mockTask = mock(Task.class);
        when(mockTask.getRecurrence()).thenReturn("5,5");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(mockTask));
        recurrenceService.updateRecurrence();
        verify(mockTask, times(1)).setRecurrence("5,4");
    }

    @Test
    public void testRecurrenceCreatesNewTask() {
        Task mockTask = mock(Task.class);
        when(mockTask.getRecurrence()).thenReturn("5,1");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(mockTask));
        recurrenceService.updateRecurrence();
        verify(taskService, times(1)).addNewTask(any(Task.class));
        verify(mockTask, times(1)).setRecurrence("5,5");
    }

    @Test
    public void testDailyTaskUpdate() {
        Task mockTask = mock(Task.class);
        when(mockTask.getRecurrence()).thenReturn("5,5");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(mockTask));
        recurrenceService.dailyTaskUpdate();
        verify(taskRepository, times(1)).findAll();
    }
}
