package com.project.stm.controller;

import com.project.stm.model.Task;
import com.project.stm.model.User;
import com.project.stm.model.enums.Status;
import com.project.stm.service.TaskService;
import com.project.stm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class FrontEndController {
    UserService userService;
    TaskService taskService;

    @Autowired
    public FrontEndController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    //Wyswietlenie wszystkich opcji w przeglądarce
    @GetMapping("/home")
    public void home(){
    }

    //Wszyscy użytkownicy
    @GetMapping("/allUsers")
    public String users(Model model){
        model.addAttribute("allUsers", userService.allUsers());
        return "users";
    }

    //Dodawanie użytkownika - wywołanie strony
    @GetMapping("/addUserPage")
    public void registerPage(Model model){
        model.addAttribute("userReg", new User());
    }

    //Dodawanie użytkownika - dopisanie do bazy
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user){
        user.setRegistrationDateTime(LocalDateTime.now());
        userService.insertUser(user);
        return "redirect:/allUsers";
    }

    //Zmiana statusu
    @GetMapping("/statusUser")
    public String statusUser(@RequestParam(required = false, value="userId") Integer userId){
        if(userId != null){
            userService.changeStatusUser(userId);
            return "redirect:/allUsers";
        }
        return null;
    }

    //Wyszukanie użytkownika
    @GetMapping("/searchUser")
    public String searchUser(@RequestParam (required = false, value="userIdOrEmail") String IdOrEmail, Model model) {
        if (IdOrEmail != null) {
            model.addAttribute("user", userService.getUserByIdOrEmail(IdOrEmail));
        }
        return "searchUser";
    }

    //Usunięcie użytkownika
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam(required = false, value="userId") Integer userId){
        if (userId != null) {
            userService.deleteUserById(userId);
            return "redirect:/allUsers";
        }
        return null;
    }

    //Wyświetlenie wszystkich zadań
    @GetMapping("/allTasks")
    public String tasks(Model model){
        model.addAttribute("allTasks", taskService.selectTasksByAddDate());
        return "tasks";
    }

    //Dodanie nowego zadania - wywołanie strony
    @GetMapping("/addTaskPage")
    public void addTaskPage(Model model){
        model.addAttribute("addTask", new Task());
    }

    //Dodanie nowego zadania - dopisanie do bazy
    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task){
        taskService.addTask(task);
        return "redirect:/allTasks";
    }

    //Wyszukanie zadania
    @GetMapping("/searchTask")
    public String searchTask(@RequestParam (required = false, value="TitleStatusOrTypes") String TitleStatusOrTypes, Model model) {
        if (TitleStatusOrTypes != null) {
            model.addAttribute("task", taskService.selectByTitleStatusOrTypes(TitleStatusOrTypes));
        }
        return "searchTask";
    }

    //Usuwanie zadania
    @GetMapping("/deleteTask")
    public String deleteTask(@RequestParam(required = false, value="taskId") Integer taskId){
        if (taskId != null){
            taskService.deleteTaskById(taskId);
            return "redirect:/allTasks";
        }
        return null;
    }

    //Zmiana statusu zadania
    @GetMapping("/statusTask")
    public String statusTask(@RequestParam(required = false) Integer taskId, Status status){
        if(taskId != null){
            taskService.changeStatus(taskId, status);
            return "redirect:/allTasks";
        }
        return null;
    }
}
