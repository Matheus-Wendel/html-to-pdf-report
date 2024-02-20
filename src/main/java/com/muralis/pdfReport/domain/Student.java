package com.muralis.pdfReport.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class Student implements Serializable {
    private String id;
    private String name;
    // standard contructors, getters, and setters
}