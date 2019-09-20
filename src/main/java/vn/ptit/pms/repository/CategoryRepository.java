package vn.ptit.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.pms.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
