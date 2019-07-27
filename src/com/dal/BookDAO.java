/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dal;

import com.context.DBContext;
import com.entity.Author;
import com.entity.Book;
import com.entity.Publisher;
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
public class BookDAO {

  // Delete a book by a given bookID
  public void deleteBook(String bookID) throws Exception {
    // Firstly delete that book from TitleAuthor
    String deleteFirst = "delete from TitleAuthor where title_id = ?";
    Connection conn = new DBContext().getConnection();
    PreparedStatement ps = conn.prepareStatement(deleteFirst);
    // Specify the value for parameter
    ps.setString(1, bookID);
    ps.executeUpdate();
    ps.close();
    // Secondly delete from Book
    String deleteSecond = "delete from Books where title_id = ?";
    ps = conn.prepareStatement(deleteSecond);
    ps.setString(1, bookID);
    ps.executeUpdate();
    ps.close();
    conn.close();
  }

  // Update a new book to database
  public void editBook(Book b) throws Exception {
    String insert = "Update Books set title = ?, pub_id = ?, notes = ? where title_id = ?";
    Connection conn = new DBContext().getConnection();
    PreparedStatement ps = conn.prepareStatement(insert);
    // Specify the values for parameter
    ps.setString(1, b.getTitle());
    ps.setString(2, b.getPub().getId());
    ps.setString(3, b.getNote());
    ps.setString(4, b.getId());
    ps.executeUpdate();
    ps.close();
    // Secondly update TitleAuthor, remove all old authors of given book
    String delete = "Delete from TitleAuthor where title_id = ?";
    ps = conn.prepareStatement(delete);
    ps.setString(1, b.getId());
    ps.executeUpdate();
    ps.close();
    // Update new authors of given book
    List<Author> authors = b.getAuthors();
    for (int i = 0; i < authors.size(); i++) {
      Author a = authors.get(i);
      insert = "insert into TitleAuthor values(?,?,?)";
      PreparedStatement p = conn.prepareStatement(insert);
      p.setString(1, a.getId());
      p.setString(2, b.getId());
      p.setInt(3, i);
      p.executeUpdate();
      p.close();
    }
    conn.close();
  }

  // Insert a new book to database
  public void addBook(Book b) throws Exception {
    // Firstly insert into Book table
    String insert = "insert into Books values(?,?,?,?,?)";
    Connection conn = new DBContext().getConnection();
    PreparedStatement ps = conn.prepareStatement(insert);
    // Specify the value for parameter
    ps.setString(1, b.getId());
    ps.setString(2, b.getTitle());
    ps.setString(3, b.getPub().getId());
    ps.setString(4, b.getNote());
    ps.setString(5, b.getUser().getUsername());
    ps.executeUpdate();
    ps.close();
    // Secondly insert into TitleAuthor, because a book is written by many authors
    List<Author> authors = b.getAuthors();
    for (int i = 0; i < authors.size(); i++) {
      Author a = authors.get(i);
      insert = "insert into TitleAuthor values(?,?,?)";
      PreparedStatement p = conn.prepareStatement(insert);
      p.setString(1, a.getId());
      p.setString(2, b.getId());
      p.setInt(3, i);
      p.executeUpdate();
      p.close();
    }
    conn.close();
  }

  // Return information of a Book of a given bookID
  // Return null if a given bookID does not exists
  public Book getBookByBookID(String bookID) throws Exception {
    String select = "select * from Books where title_id = ?";
    Connection conn = new DBContext().getConnection();
    PreparedStatement ps = conn.prepareStatement(select);
    // Specify the values for paramater
    ps.setString(1, bookID);
    ResultSet rs = ps.executeQuery();
    // Use to get the information of publishers of the book
    PublisherDAO pubDAO = new PublisherDAO();
    // Use to get the information of a list of authors of the book
    AuthorDAO authorDAO = new AuthorDAO();
    // Use to get information of user
    UsersDAO usersDAO = new UsersDAO();
    Book b = null;
    while (rs.next()) {
      String id = rs.getString("title_id");
      String title = rs.getString("title");
      String pubID = rs.getString("pub_id");
      String note = rs.getString("notes");
      String username = rs.getString("username");
      // Get publisher of the book
      Publisher pub = pubDAO.getPublisherByID(pubID);
      // Get user of the book
      Users user = usersDAO.getUserByUsername(username);
      // Get list of authors of the book
      List<Author> authors = authorDAO.selectAuthorsByBookID(id);
      b = new Book(id, title, note, pub, authors, user);
    }
    rs.close();
    conn.close();
    return b;
  }

  // Return the list of books - use for searching, need to join all given tables except Users
  public List<Book> select(String columnName, String keyword) throws Exception {
    String select = "Select distinct Books.* from Books, Publishers, Authors, TitleAuthor where "
            + " books.pub_id = Publishers.pub_id and books.title_id = TitleAuthor.title_id and TitleAuthor.au_id = Authors.au_id and ";
    select += columnName + " like '%" + keyword + "%'";
    Connection conn = new DBContext().getConnection();
    PreparedStatement ps = conn.prepareStatement(select);
    ResultSet rs = ps.executeQuery();
    List<Book> books = new ArrayList<>();
    // Use to get information of a publisher of the book
    PublisherDAO pubDAO = new PublisherDAO();
    // Use to get information of a list of authors of the book
    AuthorDAO authorDAO = new AuthorDAO();
    while (rs.next()) {
      String id = rs.getString("title_id");
      String title = rs.getString("title");
      String pubID = rs.getString("pub_id");
      String note = rs.getString("notes");
      // Get publisher of the book
      Publisher pub = pubDAO.getPublisherByID(pubID);
      // Get list of authors of the book
      List<Author> authors = authorDAO.selectAuthorsByBookID(id);
      books.add(new Book(id, title, note, pub, authors));
    }
    rs.close();
    conn.close();
    return books;
  }

  // Return the list of all books
  public List<Book> selectAll() throws Exception {
    String select = "select * from Books";
    Connection conn = new DBContext().getConnection();
    PreparedStatement ps = conn.prepareStatement(select);
    ResultSet rs = ps.executeQuery();
    List<Book> books = new ArrayList<>();
    // Use to get information of a publisher of the book
    PublisherDAO pubDAO = new PublisherDAO();
    // Use to get information of a list of authors of the book
    AuthorDAO authorDAO = new AuthorDAO();
    while (rs.next()) {
      String id = rs.getString("title_id");
      String title = rs.getString("title");
      String pubID = rs.getString("pub_id");
      String note = rs.getString("notes");
      // Get publisher of the book
      Publisher pub = pubDAO.getPublisherByID(pubID);
      // Get list of authors of the book
      List<Author> authors = authorDAO.selectAuthorsByBookID(id);
      books.add(new Book(id, title, note, pub, authors));
    }
    rs.close();
    conn.close();
    return books;
  }

}
