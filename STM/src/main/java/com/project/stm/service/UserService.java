package com.project.stm.service;


import com.project.stm.model.Task;
import com.project.stm.model.User;
import com.project.stm.repository.TaskRepository;
import com.project.stm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    //CREATE NEW USER
    public User insertUser(User user){
        return (User) userRepository.save(user);
    }

    //RETURN ALL USERS
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    //SEARCH BY PK OR EMAIL
    public Optional<User> getUserByIdOrEmail(String value){
        if(value.matches("[0-9]+")){
            return userRepository.findById(Integer.parseInt(value));
        } else {
            return userRepository.findByEmail(value);
        }
    }

    //UPDATE STATUS
    public boolean changeStatusUser(int userId){
        boolean isChanged = false;
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User userToChange = userOptional.get();
            if(userToChange.isStatus()){
                userToChange.setStatus(false);
                userRepository.save(userToChange);
                isChanged = true;
            }else {
                userToChange.setStatus(true);
                userRepository.save(userToChange);
                isChanged = true;
            }
        }
        return isChanged;
    }

    //DELETE USER FROM RELATIONS
    public boolean deleteUserById(int userId){
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        userRepository.findById(userId).ifPresent(user -> {
            for(Task task : taskRepository.findTasksUser(userId)) {
                taskRepository.delete(task);
            }
            userRepository.deleteById(userId);
            isDeleted.set(true);
        });
        return isDeleted.get();
    }
}
