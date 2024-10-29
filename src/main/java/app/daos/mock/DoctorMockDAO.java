package app.daos.mock;

import app.daos.IDAO;
import app.dtos.DoctorDTO;
import app.enums.Speciality;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DoctorMockDAO implements IDAO<DoctorDTO> {

    private static List<DoctorDTO> doctorDTOList = new ArrayList<>();

    @Override
    public List<DoctorDTO> getAll() {
        return new ArrayList<>(doctorDTOList);
    }

    @Override
    public DoctorDTO getById(Integer id) {
        Optional<DoctorDTO> doctorDTO =
                doctorDTOList.stream()
                        .filter(p -> p.getId() == id)
                        .findFirst();
        return doctorDTO.orElse(null);
    }

    @Override
    public List<DoctorDTO> doctorBySpeciality(Speciality speciality) {
        return doctorDTOList.stream()
                .filter(doctorDTO -> doctorDTO.getSpeciality()
                        .equals(speciality))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> doctorByBirthdateRange(LocalDate from, LocalDate to) {
        return doctorDTOList.stream()
                .filter(doctorDTO -> !doctorDTO.getDateOfBirth()
                        .isBefore(from) && !doctorDTO.getDateOfBirth()
                        .isAfter(to)).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        doctorDTOList.add(doctorDTO);
        return doctorDTO;
    }


    @Override
    public DoctorDTO update(Integer id, DoctorDTO doctorDTO) {
        for (int i = 0; i < doctorDTOList.size(); i++) {
            if (doctorDTOList.get(i).getId().equals(id)) { // Use .equals for Integer comparison
                doctorDTOList.set(i, doctorDTO);
                System.out.println(doctorDTO);
                return doctorDTO; // return the updated doctor
            }
        }
        System.out.println("Doctor not found with id: " + id);
        return null; // or throw an exception if preferred
    }

    @Override
    public void delete(Integer id) {
        doctorDTOList.removeIf(doctorDTO -> doctorDTO.getId().equals(id));
    }
}
