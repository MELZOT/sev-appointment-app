package gr.aueb.gr.appointment.api;

import gr.aueb.gr.appointment.dto.AppointmentCreateRequest;
import gr.aueb.gr.appointment.dto.AppointmentResponse;
import gr.aueb.gr.appointment.dto.AppointmentUpdateRequest;
import gr.aueb.gr.appointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @Operation(summary = "Get all appointments (public)")
    @GetMapping
    public List<AppointmentResponse> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get appointment by id (public)")
    @GetMapping("/{id}")
    public AppointmentResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(
            summary = "Create appointment (owner)",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse create(@Valid @RequestBody AppointmentCreateRequest req) {
        return service.create(req);
    }

    @Operation(
            summary = "Update appointment (owner)",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PutMapping("/{id}")
    public AppointmentResponse update(@PathVariable Long id,
                                      @Valid @RequestBody AppointmentUpdateRequest req) {
        return service.update(id, req);
    }

    @Operation(
            summary = "Delete appointment (owner)",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
