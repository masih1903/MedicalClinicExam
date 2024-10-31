package app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AppointmentDTO {

    private Integer id;
    private String clientName;
    private LocalDate date;
    private LocalTime time;
    private String comment;
}
