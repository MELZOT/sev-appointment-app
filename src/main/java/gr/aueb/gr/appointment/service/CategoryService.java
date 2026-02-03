package gr.aueb.gr.appointment.service;

import gr.aueb.gr.appointment.dto.CategoryCreateRequest;
import gr.aueb.gr.appointment.dto.CategoryUpdateRequest;
import gr.aueb.gr.appointment.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAll();

    CategoryResponse create(CategoryCreateRequest req);

    CategoryResponse update(Long id, CategoryUpdateRequest req);

    void delete(Long id);
}
