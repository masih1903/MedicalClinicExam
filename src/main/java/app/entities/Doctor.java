package app.entities;

import app.dtos.DoctorDTO;
import app.enums.Speciality;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private int yearOfGraduation;

    private String nameOfClinic;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    //@JsonManagedReference // Prevents infinite loop by managing this side of the relationship
    private List<Appointment> appointments = new ArrayList<>();


    public Doctor(String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Speciality speciality) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;
    }

    public Doctor(DoctorDTO doctorDTO) {
        this.name = doctorDTO.getName();
        this.dateOfBirth = doctorDTO.getDateOfBirth();
        this.yearOfGraduation = doctorDTO.getYearOfGraduation();
        this.nameOfClinic = doctorDTO.getNameOfClinic();
        this.speciality = doctorDTO.getSpeciality();

    }

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = LocalDateTime.now();
    }
}
