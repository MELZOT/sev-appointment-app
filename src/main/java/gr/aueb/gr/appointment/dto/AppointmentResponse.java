package gr.aueb.gr.appointment.dto;

import gr.aueb.gr.appointment.core.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;
    private String title;
    private LocalDateTime dateTime;
    private String description;
    private String firstName;
    private String lastName;

    private String tel;
    private AppointmentStatus status;
    private CategoryResponse category;

}
