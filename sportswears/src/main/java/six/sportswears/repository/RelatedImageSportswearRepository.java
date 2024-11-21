package six.sportswears.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.RelatedImageSportswear;
import six.sportswears.model.Sportswear;


import java.util.List;

@Repository
public interface RelatedImageSportswearRepository extends JpaRepository<RelatedImageSportswear, Long> {
    List<RelatedImageSportswear> findAllBySportswear(Sportswear sportswear);
}

