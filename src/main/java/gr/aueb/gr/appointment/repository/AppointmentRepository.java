package gr.aueb.gr.appointment.repository;

import gr.aueb.gr.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query("select a from Appointment a left join fetch a.category")
    List<Appointment> findAllWithCategory();

    //  Φορτώνει appointment και category με id
    @Query("select a from Appointment a left join fetch a.category where a.id = :id")
    Optional<Appointment> findByIdWithCategory(@Param("id") Long id);
}//
