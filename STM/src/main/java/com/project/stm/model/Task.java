package com.project.stm.model;

import com.project.stm.model.enums.Status;
import com.project.stm.model.enums.Types;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;

    private String title;

    @Type(type = "text")
    private String decription;

    @Column(name = "date_added")
    private LocalDateTime dateAdded = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private Types types;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private User author;

    public Task() {
    }

    public Task(int taskId, String title, String decription, LocalDateTime dateAdded, Types types, Status status, User author) {
        this.taskId = taskId;
        this.title = title;
        this.decription = decription;
        this.dateAdded = dateAdded;
        this.types = types;
        this.status = status;
        this.author = author;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
