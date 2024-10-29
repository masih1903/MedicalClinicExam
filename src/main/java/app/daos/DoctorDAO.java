package app.daos;

import app.entities.Doctor;
import app.enums.Speciality;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class DoctorDAO implements IDAOMock<Doctor> {

    private final EntityManagerFactory emf;

    public DoctorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Doctor> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d", Doctor.class);
            return query.getResultList();
        }
    }

    @Override
    public Doctor getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Doctor.class, id);
        }
    }

    @Override
    public List<Doctor> doctorBySpeciality(Speciality speciality) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d WHERE d.speciality = :speciality", Doctor.class);
            query.setParameter("speciality", speciality);
            return query.getResultList();
        }
    }

    @Override
    public List<Doctor> doctorByBirthdateRange(LocalDate from, LocalDate to) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d WHERE d.dateOfBirth BETWEEN :from AND :to", Doctor.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            return query.getResultList();
        }
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(doctor);
            em.getTransaction().commit();
            return doctor;
        }
    }

    @Override
    public Doctor update(Integer id, Doctor doctor) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Doctor doctorToUpdate = em.find(Doctor.class, id);
            doctorToUpdate.setDateOfBirth(doctor.getDateOfBirth());
            doctorToUpdate.setYearOfGraduation(doctor.getYearOfGraduation());
            doctorToUpdate.setNameOfClinic(doctor.getNameOfClinic());
            doctorToUpdate.setSpeciality(doctor.getSpeciality());
            em.getTransaction().commit();
            return doctorToUpdate;
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Doctor doctor = em.find(Doctor.class, id);
            em.remove(doctor);
            em.getTransaction().commit();
        }
    }
}
