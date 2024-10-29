package app;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.config.Populator;
import app.daos.DoctorDAO;
import app.daos.DoctorMockDAO;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {


//        //Populate in static arraylist
//        DoctorMockDAO doctorMockDAO = new DoctorMockDAO();
//        Populator.populateDoctorList(doctorMockDAO);
//
        //Populate in db "doctors"
//        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
//        DoctorDAO doctorDAO = new DoctorDAO(emf);
//        Populator populator = new Populator(emf, doctorDAO);
//        populator.populateDoctors();

        AppConfig.startServer();

    }
}