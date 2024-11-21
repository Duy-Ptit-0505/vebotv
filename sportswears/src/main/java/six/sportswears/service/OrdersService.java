package six.sportswears.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.payload.request.OrdersRequest;
import six.sportswears.payload.response.ListOrderResponse;
import six.sportswears.payload.response.MessageResponse;


@Service
@Transactional
public interface OrdersService {

    ResponseEntity<MessageResponse> createOrders(OrdersRequest ordersRequest);
    ResponseEntity<ListOrderResponse> getAllOrderResponseForUserByStatus(Integer status, Long noPage);
    ResponseEntity<ListOrderResponse> getAllOrderResponseForAdminByStatus(Integer status, Long noPage);
    ResponseEntity<MessageResponse> changeOrderStatus(Integer status, Long id);
}
