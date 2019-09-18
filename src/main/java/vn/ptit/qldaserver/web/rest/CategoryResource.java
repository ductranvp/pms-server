package vn.ptit.qldaserver.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.Category;
import vn.ptit.qldaserver.service.CategoryService;
import vn.ptit.qldaserver.service.dto.core.ErrorEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);
    private final String ENTITY_NAME = "Category";

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        log.info("REST request to save {}", ENTITY_NAME);
        if (category.getId() != null){
            return new ResponseEntity<>(ErrorEntity.badRequest("A new category cannot already have an ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("/category")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        log.info("REST request to save {}", ENTITY_NAME);
        if (category.getId() == null){
            return new ResponseEntity<>(ErrorEntity.badRequest("Category must have and ID"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(categoryService.save(category));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("REST request to get all {}", ENTITY_NAME);
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        log.info("REST request to get {}", ENTITY_NAME);
        return ResponseEntity.ok(categoryService.findOne(id));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("REST request to delete {}", ENTITY_NAME);
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
