package spring.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.task.orm.entity.Task;
import spring.task.orm.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
public class TaskConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(TaskRepository repository){
//        return args -> {
//                    Task task1 = new Task(
//                        "take out rubbish",
//                        "bins are full, take out rubbish",
//                        1L,
//                        LocalDateTime.of(2023, Month.APRIL, 27, 13, 00),
//                        null,
//                            null,
//                        1L
//                    );
//
//                    Task task2 = new Task(
//                    "feed cat",
//                    "he is hungry boi",
//                    2L,
//                    LocalDateTime.of(2023, Month.APRIL, 28, 13, 00),
//                    null,
//                    null,
//                    1L
//                     );
//            repository.saveAll(
//                List.of(task1, task2)
//            );
//        };
//    }
}
