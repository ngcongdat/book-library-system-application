/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.dal.BookDAO;
import com.entity.Book;
import javax.swing.JTable;

/**
 *
 * @author tiny
 */
public class BookController {
    
    private BookDAO bookDAO;
    
    //ctor
    public BookController() {
        bookDAO = new BookDAO();
    }
    
    //add a new book to database
    public void add(Book b) throws Exception {
        bookDAO.addBook(b);
    }
    
    //output list of all available books to jtable
    public void list(JTable tblBook) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    
    //search and output the list of all availables books to jtable
    public void search(String column, String keyword,JTable tblBook) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    
    //return information of a Book by Book ID
    public Book getBookByBookID(String bookID)throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    
    //update information of a Book
    public void editBook(Book b)throws Exception  {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    
    //delete a book by BookID
    public void deleteBook(String bookID)throws Exception  {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }
    
}
