package com.nhnacademy.springmvc.domain;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Value
public class StudentRequest {
    @NotBlank
    String name;

    @Email
    String email;

    @Min(0)
    @Max(100)
    int score;

    @NotBlank
    @Size(max = 200)
    String comment;
}