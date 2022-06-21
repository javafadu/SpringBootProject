package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    public List<Student> getAll();
    public List<Student> findStudents(String lastName);

    Student findStudent(Long id) throws ResourceNotFoundException;
    void createStudent(Student student);

    void updateStudent(Long id, StudentDTO student);
    void deleteStudent(Long id);

    Page<Student> getAllWithPage(Pageable pageable);

    List<Student> findAllEqualsGrade(Integer grade);

    StudentDTO findStudentDTOById (Long id);

}