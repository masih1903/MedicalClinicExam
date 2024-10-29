package app.controllers;

import app.daos.DoctorDAO;
import app.dtos.DoctorDTO;
import app.entities.Doctor;
import app.entities.Message;
import app.enums.Speciality;
import app.exceptions.ApiException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DoctorController implements IController {

    private final Logger log = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorDAO doctorDAO;

    public DoctorController(DoctorDAO doctorDao) {
        this.doctorDAO = doctorDao;
    }

    @Override
    public void getAll(Context ctx) {

        try {
            // == querying ==
            List<Doctor> doctors = doctorDAO.getAll();

            // == response ==
            List<DoctorDTO> doctorDTOs = DoctorDTO.toDoctorDTOList(doctors);

            ctx.res().setStatus(200);
            ctx.json(doctorDTOs, DoctorDTO.class);
        }

        catch (ApiException e) {
            ctx.status(500).json(new Message(500, e.getMessage(), e.getTimeStamp()));
        }

        catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }

    @Override
    public void getById(Context ctx) {

        try {
            // == request ==
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            // == querying ==
            Doctor doctor = doctorDAO.getById(id);

            // == response ==
            DoctorDTO countryDTO = new DoctorDTO(doctor);
            ctx.res().setStatus(200);
            ctx.json(countryDTO, DoctorDTO.class);
        }

        catch (ApiException e) {
            ctx.status(500).json(new Message(500, e.getMessage(), e.getTimeStamp()));
        }

        catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }

    @Override
    public void doctorBySpecialty(Context ctx) {

        try {
            // == request ==
            String specialityParam = ctx.pathParam("speciality"); // Assuming speciality is in the path parameter
            Speciality speciality = Speciality.valueOf(specialityParam.toUpperCase()); // Convert to enum if necessary

            // == querying doctors by speciality ==
            List<Doctor> doctors = doctorDAO.doctorBySpeciality(speciality);

            if (doctors.isEmpty()) {
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                ctx.res().setStatus(404);
                ctx.json(new Message(404, "No doctors found for the given speciality", timeStamp), Message.class);
            } else {
                ctx.res().setStatus(200);
                ctx.json(doctors);
            }

        }
        catch (IllegalArgumentException e) {
            log.error("Invalid speciality format: {}", e.getMessage());
            throw new ApiException(400, "Invalid speciality format");
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }


    @Override
    public void doctorByBirthdayRange(Context ctx) {

        try {
            // == request ==
            LocalDate fromDate = LocalDate.parse(ctx.pathParam("from"));
            LocalDate toDate = LocalDate.parse(ctx.pathParam("to"));

            // == querying doctors by birthdate range ==
            List<Doctor> doctors = doctorDAO.doctorByBirthdateRange(fromDate, toDate);

            if (doctors.isEmpty()) {
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                ctx.res().setStatus(404);
                ctx.json(new Message(404, "No doctors found within the specified birthdate range", timeStamp), Message.class);
            } else {
                ctx.res().setStatus(200);
                ctx.json(doctors, DoctorDTO.class); // Use DoctorDTO to control JSON output
            }
        } catch (DateTimeParseException e) {
            log.error("Invalid date format: {}", e.getMessage());
            throw new ApiException(400, "Invalid date format. Expected format is YYYY-MM-DD.");
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }

    @Override
    public void createDoctor(Context ctx) {

        try {
            // == request ==
            DoctorDTO doctorDTO = ctx.bodyAsClass(DoctorDTO.class);

            // == querying ==
            Doctor doctor = new Doctor(doctorDTO);

            doctorDAO.createDoctor(doctor);

            // == response ==
            ctx.status(201).json(new DoctorDTO(doctor));  // Return the created CountryDTO in the response
        }

        catch (ApiException e) {
            ctx.status(500).json(new Message(500, e.getMessage(), e.getTimeStamp()));
        }

        catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }

    @Override
    public void update(Context ctx) {

        try {
            // == request ==
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            DoctorDTO doctorDTO = ctx.bodyAsClass(DoctorDTO.class); // Request body as DTO

            // == querying for existing hotel ==
            Doctor existingDoctor = doctorDAO.getById(id);

            if (existingDoctor == null) {
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                ctx.res().setStatus(404);
                ctx.json(new Message(404, "Doctor not found", timeStamp), Message.class);
                return;
            }

            // == updating fields of the existing hotel ==
            existingDoctor.setDateOfBirth(doctorDTO.getDateOfBirth());
            existingDoctor.setYearOfGraduation(doctorDTO.getYearOfGraduation());
            existingDoctor.setNameOfClinic(doctorDTO.getNameOfClinic());
            existingDoctor.setSpeciality(doctorDTO.getSpeciality());
            // Update any other fields necessary

            // == updating in DB ==
            doctorDAO.update(id, existingDoctor);

            // == response ==
            ctx.res().setStatus(200);
            ctx.result("Doctor updated");

        } catch (NumberFormatException e) {
            log.error("Invalid hotel ID format: {}", e.getMessage());
            throw new ApiException(400, "Invalid hotel ID format");
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }

    @Override
    public void delete(Context ctx) {

        try {
            // == request ==
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            // == querying ==
            Doctor doctor = doctorDAO.getById(id);

            if (doctor == null) {
                String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                ctx.res().setStatus(404);
                ctx.json(new Message(404, "Doctor not found", timeStamp), Message.class);
                return;
            }

            // == deleting ==
            doctorDAO.delete(id);

            // == response ==
            ctx.res().setStatus(204);
        } catch (NumberFormatException e) {
            log.error("Invalid doctor ID format: {}", e.getMessage());
            throw new ApiException(400, "Invalid doctor ID format");
        } catch (Exception e) {
            log.error("500 - {}", e.getMessage(), e);
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            ctx.status(500).json(new Message(500, e.getMessage(), timeStamp));
        }
    }
}
