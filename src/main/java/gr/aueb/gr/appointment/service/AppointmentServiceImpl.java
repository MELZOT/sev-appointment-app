package gr.aueb.gr.appointment.service;

import gr.aueb.gr.appointment.core.enums.AppointmentStatus;
import gr.aueb.gr.appointment.core.exceptions.ResourceNotFoundException;
import gr.aueb.gr.appointment.dto.AppointmentCreateRequest;
import gr.aueb.gr.appointment.dto.AppointmentResponse;
import gr.aueb.gr.appointment.dto.AppointmentUpdateRequest;
import gr.aueb.gr.appointment.dto.CategoryResponse;
import gr.aueb.gr.appointment.model.Appointment;
import gr.aueb.gr.appointment.model.Category;
import gr.aueb.gr.appointment.repository.AppointmentRepository;
import gr.aueb.gr.appointment.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public AppointmentResponse create(AppointmentCreateRequest req) {

        // UI έχει dropdown
        if (req.getCategoryId() == null) {
            throw new IllegalArgumentException("categoryId is required");
        }

        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found: " + req.getCategoryId()
                ));

        Appointment appointment = Appointment.builder()
                .title(req.getTitle())
                .dateTime(req.getDateTime())
                .description(req.getDescription())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .tel(req.getTel())
                .status(AppointmentStatus.SCHEDULED)
                .category(category) // ✅ εδώ είναι η διαφορά
                .build();

        return toResponse(repository.save(appointment));
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentUpdateRequest req) {
        Appointment appointment = repository.findByIdWithCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));

        appointment.setTitle(req.getTitle());
        appointment.setDateTime(req.getDateTime());
        appointment.setDescription(req.getDescription());
        appointment.setFirstName(req.getFirstName());
        appointment.setLastName(req.getLastName());
        appointment.setTel(req.getTel());
        appointment.setStatus(req.getStatus());

        // Allow αλλαγή category στο edit
        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found: " + req.getCategoryId()
                    ));
            appointment.setCategory(category);
        }

        return toResponse(repository.save(appointment));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public AppointmentResponse getById(Long id) {
        Appointment appointment = repository.findByIdWithCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + id));

        return toResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAll() {
        return repository.findAllWithCategory()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //  Null-safe mapping
    private AppointmentResponse toResponse(Appointment a) {

        CategoryResponse category = null;
        if (a.getCategory() != null) {
            category = CategoryResponse.builder()
                    .id(a.getCategory().getId())
                    .name(a.getCategory().getName())
                    .build();
        }

        return AppointmentResponse.builder()
                .id(a.getId())
                .title(a.getTitle())
                .dateTime(a.getDateTime())
                .description(a.getDescription())
                .firstName(a.getFirstName())
                .lastName(a.getLastName())
                .tel(a.getTel())
                .status(a.getStatus())
                .category(category)
                .build();
    }
}