package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.exceptions.AccountNotFoundException;
import com.techelevator.tenmo.exceptions.UserNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TenmoController {

    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public TenmoController(AccountDAO accountDAO, UserDAO userDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(path="/accounts", method= RequestMethod.GET)
    public Account getBalance(Principal principal) throws AccountNotFoundException {
        int userId = userDAO.findIdByUsername(principal.getName());
        Account account = accountDAO.getBalance(userId, principal.getName());
        if(account == null){
           throw new AccountNotFoundException();
        }
        return account ;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsersList(Principal principal) throws UserNotFoundException {
        List<User> users = userDAO.userList(principal.getName());
        if(users == null){
            throw new UserNotFoundException();
        }
        return users;
    }

}
