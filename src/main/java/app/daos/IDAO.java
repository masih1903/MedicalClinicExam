package app.daos;


import app.enums.Speciality;

import java.time.LocalDate;
import java.util.List;

public interface IDAO<T> {

    List<T> getAll();

    T getById(Integer id);

    List<T> doctorBySpeciality(Speciality speciality);

    List<T> doctorByBirthdateRange(LocalDate from, LocalDate to);

    T createDoctor(T t);

    T update(Integer id, T t);

    void delete(Integer id);
}
