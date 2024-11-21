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
import six.sportswears.converter.CartItemToOrderItem;
import six.sportswears.converter.ListOrdersToListOrderResponse;
import six.sportswears.converter.OrdersRequestToOrders;
import six.sportswears.model.CartItem;
import six.sportswears.model.OrderItem;
import six.sportswears.model.Orders;
import six.sportswears.model.User;
import six.sportswears.payload.request.OrdersRequest;
import six.sportswears.payload.response.ListOrderResponse;
import six.sportswears.payload.response.MessageResponse;
import six.sportswears.repository.CartItemRepository;
import six.sportswears.repository.OrderItemRepository;
import six.sportswears.repository.OrdersRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.security.services.UserDetailsImpl;
import six.sportswears.service.OrdersService;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class OrdersServiceImpl implements OrdersService {
//    ShippingRepository shippingRepository;
//    PaymentRepository paymentRepository;
    OrdersRepository ordersRepository;
    OrderItemRepository orderItemRepository;
    CartItemRepository cartItemRepository;
//    OrdersToOrdersResponse ordersToOrdersResponse;
    UserRepository userRepository;
//    CartRepository cartRepository;
    ListOrdersToListOrderResponse listOrdersToListOrderResponse;
    OrdersRequestToOrders ordersRequestToOrders;

    CartItemToOrderItem cartItemToOrderItem;
    @Override
    public ResponseEntity<MessageResponse> createOrders(OrdersRequest ordersRequest) {

        Orders orders = ordersRequestToOrders.toOrders(ordersRequest);

        List<CartItem> cartItemList = orders.getUser().getCartList().get(0).getCartItemList();
        if (cartItemList.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("wrong"));
        }
        ordersRepository.save(orders);

//        List<CartItem> cartItemList = cartItemRepository.findAllByCart(orders.getUser().getCartList().get(0));
        for(CartItem cartItem : cartItemList) {
            OrderItem orderItem = cartItemToOrderItem.toOrderItem(cartItem);
            if (orderItem != null) {
                orderItem.setOrders(orders); // Update reference to Orders
                orderItemRepository.save(orderItem);
            }

        }
        cartItemRepository.deleteAllInBatch(cartItemList);
        return  ResponseEntity.ok(new MessageResponse("oke"));
    }

    @Override
    public ResponseEntity<ListOrderResponse> getAllOrderResponseForUserByStatus(Integer status, Long noPage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//        Customer customer = user.getCustomer();
        List<Integer> statusList = new ArrayList<>();
        if(status == 1) {
            statusList.add(1);
            statusList.add(2);
            statusList.add(3);
        }else {
            statusList.add(4);
            statusList.add(5);
        }
        List<Orders> ordersList = ordersRepository.findAllByUserAndOrderStatusIn(user, statusList);
        return ResponseEntity.ok(listOrdersToListOrderResponse.toListOrderResponse(ordersList));
    }

    @Override
    public ResponseEntity<ListOrderResponse> getAllOrderResponseForAdminByStatus(Integer status, Long noPage) {
        List<Orders> ordersList = ordersRepository.findAllByOrderStatus(status);
        ListOrderResponse listOrderResponse = listOrdersToListOrderResponse.toListOrderResponse(ordersList);
        return ResponseEntity.ok(listOrderResponse);

    }

    @Override
    public ResponseEntity<MessageResponse> changeOrderStatus(Integer status, Long id) {
        Orders orders = ordersRepository.findById(id).orElse(null);
        if(orders != null) {
            orders.setOrderStatus(status);
            ordersRepository.save(orders);
            return ResponseEntity.ok(new MessageResponse("oke"));
        }else {
            return  ResponseEntity.ok(new MessageResponse("not found orders by id"));
        }


    }

//0    @Override
//    public OrdersResponse getOrdersResponseByID(Long id) {
//        Orders orders = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("not found orders by id"));
//        return ordersToOrdersResponse.toOrdersResponse(orders);
//    }
}
