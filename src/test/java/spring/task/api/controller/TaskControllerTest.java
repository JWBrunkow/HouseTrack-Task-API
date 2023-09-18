package spring.task.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import spring.task.api.service.TaskService;
import spring.task.exception.custom.InvalidTaskInputException;
import spring.task.exception.custom.TaskAlreadyExistsException;
import spring.task.exception.custom.TaskNotFoundException;
import spring.task.orm.entity.Task;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("Test Task", "This is a test", 1L, LocalDateTime.now(), null, null, 1L);
    }
    @Test
    public void testGetTasks() throws Exception {
        when(taskService.getTasks()).thenReturn(Collections.singletonList(task));

        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value(task.getTaskName()));

        verify(taskService, times(1)).getTasks();
    }
    @Test
    public void testDeleteTask_NotFound() throws Exception {
        doThrow(new TaskNotFoundException("Task not found")).when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/api/v1/task/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found"));

        verify(taskService, times(1)).deleteTask(1L);
    }
    @Test
    public void getIncompleteTasks_ShouldReturnStatusOk() throws Exception {
        given(taskService.getIncompleteTasks()).willReturn(Collections.singletonList(task));

        mockMvc.perform(get("/api/v1/task/incomplete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addTask_ShouldReturnStatusOk() throws Exception {
        String taskJson = "{ \"taskName\": \"TestTask\", \"taskDesc\": \"TestDescription\" }";

        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTask_ShouldReturnStatusOk() throws Exception {
        long taskId = 1L;
        String taskJson = "{ \"taskName\": \"UpdatedTask\", \"taskDesc\": \"UpdatedDescription\" }";

        mockMvc.perform(put("/api/v1/task/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk());
    }
}
