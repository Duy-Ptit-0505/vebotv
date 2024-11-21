package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Sportswear;
import six.sportswears.model.UserReview;

import java.util.List;
@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
    List<UserReview> findAllBySportswear(Sportswear sportswear);
}
