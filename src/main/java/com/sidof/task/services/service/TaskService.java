package com.sidof.task.services.service;

import com.sidof.task.model.Task;
import com.sidof.task.security.model.Appuser;

import java.util.List;

/**
 * @Author sidof
 * @Since 10/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
public interface TaskService {
    Task createTask(Task task);
    Task getTaskById(Long taskId);
    List<Task> getAllTasks();
    List<Task> getTasksByAssignee(Appuser assignee);
    Task updateTask(Task task);
    void deleteTask(Long taskId);
}
