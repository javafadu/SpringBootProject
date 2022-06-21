package com.tpe.dto;

import com.tpe.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="First Name can not be null")
    @NotBlank(message="First Name can not be white space")
    @Size(min=1,max=100, message="First Name '${validatedValue}' must be between {min} and {max} chars long")
    private String firstName;

    @NotNull(message="Last Name can not be null")
    @NotBlank(message="Last Name can not be white space")
    @Size(min=1,max=100, message="Last Name '${validatedValue}' must be between {min} and {max} chars long")
    private String lastName;

    private Integer grade;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Please provide valid phone number")
    private String phoneNumber;

    @Email(message="Provide valid email")
    private String email;


    private LocalDateTime createDate=LocalDateTime.now();


    // kendi yazacagimiz bi constructor ile veritabanindan direk

    public StudentDTO(Student student) {
        this.id= student.getId();
        this.firstName=student.getFirstName();
        this.lastName=student.getLastName();
        this.grade=student.getGrade();
        this.email=student.getEmail();
        this.phoneNumber=student.getPhoneNumber();
        this.createDate=student.getCreatedDate();

    }

}
