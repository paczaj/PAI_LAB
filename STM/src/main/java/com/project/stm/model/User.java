package com.project.stm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean status = false;

    @Column(name = "registration_date_time")
    private LocalDateTime registrationDateTime = LocalDateTime.now();

    @OneToMany(
            mappedBy = "author",
            // ONLY ON THE PARENT SIDE
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties({"author"})
    private List<Task> tasks = new ArrayList<>();

    public User(int userId, String name, String lastName, String email, String password, LocalDateTime registrationDateTime) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationDateTime = registrationDateTime;
    }

    public User(String name, String lastName, String email, String password, boolean status) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }
}
