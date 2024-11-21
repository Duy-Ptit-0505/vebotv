package six.sportswears.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import six.sportswears.model.OrderItem;


import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
//    List<OrderItem> findAllByStatusAndCart(Integer status, Cart cart);
//    void deleteAllByCart(Cart cart);
}
