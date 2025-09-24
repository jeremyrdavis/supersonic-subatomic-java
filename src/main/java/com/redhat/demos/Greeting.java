package com.redhat.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Greeting extends PanacheEntity {

    String text;

    String author;

    public Greeting() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", id=" + id +
                '}';
    }



}
