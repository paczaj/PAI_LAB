package com.project.stm.controller;

import com.project.stm.model.Task;
import com.project.stm.model.User;
import com.project.stm.model.enums.Status;
import com.project.stm.model.enums.Types;
import com.project.stm.repository.TaskRepository;
import com.project.stm.repository.UserRepository;
import com.project.stm.service.TaskService;
import com.project.stm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
public class MainController {
    UserService userService;
    TaskService taskService;

    @Autowired
    public MainController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.allUsers();
    }

    @GetMapping("/users/search")
    public Optional<User> searchUser(
            @RequestParam("id lub adres email") String value
    ){
        return userService.getUserByIdOrEmail(value);
    }

    @PostMapping("/users/registration")
    public User registerUser(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        return userService.insertUser(new User(name, lastName, email, password, false));
    }

    @PutMapping("/users/statusChange/id={userId}")
    public boolean changeUserStatus(
            @PathVariable("userId") int userId
    ){
        return userService.changeStatusUser(userId);
    }

    @DeleteMapping("/users/delete")
    public boolean deleteUserById(
            @RequestParam("userId") int userId
    ){
        return userService.deleteUserById(userId);
    }

    @PostMapping("/tasks/add")
    public Task addTask(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("type") Types types,
            @RequestParam("status") Status status,
            @RequestParam("userId") User user
    ){
        return taskService.addTask(new Task(title, description, types, status, user));
    }

    @GetMapping("/tasks")
    public List<Task> allTasks() {
        return taskService.selectTasksByAddDate();
    }

    @GetMapping("/tasks/search")
    public List<Task> searchTasks(
            @RequestParam("title, status or type") String value
    ){
        return taskService.selectByTitleStatusOrTypes(value);
    }

    @PutMapping("/tasks/changeStatus")
    public boolean changeStatus(
            @RequestParam("taskId") int taskId,
            @RequestParam("status") Status status
    ){
        return taskService.changeStatus(taskId, status);
    }

    @DeleteMapping("/tasks/delete")
    public boolean deleteTask(
            @RequestParam("taskId") int taskId
    ){
        return taskService.deleteTaskById(taskId);
    }
}
