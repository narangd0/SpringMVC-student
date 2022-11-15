package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRequest;
import com.nhnacademy.springmvc.exception.StudentNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("student")
    public Student getStudent(@PathVariable(name = "studentId") Long id) {
        Student student = studentRepository.getStudent(id);

        if (Objects.isNull(student)) {
            throw new StudentNotFoundException();
        }

        return student;
    }

    @GetMapping("/{studentId}")
    public String viewStudent(@ModelAttribute Student student, ModelMap modelMap) {
        modelMap.put("student", student);
        return "studentView";
    }

    /**
     * 점수 및 평가에 대한 항목 출력하지 않기
     * @param student
     * @param model
     * @return
     */
    @GetMapping(value = "{studentId}", params = {"hideScore=yes"})
    public String viewStudentHideScore(@ModelAttribute Student student, Model model) {
        model.addAttribute("student", student);
        return "studentViewHideScore";
    }

    @GetMapping("/{studentId}/modify")
    public String studentModifyForm(@ModelAttribute Student student, Model model) {
        model.addAttribute("student", student);
        return "studentModify";
    }

    @PostMapping("/{studentId}/modify")
    public ModelAndView modifyUser(@ModelAttribute Student student,
                                   @Valid @ModelAttribute StudentRequest studentRequest,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setScore(studentRequest.getScore());
        student.setComment(studentRequest.getComment());

        studentRepository.modify(student);

        ModelAndView mav = new ModelAndView("studentView");
        mav.addObject("student", student);
        return mav;
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(StudentNotFoundException ex, Model model) {
        model.addAttribute("exception", ex);
        log.error("", ex);
        return "error";
    }
}