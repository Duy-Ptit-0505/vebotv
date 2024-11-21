package six.sportswears.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import six.sportswears.converter.CartToCartResponse;
import six.sportswears.converter.ListCartItemToCheckoutResponse;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.model.User;
import six.sportswears.payload.response.CartResponse;
import six.sportswears.payload.response.CheckoutResponse;
import six.sportswears.payload.response.MessageResponse;
import six.sportswears.repository.CartItemRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.security.services.UserDetailsImpl;
import six.sportswears.service.CartService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class CartServiceImpl implements CartService {
    UserRepository userRepository;
//    OrderItemRequestToOrderItem orderItemRequestToOrderItem;
//    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
//    CustomerRepository customerRepository;
//    OrderItemToOrderItemResponse orderItemToOrderItemResponse;
    CartToCartResponse cartToCartResponse;
    ListCartItemToCheckoutResponse listCartItemToCheckoutResponse;
    @Override
    public ResponseEntity<CartResponse> getCart(Long noPage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//        Customer customer = user.getCustomer();
        Cart cart = user.getCartList().get(0);



        List<CartItem> cartItemList = cartItemRepository.findAllByCart(cart);

        CartResponse cartResponse = cartToCartResponse.toCartResponse(cartItemList, noPage);

        return ResponseEntity.ok(cartResponse);
    }

    @Override
    public ResponseEntity<MessageResponse> clearCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        Cart cart = user.getCartList().get(0);
        cartItemRepository.deleteAllByCart(cart);
        return ResponseEntity.ok(new MessageResponse("oke"));
    }
    @Override
    public ResponseEntity<CheckoutResponse> getCheckout(Long noPage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        Cart cart = user.getCartList().get(0);

        List<CartItem> cartItemList = cartItemRepository.findAllByCart(cart);
        CheckoutResponse checkoutResponse = listCartItemToCheckoutResponse.toCheckoutResponse(cartItemList, noPage, cart.getId());
        return ResponseEntity.ok(checkoutResponse);
    }
}
