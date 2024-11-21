package six.sportswears.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.payload.response.CartResponse;
import six.sportswears.payload.response.CheckoutResponse;
import six.sportswears.payload.response.MessageResponse;

@Service
@Transactional
public interface CartService {
    ResponseEntity<CartResponse> getCart(Long noPage);
    ResponseEntity<MessageResponse> clearCart();
    ResponseEntity<CheckoutResponse> getCheckout(Long noPage);
}
