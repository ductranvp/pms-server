package vn.ptit.pms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.domain.enumeration.ProjectRole;
import vn.ptit.pms.security.UserPrincipal;
import vn.ptit.pms.security.annotation.CurrentUser;
import vn.ptit.pms.service.CategoryService;
import vn.ptit.pms.service.UserProjectService;
import vn.ptit.pms.service.dto.core.ErrorEntity;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryResource {
    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);
    private final String ENTITY_NAME = "Category";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserProjectService userProjectService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Category category, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to create {}", ENTITY_NAME);

        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserProjectInRole(userId, category.getProjectId(), ProjectRole.ROLE_MANAGER))
            return new ResponseEntity<>(ErrorEntity.forbidden("Access denied"), HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category, @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        log.info("REST request to update {}", ENTITY_NAME);

        Long userId = userPrincipal.getId();
        if (!userProjectService.isUserProjectInRole(userId, category.getProjectId(), ProjectRole.ROLE_MANAGER))
            return new ResponseEntity<>(ErrorEntity.forbidden("Access denied"), HttpStatus.FORBIDDEN);

        return ResponseEntity.ok(categoryService.update(category));
    }
//
//    @GetMapping("/list")
//    public ResponseEntity<List<Category>> getAll() {
//        log.info("REST request to get list {}", ENTITY_NAME);
//        return ResponseEntity.ok(categoryService.getAll());
//    }
//
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Category> get(@PathVariable Long id) {
//        log.info("REST request to get {}", ENTITY_NAME);
//        return ResponseEntity.ok(categoryService.getOneById(id));
//    }

    @PutMapping("/archive/{id}")
    public ResponseEntity<Void> archive(@PathVariable Long id) {
        log.info("REST request to archive {}", ENTITY_NAME);
        categoryService.archive(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/un-archive/{id}")
    public ResponseEntity<Void> unArchive(@PathVariable Long id) {
        log.info("REST request to un-archive {}", ENTITY_NAME);
        categoryService.unArchived(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST request to delete {}", ENTITY_NAME);
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
