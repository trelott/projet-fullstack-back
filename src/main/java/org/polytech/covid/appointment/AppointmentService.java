package org.polytech.covid.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<Appointment> getAppointmentByDate(String dateString) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = null;
        try {
            date = isoFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return this.appointmentRepository.findAllByDate(date);
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
