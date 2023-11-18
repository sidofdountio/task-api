package com.sidof.task.api;

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

    @PostMapping("addTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws InterruptedException {

        Task createdTask = taskService.createTask(task);
        TimeUnit.SECONDS.sleep(2);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
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

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() throws InterruptedException {
        List<Task> tasks = taskService.getAllTasks();
        TimeUnit.SECONDS.sleep(2);
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
