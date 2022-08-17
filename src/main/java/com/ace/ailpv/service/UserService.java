package com.ace.ailpv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean checkLogin(String id, String password) {
        return userRepository.existsByIdAndPassword(id, password);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllStudents() {
        List<User> studentList = getAllUsers().stream()
                .filter(user -> user.getRole().equals("student")).collect(Collectors.toList());
        return studentList;
    }

    public List<User> getAllTeachers() {
        List<User> teacherList = getAllUsers().stream()
                .filter(user -> user.getRole().equals("teacher")).collect(Collectors.toList());
        return teacherList;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public boolean checkUserId(String id) {
        return userRepository.existsById(id);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public List<User> findUserByBatchId(Long id) {
        return userRepository.findByBatchList_id(id);
    }

    public List<User> getStudentListByTeacherId(String id) {
        User resUser = getUserById(id);
        Set<Batch> bathList = resUser.getBatchList();
        List<User> userList = new ArrayList<>();
        for (Batch batch : bathList) {
            for (User user : batch.getUserList()) {
                userList.add(user);
            }
        }
        List<User> stduentList = userList.stream()
                .filter(user -> user.getRole().equals("student"))
                .collect(Collectors.toList());
        return stduentList;
    }

}