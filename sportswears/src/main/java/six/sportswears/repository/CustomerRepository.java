package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
