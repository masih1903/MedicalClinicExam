package app.routes;

import app.config.HibernateConfig;
import app.controllers.DoctorController;
import app.daos.DoctorDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class DoctorRoute {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final DoctorDAO doctorDao = new DoctorDAO(emf);
    DoctorController doctorController = new DoctorController(doctorDao);


    public EndpointGroup getDoctorRoutes() {
        return () ->
        {
            get("/", doctorController::getAll);
            get("/{id}", doctorController::getById);
            get("/speciality/{speciality}", doctorController::doctorBySpecialty);
            get("/birthdate-range/{from}/{to}", doctorController::doctorByBirthdayRange);
            post("/", doctorController::createDoctor);
            put("/{id}", doctorController::update);
            delete("/{id}", doctorController::delete);
        };
    }
}
