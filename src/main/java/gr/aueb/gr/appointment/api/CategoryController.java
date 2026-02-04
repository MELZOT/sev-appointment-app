package gr.aueb.gr.appointment.api;

import gr.aueb.gr.appointment.dto.CategoryCreateRequest;
import gr.aueb.gr.appointment.dto.CategoryResponse;
import gr.aueb.gr.appointment.dto.CategoryUpdateRequest;
import gr.aueb.gr.appointment.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")//
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    //  Public
    @Operation(summary = "Get all categories (public)")
    @GetMapping
    public List<CategoryResponse> getAll() {
        return service.getAll();
    }

    //  Owner
    @Operation(
            summary = "Create category (owner)",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryCreateRequest req) {
        return service.create(req);
    }

    // Owner
    @Operation(
            summary = "Update category (owner)",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @PutMapping("/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest req
    ) {
        return service.update(id, req);
    }

    // Owner
    @Operation(
            summary = "Delete category (owner)",
            security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
