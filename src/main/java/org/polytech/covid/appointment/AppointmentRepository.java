package org.polytech.covid.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT c FROM Appointment c where lower(c.firstname) LIKE lower(concat('%', :name, '%')) or lower(c.lastname) LIKE lower(concat('%', :name, '%'))")
    public List<Appointment> findAllByName(@Param("name") String name);
}
