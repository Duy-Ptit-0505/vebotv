package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.Orders;
import six.sportswears.model.User;


import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
//    List<Orders> findAllByCustomer(Customer customer);
    List<Orders> findAllByOrderStatus(Integer status);
    List<Orders> findAllByUserAndOrderStatusIn(User user, List<Integer> status);

}
