package org.polytech.covid.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {
    @Autowired
    public AppointmentService appointmentService;

    @GetMapping("/user/appointment")
    public List<Appointment> getAllAppointment(){
        return this.appointmentService.getAllAppointment();
    }

    @GetMapping("/user/appointment/name/{name}")
    public List<Appointment> getAppointmentByName(@PathVariable("name") String name) {
        return appointmentService.getAppointmentByName(name);
    }

    @GetMapping("/user/appointment/date/{date}")
    public List<Appointment> getAppointmentByDate(@PathVariable("date") String date) {
        return appointmentService.getAppointmentByDate(date);
    }

    @GetMapping("/user/appointment/{id}")
    public Appointment getAppointmentById(@PathVariable("id") Integer id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping("/public/appointment")
    public Appointment saveAppointment(@RequestBody Appointment newAppointment) {
        return this.appointmentService.saveAppointment(newAppointment);
    }

    @PatchMapping("/user/appointment/{id}")
    public Appointment updateAppointment(@PathVariable("id") Integer id, @RequestBody Appointment updatedAppointment) {
        return this.appointmentService.updateAppointment(id, updatedAppointment);
    }

    @DeleteMapping("/user/appointment/{id}")
    public void deleteAppointment(@PathVariable("id") Integer id) {
        appointmentService.deleteAppointment(id);
    }
}
