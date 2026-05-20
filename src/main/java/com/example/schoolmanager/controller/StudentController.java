package com.example.schoolmanager.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.service.StudentService;

import jakarta.validation.Valid;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // =========================
    // HOME PAGE
    // =========================
    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {

        Page<Student> students = service.getStudents(keyword, page, 5);

        model.addAttribute("students", students);

        model.addAttribute("student", new Student());

        model.addAttribute("keyword", keyword);

        return "index";
    }

    // =========================
    // ADD STUDENT
    // =========================
    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute Student student,
            BindingResult result,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        // VALIDATION
        if (result.hasErrors()) {

            Page<Student> students = service.getStudents(keyword, page, 5);

            model.addAttribute("students", students);

            model.addAttribute("keyword", keyword);

            return "index";
        }

        // SAVE
        service.create(student);

        // SUCCESS MESSAGE
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "🎉 Student added successfully!"
        );

        return "redirect:/";
    }

    // =========================
    // UPDATE STUDENT
    // =========================
    @PostMapping("/update/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute Student student,
            BindingResult result,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        // VALIDATION
        if (result.hasErrors()) {

            Page<Student> students = service.getStudents(keyword, page, 5);

            model.addAttribute("students", students);

            model.addAttribute("student", student);

            model.addAttribute("keyword", keyword);

            model.addAttribute("editError", true);

            model.addAttribute(
                    "errorMessage",
                    "❌ Update failed. Please check the information again."
            );

            return "index";
        }

        // UPDATE
        service.update(id, student);

        // SUCCESS MESSAGE
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "✅ Student updated successfully!"
        );

        return "redirect:/";
    }

    // =========================
    // DELETE STUDENT
    // =========================
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        service.delete(id);

        // SUCCESS MESSAGE
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "🗑 Student deleted successfully!"
        );

        return "redirect:/";
    }
}
