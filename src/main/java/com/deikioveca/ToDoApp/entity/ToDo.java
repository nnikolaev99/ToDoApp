package com.deikioveca.ToDoApp.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "To_Do")
@Builder
public class ToDo {

    @Id
    @SequenceGenerator(name = "toDo_sequence", sequenceName = "toDo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "toDo_sequence")
    private Integer id;

    @Column(name = "to_do", nullable = false)
    private String toDo;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;
}
