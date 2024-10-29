package app.controllers;

import app.daos.DoctorMockDAO;
import app.dtos.DoctorDTO;
import app.entities.Message;
import app.enums.Speciality;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DoctorMockController {

    private final DoctorMockDAO doctorMockDAO = new DoctorMockDAO();
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()); // For JSON conversion
    private static final Logger log = LoggerFactory.getLogger(DoctorMockController.class);

    public void getAll(Context ctx) {
        try {
            List<DoctorDTO> doctorDTOList = doctorMockDAO.getAll();
            ctx.res().setStatus(200);
            ctx.json(doctorDTOList);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }

    public void getById(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            DoctorDTO doctorDTO = doctorMockDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(doctorDTO, DoctorDTO.class);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }

    public void getBySpeciality(Context ctx) {
        try {
            String speciality = ctx.pathParam("speciality");
            List<DoctorDTO> doctorDTOList = doctorMockDAO.doctorBySpeciality(Speciality.valueOf(speciality));
            ctx.res().setStatus(200);
            ctx.json(doctorDTOList, DoctorDTO.class);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }

    public void getByBirthDateRange(Context ctx) {
        try {
            LocalDate from = LocalDate.parse(ctx.pathParam("from"));
            LocalDate to = LocalDate.parse(ctx.pathParam("to"));

            List<DoctorDTO> doctorDTOList = doctorMockDAO.doctorByBirthdateRange(from, to);
            ctx.res().setStatus(200);
            ctx.json(doctorDTOList, DoctorDTO.class);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }

    public void createDoctor(Context ctx) {
        try {
            DoctorDTO doctorDTO = objectMapper.readValue(ctx.body(), DoctorDTO.class);
            DoctorDTO newDoctorDTO = doctorMockDAO.createDoctor(doctorDTO);

            ctx.res().setStatus(201);
            ctx.json(newDoctorDTO, DoctorDTO.class);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }

    public void update(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            DoctorDTO doctorDTO = objectMapper.readValue(ctx.body(), DoctorDTO.class);
            DoctorDTO updatedDoctorDTO = doctorMockDAO.update(id, doctorDTO);

            ctx.res().setStatus(200);
            ctx.json(updatedDoctorDTO, DoctorDTO.class);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }

    public void delete(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            doctorMockDAO.delete(id);
            ctx.res().setStatus(204);
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage() + " - Timestamp: " + timestamp));
        }
    }
}
