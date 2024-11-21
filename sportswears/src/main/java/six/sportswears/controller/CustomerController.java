package six.sportswears.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import six.sportswears.payload.request.*;
import six.sportswears.payload.response.*;
import six.sportswears.service.CartItemService;
import six.sportswears.service.CartService;
import six.sportswears.service.OrdersService;
import six.sportswears.service.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CartItemService cartItemService;
    CartService cartService;
    OrdersService ordersService;
    UserService userService;
    @PostMapping("/add-to-cart")
    ResponseEntity<MessageResponse> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        return cartItemService.addToCart(cartItemRequest);
    }
    @GetMapping("/cart")
    ResponseEntity<CartResponse> getCartForCustomer(@ModelAttribute SearchRequest searchRequest) {
        if(searchRequest.getNoPage() == null) {
            searchRequest.setNoPage(1L);
        }
        return cartService.getCart(searchRequest.getNoPage());
    }
    @GetMapping("/increase-quantity/{id}")
    ResponseEntity<MessageResponse> increaseQuantityByID(@PathVariable Long id) {

        return cartItemService.increaseQuantityByID(id);
    }
    @GetMapping("/clear-cart")
    ResponseEntity<MessageResponse> clearCart() {

        return cartService.clearCart();
    }
    @GetMapping("/decrease-quantity/{id}")
    ResponseEntity<MessageResponse> decreaseQuantityByID(@PathVariable Long id) {

        return cartItemService.decreaseQuantityByID(id);
    }

    @GetMapping("/delete-cart-item/{id}")
    ResponseEntity<MessageResponse> deleteOrderItemByID(@PathVariable Long id) {
        return cartItemService.deleteOrderItemByID(id);
    }

    @GetMapping("/checkout")
    ResponseEntity<CheckoutResponse> getCheckout(@ModelAttribute SearchRequest searchRequest) {
        if(searchRequest.getNoPage() == null) {
            searchRequest.setNoPage(1L);
        }
        return cartService.getCheckout(searchRequest.getNoPage());
    }

    @PostMapping("/create-orders")
    ResponseEntity<MessageResponse> createOrders(@RequestBody OrdersRequest ordersRequest) {
        return ordersService.createOrders(ordersRequest);
    }

    @PostMapping("/orders")
    ResponseEntity<ListOrderResponse> getListOrderResponse(@RequestBody StatusAndNoPage statusAndNoPage) {
        if(statusAndNoPage.getNoPage() == null) {
            statusAndNoPage.setNoPage(1L);
        }
        return ordersService.getAllOrderResponseForUserByStatus(statusAndNoPage.getStatus(), statusAndNoPage.getNoPage());
    }

    @GetMapping("/profile")
    ResponseEntity<UserResponse> getProfile() {
        return userService.getProfileUser();
    }

    @PostMapping("/feedback")
    ResponseEntity<MessageResponse> feedBack(@RequestBody OrdersResponse ordersResponse) {
        List<ReviewRequest> reviewRequestList = new ArrayList<>();
        for(OrderItemResponse x : ordersResponse.getOrderItemResponseList()) {
            reviewRequestList.add(new ReviewRequest(x.getSportswearID(), x.getRatingValue(), x.getComment()));
        }
        ordersService.changeOrderStatus(5, ordersResponse.getId());
        return userService.feedBack(reviewRequestList);
    }
    @PostMapping("/change-profile")
    ResponseEntity<MessageResponse> changeProfile(@RequestBody UserRequest userRequest) {
        return userService.changeProfile(userRequest);
    }

}
