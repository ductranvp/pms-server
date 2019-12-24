package vn.ptit.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.pms.domain.Category;
import vn.ptit.pms.exception.AppException;
import vn.ptit.pms.repository.CategoryRepository;
import vn.ptit.pms.socket.WebSocketService;
import vn.ptit.pms.util.WSConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final int INCREMENT = (int) Math.pow(2, 16);
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WebSocketService webSocketService;

    public Category create(Category category) {
        Long projectId = category.getProjectId();
        Integer lastPos = categoryRepository.getLastCategoryPos(projectId);
        int pos = lastPos != null ? lastPos + INCREMENT : INCREMENT - 1;
        category.setPos(pos);
        webSocketService.sendMessage(WSConstants.TASK);
        return categoryRepository.save(category);
    }

    public Category updatePos(Category category) {
        Category entityToUpdate = categoryRepository.getOne(category.getId());
        entityToUpdate.setPos(category.getPos());
        webSocketService.sendMessage(WSConstants.TASK);
        return categoryRepository.save(entityToUpdate);
    }

    public List<Category> updateList(List<Category> list) {
        List<Category> result = new ArrayList<>();
        int pos = INCREMENT - 1;
        for (int i = 0; i < list.size(); i++) {
            Category cat = list.get(i);
            cat.setPos(pos);
            categoryRepository.save(cat);
            pos += INCREMENT;
            result.add(cat);
        }
        webSocketService.sendMessage(WSConstants.TASK);
        return result;
    }

    public Category updateName(Category category) {
        Category entityToUpdate = categoryRepository.getOne(category.getId());
        entityToUpdate.setName(category.getName());
        webSocketService.sendMessage(WSConstants.TASK);
        return categoryRepository.save(entityToUpdate);
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
            webSocketService.sendMessage(WSConstants.TASK);
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
            webSocketService.sendMessage(WSConstants.TASK);
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
            webSocketService.sendMessage(WSConstants.TASK);
            categoryRepository.save(category);
        } catch (NoSuchElementException e) {
            throw new AppException("Category " + id + " could not be found");
        }
    }

    public List<Category> getByProjectId(Long projectId, boolean archived) {
        return categoryRepository.findByProjectIdAndArchivedOrderByPosAsc(projectId, archived);
    }

    public Long getProjectIdByCategoryId(Long categoryId) {
        return categoryRepository.findProjectIdByCategoryId(categoryId);
    }

}
