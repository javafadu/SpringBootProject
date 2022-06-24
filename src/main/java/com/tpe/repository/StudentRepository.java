package com.tpe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;

@Repository

//Repository interface
//CRUDRepository-CRUD Operasyonları
//JPARepository-->CRUD + PagingAndSortingRepository
//PagingAndSortingRepository-->Paging ve Sorting
public interface StudentRepository extends JpaRepository<Student, Long> {

	//@Query("SELECT new com.tpe.dto.StudentDTO(s.id,concat(s.firstName, ' ', s.lastName),s.grade,s.email,s.phoneNumber, s.createdDate) FROM Student s WHERE s.id = :id")
	
	
	@Query("SELECT s from Student s WHERE s.grade=:pGrade")
	List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);
	
	@Query(value="SELECT * from Student s WHERE s.grade=:pGrade",nativeQuery = true)
	List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);
	
	@Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id")
	Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);
	
	
	List<Student> queryByLastName(String lastName);
	
	Boolean existsByEmail(String email) throws ConflictException;
	
}
