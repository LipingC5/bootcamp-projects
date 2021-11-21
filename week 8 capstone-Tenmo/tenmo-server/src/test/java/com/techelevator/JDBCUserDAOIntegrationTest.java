package com.techelevator;

import com.techelevator.tenmo.dao.JDBCUserDAO;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JDBCUserDAOIntegrationTest extends DAOIntegrationTest {

    private JdbcTemplate jdbcTemplate;
    private JDBCUserDAO userDAO;

    private static final String PASSWORD = "IjoiUk9MRV9VU0VSI";
    private static final double BALANCE = 123;

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
        userDAO = new JDBCUserDAO(jdbcTemplate);
    }

    @Override
    protected DataSource getDataSource() {
        return super.getDataSource();
    }

    @Test
    public void find_id_by_username(){
        User user = insertTestUser("Jack");
        int actualUserId = userDAO.findIdByUsername("Jack");
        Assert.assertEquals(user.getUserId(), actualUserId);
    }

    @Test
    public void user_list_compare_by_size(){

        insertUserWithAccountId("A");
        int listSize = userDAO.userList("A").size();
        insertUserWithAccountId("B");
        insertUserWithAccountId("C");

        List<User> actualList = userDAO.userList("A");

        Assert.assertEquals( listSize + 2, actualList.size());
    }

    private User insertTestUser(String name){
        User user = new User();
        user.setUserName(name);
        String sql = "INSERT INTO users (user_id, username, password_hash) " +
                "VALUES(DEFAULT, ?, ?) RETURNING user_id ";
        Integer userId = jdbcTemplate.queryForObject(sql, Integer.class, name, PASSWORD);
        user.setUserId(userId);
        return user;
    }

    private User insertUserWithAccountId(String name){

        String sql = "INSERT INTO users (user_id, username, password_hash) " +
                "VALUES(DEFAULT, ?, ?) RETURNING user_id ";
        Integer userId = jdbcTemplate.queryForObject(sql, Integer.class, name, PASSWORD);

        String sqlAccount = "INSERT INTO accounts (account_id, user_id, balance) VALUES (DEFAULT, ?, ?) RETURNING account_id";
        Integer accountId = jdbcTemplate.queryForObject(sqlAccount, Integer.class, userId, BALANCE);

        User user = new User();
        user.setUserName(name);
        user.setUserId(userId);
        user.setAccountId(accountId);
        return user;
    }

}
