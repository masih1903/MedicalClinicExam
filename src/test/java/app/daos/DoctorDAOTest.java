package app.daos;

import app.config.HibernateConfig;
import app.entities.Doctor;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static app.enums.Speciality.*;
import static org.junit.jupiter.api.Assertions.*;

class DoctorDAOTest {

    static EntityManagerFactory emf;
    static DoctorDAO doctorDAO;
    static Doctor d1, d2, d3;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryConfigTest();
        doctorDAO = new DoctorDAO(emf);
    }

    @BeforeEach
    void setUp() {

        d1 = new Doctor("Dr. John Doe", LocalDate.of(1975, 3, 15), 2000, "City Health Clinic", FAMILY_MEDICINE);
        d2 = new Doctor("Dr. Jane Smith", LocalDate.of(1980, 7, 22), 2005, "Downtown Medical Center", PEDIATRICS);
        d3 = new Doctor("Dr. Sam Brown", LocalDate.of(1969, 11, 5), 1995, "General Hospital", PSYCHIATRY);

        doctorDAO.createDoctor(d1);
        doctorDAO.createDoctor(d2);
        doctorDAO.createDoctor(d3);
    }

    @AfterEach
    void tearDown() {

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Doctor ").executeUpdate();
            em.createQuery("DELETE FROM Appointment ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE doctor_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE appointment_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAll() {

        List<Doctor> doctors = doctorDAO.getAll();
        assertEquals(3, doctors.size());
    }

    @Test
    void getById() {

        Doctor doctor = doctorDAO.getById(1);
        assertEquals(d1.getId(), doctor.getId());
    }

    @Test
    void doctorBySpeciality() {

        List<Doctor> doctors = doctorDAO.doctorBySpeciality(FAMILY_MEDICINE);
        assertEquals(1, doctors.size());
    }

    @Test
    void doctorByBirthdateRange() {

        List<Doctor> doctors = doctorDAO.doctorByBirthdateRange(LocalDate.of(1970, 1, 1), LocalDate.of(1979, 12, 31));
        assertEquals(1, doctors.size());
    }

    @Test
    void createDoctor() {

        Doctor doctor = new Doctor("Dr. Alex Johnson", LocalDate.of(1985, 5, 10), 2010, "City Health Clinic", FAMILY_MEDICINE);
        doctorDAO.createDoctor(doctor);
        assertEquals(4, doctor.getId());
    }

    @Test
    void update() {

        Integer id = d1.getId();
        d1.setNameOfClinic("City Hospital");
        doctorDAO.update(id, d1);
        assertEquals("City Hospital", doctorDAO.getById(id).getNameOfClinic());

    }

    @Test
    void delete() {

        doctorDAO.delete(1);
        assertNull(doctorDAO.getById(1));
    }
}