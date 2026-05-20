package com.example.schoolmanager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // lấy sinh viên theo từ khóa và phân trang
    public Page<Student> getStudents(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (keyword == null || keyword.isBlank()) {

            return repo.findAll(pageable);
        }

        return repo.findByNameContainingIgnoreCase(keyword, pageable);
    }

    // lấy tất cả sinh viên
    public List<Student> getAll() {

        return repo.findAll();
    }

    // thêm sinh viên
    public Student create(Student student) {

        return repo.save(student);
    }

    // xóa sinh viên
    public void delete(Long id) {

        repo.deleteById(id);
    }

    // tìm theo id
    public Student getById(Long id) {

        return repo.findById(id).orElse(null);
    }

    // cập nhật sinh viên
    public Student update(Long id, Student student) {

        Student existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setMajor(student.getMajor());

        existing.setEmail(student.getEmail());
        existing.setPhone(student.getPhone());
        existing.setAddress(student.getAddress());

        return repo.save(existing);
    }
}
