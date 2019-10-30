package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final int INCREMENT = (int) Math.pow(2, 16);
    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        Long projectId = category.getProjectId();
        Integer lastPos = categoryRepository.getLastCategoryPos(projectId);
        int pos = lastPos != null ? lastPos + INCREMENT : INCREMENT - 1;
        category.setPos(pos);
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        if (category.isArchived()) {
            category.setPos(-1);
        }
        return categoryRepository.save(category);
    }

    public Category getOneById(Long id) {
        try {
            return categoryRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void unArchived(Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            int pos = categoryRepository.getLastCategoryPos(category.getProjectId());
            category.setPos(pos + INCREMENT);
            category.setArchived(false);
            categoryRepository.save(category);
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

    public void archive(Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            category.setPos(-1);
            category.setArchived(true);
            categoryRepository.save(category);
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

    public void delete(Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            category.setPos(-2);
            category.setDeleted(true);
            categoryRepository.save(category);
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

    public List<Category> getByProjectId(Long projectId, boolean archived) {
        return categoryRepository.findByProjectIdAndArchivedOrderByPosAsc(projectId, archived);
    }

}
