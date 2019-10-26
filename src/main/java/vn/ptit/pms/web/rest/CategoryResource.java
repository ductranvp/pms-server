package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.service.CategoryService;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);
    private final String ENTITY_NAME = "Category";

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Category category) {
        log.info("REST request to create {}", ENTITY_NAME);
        if (category.getId() != null) {
            return new ResponseEntity<>(ErrorEntity.badRequest("A new " + ENTITY_NAME + " cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        log.info("REST request to update {}", ENTITY_NAME);
        if (category.getId() == null) {
            return new ResponseEntity<>(ErrorEntity.badRequest(ENTITY_NAME + " must have and ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(categoryService.update(category));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAll() {
        log.info("REST request to get list {}", ENTITY_NAME);
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(categoryService.getOneById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete {}", ENTITY_NAME);
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
