package com.sidof.task.services;

import com.sidof.task.Repository.TaskRepo;
import com.sidof.task.model.Task;
import com.sidof.task.security.model.Appuser;
import com.sidof.task.services.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author sidof
 * @Since 10/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;

    @Override
    public Task createTask(Task task) {
        return taskRepo.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepo.findById(taskId).orElse(null);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public List<Task> getTasksByAssignee(Appuser assignee) {
        return taskRepo.findByAssignee(assignee);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepo.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepo.deleteById(taskId);
    }
}
