package com.project.stm.service;

import com.project.stm.model.Task;
import com.project.stm.model.User;
import com.project.stm.model.enums.Status;
import com.project.stm.model.enums.Types;
import com.project.stm.repository.TaskRepository;
import com.project.stm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    //CREATE NEW TASK
    public Task addTask(Task task){
        Optional<User> userOptional = userRepository.findById(task.getAuthor().getUserId());
        return userOptional.map(user ->
            taskRepository.save(task)
        ).orElse(null);
    }

    //SORT BY DATE DESC
    public List<Task> selectTasksByAddDate(){
        return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "dateAdded"));
    }

    //SEARCH BY NAME, STATUS OR TYPE
    public List<Task> selectByTitleStatusOrTypes(String value){
        if(value.equalsIgnoreCase(Status.DONE.getStatusName()) || value.equalsIgnoreCase(Status.NEW.getStatusName()) || value.equalsIgnoreCase(Status.INPROGRESS.getStatusName())){
            value = value.toUpperCase();
            Status status = Status.valueOf(value);
            return taskRepository.findTasksByStatus(status);
        }else if(value.equalsIgnoreCase(Types.BUG.getTypesName()) || value.equalsIgnoreCase(Types.FEATURES.getTypesName()) ||
                value.equalsIgnoreCase(Types.TASK.getTypesName())){
            value = value.toUpperCase();
            Types types = Types.valueOf(value);
            return taskRepository.findTasksByTypes(types);
        }else{
            return taskRepository.findTasksByTitle(value);
        }
    }

    //CHANGE STATUS BY OTHER
    public boolean changeStatus(int taskId, Status status){
        boolean isChanged = false;
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(taskOptional.isPresent()){
            Task taskToChange = taskOptional.get();
            if(taskToChange.getStatus() == (status)) {
                isChanged = false;
            } else {
                taskToChange.setStatus(status);
                taskRepository.save(taskToChange);
                isChanged = true;
            }
        }
        return isChanged;
    }

    //DELETE ONLY TASK
    public boolean deleteTaskById(int taskId){
        AtomicBoolean isDeleted = new AtomicBoolean(false);
        taskRepository.findById(taskId).ifPresent(task -> {
            taskRepository.deleteById(taskId);
            isDeleted.set(true);
        });
        return isDeleted.get();
    }
}
