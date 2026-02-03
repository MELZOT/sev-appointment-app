package gr.aueb.gr.appointment.service;

import gr.aueb.gr.appointment.core.exceptions.ResourceNotFoundException;
import gr.aueb.gr.appointment.dto.CategoryCreateRequest;
import gr.aueb.gr.appointment.dto.CategoryResponse;
import gr.aueb.gr.appointment.dto.CategoryUpdateRequest;
import gr.aueb.gr.appointment.model.Category;
import gr.aueb.gr.appointment.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<CategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse create(CategoryCreateRequest req) {
        Category category = Category.builder()
                .name(req.getName())
                .build();

        return toResponse(repository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryUpdateRequest req) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));

        category.setName(req.getName());

        return toResponse(repository.save(category));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
        repository.deleteById(id);
    }

    private CategoryResponse toResponse(Category c) {
        return CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }
}
