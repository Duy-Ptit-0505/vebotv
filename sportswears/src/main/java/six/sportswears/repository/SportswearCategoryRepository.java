package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.SportswearCategory;

@Repository
public interface SportswearCategoryRepository  extends JpaRepository<SportswearCategory, Long> {
    SportswearCategory findByName(String name);
}
