package com.sidof.task.Repository;

import com.sidof.task.model.Task;
import com.sidof.task.security.model.Appuser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author sidof
 * @Since 10/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findByAssignee(Appuser assignee);
}
