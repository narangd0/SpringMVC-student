package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.exception.StudentAlreadyExistsException;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<Long, Student> studentMap = new HashMap<>();

    @Override
    public boolean exists(Long id) {
        return studentMap.containsKey(id);
    }

    @Override
    public Student register(String name, String email, int score, String comment) {
        Long id = Long.valueOf(studentMap.size() + 1);

        Student student = new Student(name, email, score, comment);
        student.setId(id);

        studentMap.put(id, student);

        return student;
    }

    @Override
    public Student getStudent(Long id) {
        return exists(id) ? studentMap.get(id) : null;
    }

    @Override
    public void modify(Student student) {
        Student dbStudent = getStudent(student.getId());
        if (Objects.isNull(dbStudent)) {
            throw new StudentNotFoundException();
        }

        dbStudent.setName(student.getName());
        dbStudent.setEmail(student.getEmail());
        dbStudent.setScore(student.getScore());
        dbStudent.setComment(student.getComment());
    }
}
