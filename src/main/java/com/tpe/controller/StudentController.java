package com.tpe.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.tpe.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.tpe.domain.Student;
import com.tpe.service.StudentService;
@RestController
@RequestMapping("/students")
public class StudentController {

    // logger objesi olusturalim (org.slf4j.Logger)
    Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;


    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        // web server a bir request geliyor,
        logger.info("----------Welcome {}",request.getServletPath());

        return "Welcome to Student Controller";
    }

    // 1. Islem : Student Create


    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        studentService.createStudent(student);
        Map<String,String> map=new HashMap<>();
        map.put("message", "Student created successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    @PostMapping("/kaydet")
    public void ogrenciKaydet(@Valid @RequestBody Student student) {
        studentService.createStudent(student);
    }

    // 2. Islem : Get all Students

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students);
    }



    // 3. Islem : ID ye gore Get Student by ID

    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @GetMapping("/query")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student= studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student= studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }


    // 4. Islem : ID den delete student
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String,String> map=new HashMap<>();
        map.put("message", "Student delete successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    // 5. ID li student Update

    @PutMapping("{id}")
    public ResponseEntity<Map<String,String>> updateStudent(@PathVariable Long id, @Valid @RequestBody
    StudentDTO studentDTO) {
        studentService.updateStudent(id,studentDTO);
        Map<String,String> map=new HashMap<>();
        map.put("message", "Student update successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    // 6. Sayfali Gosterim

    @GetMapping("/pages")
    public ResponseEntity<Page<Student>> getAllWithPage(@RequestParam("page") int page, @RequestParam("size") int size,
                                                        @RequestParam("sort") String prop, @RequestParam("direction") Sort.Direction direction
    ){
        Pageable pageable=PageRequest.of(page, size, Sort.by(direction,prop));
        Page<Student> studentPage= studentService.getAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);
    }


    // 7. Lastname a sahip olanlari don
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName){
        List<Student> studentList= studentService.findStudents(lastName);
        return ResponseEntity.ok(studentList);
    }

    // 8 grade e esit olanlari
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade){
        List<Student> list= studentService.findAllEqualsGrade(grade);
        return ResponseEntity.ok(list);
    }

    // 9 StudentDTO dan olusmus bir student cagirmak
    @GetMapping("/query/dto")
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id){
        StudentDTO studentDTO= studentService.findStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }

}
