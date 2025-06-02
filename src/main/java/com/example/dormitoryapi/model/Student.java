package com.example.dormitoryapi.model;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;

    private String studentYear;

    public Student() {
    }

    public Student(String firstname, String lastname, String email, String studentYear) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.studentYear = studentYear;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setstudentYear(String studentYear) {
        this.studentYear = studentYear;
    }
}
