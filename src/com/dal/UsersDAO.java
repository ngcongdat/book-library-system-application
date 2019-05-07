/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dal;

import com.context.DBContext;
import com.entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiny
 */
public class UsersDAO {

    // Return the list of all users
    public List<Users> selectAll() throws Exception {
        String select = "select * from Users";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        List<Users> users = new ArrayList<>();
        while (rs.next()) {
            String username = rs.getString("username");
            String displayName = rs.getString("displayname");
            String password = rs.getString("password");
            String description = rs.getString("description");
            users.add(new Users(username, displayName, password, description));
        }
        rs.close();
        conn.close();
        return users;
    }

    // Get a user by username, return null if given username does not exits
    public Users getUserByUsername(String username) throws Exception {
        String select = "select * from Users where username = ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        Users u = null;
        if (rs.next()) {
            String displayName = rs.getString("displayname");
            String password = rs.getString("password");
            String description = rs.getString("description");
            u = new Users(username, displayName, password, description);
        }
        rs.close();
        ps.close();
        return u;
    }

    // Return true if a given user is valid (means exists in our system), otherwise return false
    public boolean validUser(Users x) throws Exception {
        List<Users> users = selectAll();
        for (Users u : users) {
            if (u.getUsername().equalsIgnoreCase(x.getUsername()) && u.getPassword().equalsIgnoreCase(x.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
