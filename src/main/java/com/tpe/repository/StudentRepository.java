package com.tpe.repository;
import java.util.List;
import java.util.Optional;

import com.tpe.dto.StudentDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
@Repository
// Repository interface
// CRUD Repository --> CRUD Operations
// JPA Repository --> CRUD + PagingAndSorting
// Paging And Sorting Repository --> Paging and Sorting Operations
public interface StudentRepository extends JpaRepository<Student, Long>{

   @Query("SELECT s from Student s Where s.grade=:pGrade")
   List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

   // SQL ile grade e esit olan ogrencileri almak icin yukarki yerine asagidaki gibi olur
   @Query(value="SELECT * from Student s Where s.grade=:pGrade",nativeQuery = true)
   List<Student> findAllEqualsGradeWithSql(@Param("pGrade") Integer grade);

   @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id ")
   Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);

   public List<Student> findByLastName(String lastName);

   public Boolean existsByEmail(String email) throws ConflictException;



   // public List<Student> findBy

}