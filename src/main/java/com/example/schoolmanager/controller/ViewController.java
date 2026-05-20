package com.example.schoolmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.service.StudentService;

@Controller
public class ViewController {

    private final StudentService service;

    public ViewController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("students", service.getAll());
        model.addAttribute("student", new Student());

        return "students";
    }

    @GetMapping("/students/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students";
        }

        model.addAttribute("students", service.getAll());
        model.addAttribute("student", student);

        return "students";
    }

    @PostMapping("/students/add")
    public String add(@ModelAttribute Student student) {
        service.create(student);

        return "redirect:/students";
    }

    @PostMapping("/students/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Student student) {
        service.update(id, student);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);

        return "redirect:/students";
    }
}
