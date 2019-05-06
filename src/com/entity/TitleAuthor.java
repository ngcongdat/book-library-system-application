/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

/**
 *
 * @author tiny
 */
public class TitleAuthor {

    // Declare instances
    private Book book;
    private Author author;
    private int authorOrder;

    // Contructors
    public TitleAuthor() {
    }

    public TitleAuthor(Book book, Author author, int authorOrder) {
        this.book = book;
        this.author = author;
        this.authorOrder = authorOrder;
    }

    // Getter - Setter
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getAuthorOrder() {
        return authorOrder;
    }

    public void setAuthorOrder(int authorOrder) {
        this.authorOrder = authorOrder;
    }

}
