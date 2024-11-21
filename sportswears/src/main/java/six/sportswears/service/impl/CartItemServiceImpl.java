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
import six.sportswears.converter.CartItemRequestToCartItem;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.model.User;
import six.sportswears.payload.request.CartItemRequest;
import six.sportswears.payload.response.MessageResponse;
import six.sportswears.repository.CartItemRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.security.services.UserDetailsImpl;
import six.sportswears.service.CartItemService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class CartItemServiceImpl implements CartItemService {
    UserRepository userRepository;
    CartItemRequestToCartItem cartItemRequestToCartItem;
//    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    @Override
    public ResponseEntity<MessageResponse> addToCart(CartItemRequest cartItemRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        Cart cart = user.getCartList().get(0);
//        Customer customer = user.getCustomer();
//        Cart cart = customer.getCart();
//        if(customer.getCart() == null) {
//            Cart newCart = new Cart();
//            newCart.setCustomer(customer);
//            cartRepository.save(newCart);
//            cart = newCart;
//        }
        CartItem cartItem = cartItemRequestToCartItem.toCartItem(cartItemRequest);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok(new MessageResponse("oke"));
    }
    @Override
    public ResponseEntity<MessageResponse> increaseQuantityByID(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).get();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok(new MessageResponse("oke"));
    }

    @Override
    public ResponseEntity<MessageResponse> decreaseQuantityByID(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).get();
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok(new MessageResponse("oke"));
    }

    @Override
    public ResponseEntity<MessageResponse> deleteOrderItemByID(Long id) {

        cartItemRepository.deleteById(id);
        return  ResponseEntity.ok(new MessageResponse("oke"));
    }
}
