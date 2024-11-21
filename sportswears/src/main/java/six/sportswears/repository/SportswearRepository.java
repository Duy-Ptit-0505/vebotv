package six.sportswears.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Sportswear;
import six.sportswears.model.SportswearCategory;
import six.sportswears.repository.custom.SportswearRepositoryCustom;


import java.util.List;

@Repository
public interface SportswearRepository extends JpaRepository<Sportswear, Long>, SportswearRepositoryCustom {
//    List<Sportswear> findByStatus(Integer status);
//    List<Sportswear> findByNameContainingAndSportswearCategoryAndStatus(String key, SportswearCategory sportswearCategory, Integer status);
//    List<Sportswear> findByNameContainingAndStatus(String key, Integer status);
//    List<Sportswear> findByNameContainingAndCategory(String key, SportswearCategory sportswearCategory);
//    Page<Sportswear> findAllByIdNot(Long id, Pageable pageable);






}
