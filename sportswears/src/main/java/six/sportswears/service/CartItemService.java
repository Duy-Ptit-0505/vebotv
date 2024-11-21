package six.sportswears.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.payload.request.CartItemRequest;
import six.sportswears.payload.response.MessageResponse;

@Service
@Transactional
public interface CartItemService {
    ResponseEntity<MessageResponse> addToCart(CartItemRequest cartItemRequest);
    ResponseEntity<MessageResponse> increaseQuantityByID(Long id);
    ResponseEntity<MessageResponse> decreaseQuantityByID(Long id);
    ResponseEntity<MessageResponse> deleteOrderItemByID(Long id);

}
