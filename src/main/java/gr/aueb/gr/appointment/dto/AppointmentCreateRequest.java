package gr.aueb.gr.appointment.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentCreateRequest {

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

    @NotNull(message = "categoryId is required")
    private Long categoryId;


}
