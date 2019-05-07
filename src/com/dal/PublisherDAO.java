/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dal;

import com.context.DBContext;
import com.entity.Publisher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiny
 */
public class PublisherDAO {

    // Return the list of all publishers
    public List<Publisher> selectAll() throws Exception {
        String select = "select * from publishers";
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        List<Publisher> publishers = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("pub_id");
            String name = rs.getString("pub_name");
            String address = rs.getString("pub_address");
            publishers.add(new Publisher(id, name, address));
        }
        rs.close();
        ps.close();
        return publishers;
    }

    // Return information of a Publisher by publisher id, 
    // Return null if a given publisher id does not exists
    public Publisher getPublisherByID(String pid) throws Exception {
        List<Publisher> publishers = selectAll();
        for (Publisher p : publishers) {
            if (p.getId().equalsIgnoreCase(pid)) {
                return p;
            }
        }
        return null;
    }

}
