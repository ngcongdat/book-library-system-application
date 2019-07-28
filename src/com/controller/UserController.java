/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dal.UsersDAO;
import com.entity.Users;

/**
 *
 * @author tiny
 */
public class UserController {

  private UsersDAO usersDAO;

  public UserController() {
    usersDAO = new UsersDAO();
  }

  public void add(Users u) throws Exception {
    usersDAO.addUser(u);
  }

}
