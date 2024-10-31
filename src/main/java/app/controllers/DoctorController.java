package app.controllers;

import app.daos.DoctorDAO;
import app.dtos.DoctorDTO;
import app.entities.Doctor;
import app.enums.Speciality;
import app.exceptions.ApiException;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DoctorController implements IController {

    private final DoctorDAO doctorDAO;

    public DoctorController(DoctorDAO doctorDao) {
        this.doctorDAO = doctorDao;

    }

    @Override
    public void getAll(Context ctx) {
        List<Doctor> doctors = doctorDAO.getAll();
        if (doctors.isEmpty()) {
            throw new ApiException(404, "No doctors found.");
        }
        List<DoctorDTO> doctorDTOs = DoctorDTO.toDoctorDTOList(doctors);
        ctx.status(200).json(doctorDTOs, DoctorDTO.class);
    }

    @Override
    public void getById(Context ctx) {

        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Doctor doctor = doctorDAO.getById(id);
            if (doctor == null) {
                throw new ApiException(404, "Doctor with ID " + id + " not found");
            }
            DoctorDTO countryDTO = new DoctorDTO(doctor);
            ctx.res().setStatus(200);
            ctx.json(countryDTO, DoctorDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }

    @Override
    public void doctorBySpecialty(Context ctx) {
        try {
            String specialityParam = ctx.pathParam("speciality");
            Speciality speciality = Speciality.valueOf(specialityParam.toUpperCase());
            List<Doctor> doctors = doctorDAO.doctorBySpeciality(speciality);
            if (doctors.isEmpty()) {
                throw new ApiException(404, "No doctors found with speciality " + speciality);
            }
            ctx.status(200).json(doctors);
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid speciality provided. Please check the speciality and try again.");
        }
    }

    @Override
    public void doctorByBirthdayRange(Context ctx) {
        try {
            LocalDate fromDate = LocalDate.parse(ctx.pathParam("from"));
            LocalDate toDate = LocalDate.parse(ctx.pathParam("to"));
            List<Doctor> doctors = doctorDAO.doctorByBirthdateRange(fromDate, toDate);
            if (doctors.isEmpty()) {
                throw new ApiException(404, "No doctors found with birthdate between " + fromDate + " and " + toDate);
            }
            ctx.status(200).json(doctors, DoctorDTO.class);
        } catch (DateTimeParseException e) {
            throw new ApiException(400, "Invalid date format. Please use the format YYYY-MM-DD for both dates.");
        }
    }

    @Override
    public void createDoctor(Context ctx) {
        try {
            DoctorDTO doctorDTO = ctx.bodyAsClass(DoctorDTO.class);
            Doctor doctor = doctorDAO.createDoctor(doctorDTO);
            ctx.status(201).json(new DoctorDTO(doctor));
        } catch (Exception e) {
            throw new ApiException(500, "An error occurred while creating the doctor. Please try again later.");
        }
    }

    @Override
    public void update(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            DoctorDTO doctorDTO = ctx.bodyAsClass(DoctorDTO.class);
            Doctor updatedDoctor = doctorDAO.update(id, doctorDTO);
            if (updatedDoctor == null) {
                throw new ApiException(404, "Doctor with ID " + id + " not found");
            }
            ctx.status(200).json(updatedDoctor, DoctorDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Doctor doctor = doctorDAO.getById(id);
            if (doctor == null) {
                throw new ApiException(404, "Doctor with ID " + id + " not found");
            }
            doctorDAO.delete(id);
            ctx.status(204);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid ID format. Please provide a numeric ID.");
        }
    }
}
