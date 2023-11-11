package com.sidof.task.model;

import com.sidof.task.model.enume.Priority;
import com.sidof.task.model.enume.TaskStatus;
import com.sidof.task.security.model.Appuser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * @Author sidof
 * @Since 10/11/2023
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {
    @SequenceGenerator(name = "sequence_id_task", allocationSize = 1, sequenceName = "sequence_id_task")
    @GeneratedValue(strategy = SEQUENCE, generator = "sequence_id_task")
    @Id
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(STRING)
    @Column(nullable = false)
    private TaskStatus status;
    @Enumerated(STRING)
    @Column(nullable = false)
    private Priority priority;
    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "assignee_id",referencedColumnName = "id",nullable = true)
    private Appuser assignee;


}
