package app.dtos;

import app.entities.Doctor;
import app.enums.Speciality;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DoctorDTO {

    private Integer id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private int yearOfGraduation;
    private String nameOfClinic;
    private Speciality speciality;

    public DoctorDTO(Integer id, String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Speciality speciality) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;
    }

    public DoctorDTO(String name, LocalDate dateOfBirth, int yearOfGraduation, String nameOfClinic, Speciality speciality) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.yearOfGraduation = yearOfGraduation;
        this.nameOfClinic = nameOfClinic;
        this.speciality = speciality;
    }

    public DoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.dateOfBirth = doctor.getDateOfBirth();
        this.yearOfGraduation = doctor.getYearOfGraduation();
        this.nameOfClinic = doctor.getNameOfClinic();
        this.speciality = doctor.getSpeciality();
    }

    public static List<DoctorDTO> toDoctorDTOList(List<Doctor> doctors) {
        return doctors.stream().map(DoctorDTO::new).collect(Collectors.toList());
    }
}
