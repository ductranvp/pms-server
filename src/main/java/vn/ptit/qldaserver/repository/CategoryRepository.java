package vn.ptit.qldaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ptit.qldaserver.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
