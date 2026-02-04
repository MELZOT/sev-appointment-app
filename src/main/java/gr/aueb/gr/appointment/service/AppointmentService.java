package gr.aueb.gr.appointment.service;

import gr.aueb.gr.appointment.dto.*;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse create(AppointmentCreateRequest request);

    AppointmentResponse update(Long id, AppointmentUpdateRequest request);

    void delete(Long id);

    AppointmentResponse getById(Long id);

    List<AppointmentResponse> getAll();
}//
