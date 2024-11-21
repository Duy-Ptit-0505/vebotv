package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
