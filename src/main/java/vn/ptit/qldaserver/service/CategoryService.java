package vn.ptit.qldaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.qldaserver.domain.Category;
import vn.ptit.qldaserver.exception.AppException;
import vn.ptit.qldaserver.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findOne(Long id) {
        try {
            return categoryRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void delete(Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            category.setDeleted(true);
            categoryRepository.save(category);
//        categoryRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

}
