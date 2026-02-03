package gr.aueb.gr.appointment.dto;

import gr.aueb.gr.appointment.core.enums.AppointmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentUpdateRequest {

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotNull
    private LocalDateTime dateTime;

    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 60)
    private String firstName;

    @NotBlank
    @Size(max = 60)
    private String lastName;

    @NotBlank
    @Size(max = 30)
    private String tel;

    @NotNull
    private AppointmentStatus status;

    @NotNull
    private Long categoryId;

}
