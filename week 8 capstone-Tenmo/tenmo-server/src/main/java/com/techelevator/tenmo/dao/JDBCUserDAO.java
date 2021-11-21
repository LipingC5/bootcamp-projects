package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCUserDAO implements UserDAO{

    private JdbcTemplate jdbcTemplate;

    public JDBCUserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        Integer userId = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return userId;
    }

    @Override
    public List<User> userList(String userName){
        List<User> users = new ArrayList<>();
        String sql = "SELECT accounts.account_id, users.user_id, username FROM users " +
                "JOIN accounts ON accounts.user_id = users.user_id WHERE username != ? ";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userName);

        while(rows.next()){
            users.add(mapRowToUser(rows));
        }
        return users;
    }

    private User mapRowToUser(SqlRowSet rows){
        User user = new User();
        user.setAccountId(rows.getInt("account_id"));
        user.setUserId(rows.getInt("user_id"));
        user.setUserName(rows.getString("username"));
        return user;
    }
}
