package com.sidof.task.api;

import com.sidof.task.Repository.TaskRepo;
import com.sidof.task.model.Task;
import com.sidof.task.security.model.Appuser;
import com.sidof.task.security.service.UserService;
import com.sidof.task.services.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Author sidof
 * @Since 10/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("/api/v1/task")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class TaskApi {
    private final TaskServiceImpl taskService;
    private final UserService userService;
    private final TaskRepo taskRepo;

    @PostMapping("addTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws InterruptedException {
        var taskToSave = Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignee(task.getAssignee())
                .userId(task.getAssignee().getId())
                .build();
        Task createdTask = taskService.createTask(taskToSave);

        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<>(taskToSave, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            return new ResponseEntity<>(task, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

//    @GetMapping
//    public ResponseEntity<List<Task>>
//    getAllTasks(@RequestParam(name = "userId", defaultValue = "0") int id,
//                @RequestParam(name = "filter", defaultValue = "") String filter,
//                @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
//                @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
//                @RequestParam(name = "pageSize", defaultValue = "3") int pageSize) throws InterruptedException {
//        List<Task> tasks = taskService.getAllTasks((long) id, filter, sortOrder, pageNumber, pageSize);
////        TimeUnit.SECONDS.sleep(1);
//        return new ResponseEntity<>(tasks, OK);
//    }

    @GetMapping
    public ResponseEntity<List<Task>>
    getAllTasks() throws InterruptedException {
        List<Task> tasks = taskRepo.findAll();
//        TimeUnit.SECONDS.sleep(1);
        return new ResponseEntity<>(tasks, OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>>getTasks() throws InterruptedException {
        List<Task> tasks = taskService.taks();
        return new ResponseEntity<>(tasks, OK);
    }


    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<Task>> getTasksByAssignee(@PathVariable Long assigneeId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        Appuser assignee = userService.getUser(assigneeId);
        if (assignee != null) {
            List<Task> tasks = taskService.getTasksByAssignee(assignee);
            return new ResponseEntity<>(tasks, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PutMapping("/editeTask")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        Task existingTask = taskService.getTaskById(task.getId());
        if (existingTask != null) {
            Task updatedTask = taskService.updateTask(task);
            return new ResponseEntity<>(updatedTask, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PutMapping("/assignUserTask")
    public ResponseEntity<Task> assignUserTask(@RequestBody Task task) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        Task existingTask = taskService.getTaskById(task.getId());
        Appuser existUser = userService.getUser(task.getAssignee().getId());
        if (existingTask != null && existUser != null) {

            Task updatedTask = taskService.updateTask(existingTask);
            return new ResponseEntity<>(updatedTask, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        Task existingTask = taskService.getTaskById(taskId);
        if (existingTask != null) {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
