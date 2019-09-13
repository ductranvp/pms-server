package vn.ptit.qldaserver.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.qldaserver.domain.Category;
import vn.ptit.qldaserver.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        if (category.getId() != null){

        }
        return null;
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findOne(id));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
