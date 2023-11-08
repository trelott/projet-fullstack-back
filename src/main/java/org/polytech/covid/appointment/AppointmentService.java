package org.polytech.covid.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointment(){
        return this.appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Integer id) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) return appointment.get();
        else throw new AppointmentNotFoundException();
    }

    public List<Appointment> getAppointmentByName(String name) {
        return this.appointmentRepository.findAllByName(name);
    }

    public Appointment saveAppointment(Appointment newAppointment) {
        return this.appointmentRepository.save(newAppointment);
    }

    public Appointment updateAppointment(Integer id, Appointment updatedAppointment) throws AppointmentNotFoundException{
        Optional<Appointment> appointmentToUpdate = appointmentRepository.findById(id);

        if(appointmentToUpdate.isPresent()) {
            updatedAppointment.setId(appointmentToUpdate.get().getId());
            return appointmentRepository.save(updatedAppointment);
        } else throw new AppointmentNotFoundException();
    }

    public void deleteAppointment(Integer id) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) appointmentRepository.deleteById(id);
        else throw new AppointmentNotFoundException();
    }
}
