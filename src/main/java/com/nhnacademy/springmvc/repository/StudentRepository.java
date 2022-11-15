package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;

public interface StudentRepository {
    boolean exists(Long id);
    Student register(String name, String email, int score, String comment);
    Student getStudent(Long id);
    void modify(Student student);
}
