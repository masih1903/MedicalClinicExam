package app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clientName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime time;

    private String comment;

    @ManyToOne
    //@JsonBackReference // Prevents infinite loop by managing this side of the relationship
    private Doctor doctor;

    public Appointment(String clientName, LocalDate date, LocalTime time, String comment) {
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.comment = comment;
    }
}
