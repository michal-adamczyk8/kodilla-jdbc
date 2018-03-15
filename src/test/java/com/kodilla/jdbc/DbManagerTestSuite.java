package com.kodilla.jdbc;

import com.mysql.cj.xdevapi.SqlDataResult;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManagerTestSuite {
    @Test
    public void testConnect() throws SQLException {
        //Given
        //When
        DbManager dbManager = DbManager.getInstance();
        //Then
        Assert.assertNotNull(dbManager.getConnection());
    }

    @Test
    public void testSelectUsers() throws SQLException {
        //Given
        DbManager dbManager = DbManager.getInstance();

        //When
        String sqlQuery = "SELECT * FROM USERS";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);

        //Then
        int counter = 0;
        while(rs.next()) {
            System.out.println(rs.getInt("ID") + ", " +
            rs.getString("FIRST_NAME") + ", " +
            rs.getString("LAST_NAME"));
        counter ++;
        }
        rs.close();
            statement.close();
        Assert.assertEquals(5, counter);
    }

    @Test
    public void testSelectUsersAndPosts() throws SQLException {
        //Given
        DbManager dbManager = DbManager.getInstance();

        //When
        String sqlQuery = "SELECT FIRST_NAME, LAST_NAME \n" +
                "FROM USERS, POSTS\n" +
                "WHERE USERS.ID = POSTS.USER_ID\n" +
                "GROUP BY  POSTS.USER_ID\n" +
                "HAVING COUNT(*) >= 2;";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);

        //Then
        int counter = 0;
        while(rs.next()) {
            System.out.println(rs.getString("FIRST_NAME") + ", " +
            rs.getString("LAST_NAME"));
            counter ++;
        }
        rs.close();
        statement.close();
        Assert.assertEquals(2, counter);
    }
}
