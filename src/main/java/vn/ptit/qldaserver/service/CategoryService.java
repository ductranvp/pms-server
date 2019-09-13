package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Category;
import vn.ptit.qldaserver.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id).get();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
