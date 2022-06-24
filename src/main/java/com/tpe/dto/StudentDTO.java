package com.tpe.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tpe.domain.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

	private Long id;
	
	@NotNull(message="First Name can not be null")
	@NotBlank(message="First Name can not be white space")
	@Size(min=3,max=100, message="First Name '${validatedValue}' must be between {min} and {max} chars long")
	private String firstName;
	

	@NotNull(message="Last Name can not be null")
	@NotBlank(message="Last Name can not be white space")
	@Size(min=1,max=100, message="Last Name '${validatedValue}' must be between {min} and {max} chars long")
	private String lastName;
	
	private Integer grade;
	
	
	//555-555-5555
	//(555).555.5555
	//555 555 5555
	//55555 5555
	@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Please provide valid phone number")
	private String phoneNumber;
	
	@Email(message="Provide valid email")
	private String email;
	
	
	private LocalDateTime createDate=LocalDateTime.now();
	
	
	public StudentDTO(Student student) {
		this.id=student.getId();
		this.firstName=student.getFirstName();
		this.lastName=student.getLastName();
		this.grade=student.getGrade();
		this.email=student.getEmail();
		this.phoneNumber=student.getPhoneNumber();
		this.createDate=student.getCreateDate();
	}
	
	
	
	
	
	
	
	
	
	
}
