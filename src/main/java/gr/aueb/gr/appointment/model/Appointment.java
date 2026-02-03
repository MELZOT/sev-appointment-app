package gr.aueb.gr.appointment.model;

import gr.aueb.gr.appointment.core.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import gr.aueb.gr.appointment.model.Category;
import jakarta.persistence.FetchType;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(name = "appointment_datetime", nullable = false)
    private LocalDateTime dateTime;

    @Column(length = 1000)
    private String description;
    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;


    @Column(nullable = false, length = 30)
    private String tel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
