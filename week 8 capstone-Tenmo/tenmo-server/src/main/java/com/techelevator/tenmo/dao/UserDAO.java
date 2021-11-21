package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDAO {

    int findIdByUsername(String username);
    List<User> userList(String userName);

}
