package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.*;
import six.sportswears.payload.request.OrdersRequest;
import six.sportswears.repository.CartRepository;
import six.sportswears.repository.PaymentRepository;
import six.sportswears.repository.ShippingRepository;
import six.sportswears.repository.UserRepository;


import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersRequestToOrders {
    ShippingRepository shippingRepository;
    PaymentRepository paymentRepository;
    CartRepository cartRepository;

    ModelMapper modelMapper;
    public Orders toOrders(OrdersRequest ordersRequest) {
        Orders orders = new Orders();
        Shipping shipping = shippingRepository.findById(ordersRequest.getShipping_id()).orElseThrow(() -> new RuntimeException("not found shipping"));
        Payment payment = paymentRepository.findById(ordersRequest.getPayment_id()).orElseThrow(() -> new RuntimeException("not found payment"));
        Cart cart = cartRepository.findById(ordersRequest.getCartID()).orElseThrow(() -> new RuntimeException("not found cart"));

        orders.setShipping(shipping);
        orders.setPayment(payment);

        orders.setOrder_date(new Date());
        orders.setContact(ordersRequest.getFullName() + " - " + ordersRequest.getPhone());
        orders.setShipping_address(ordersRequest.getAddress());

        orders.setOrderStatus(1);
        orders.setUser(cart.getUser());
        orders.setOrder_total(cart.getCartAmount());
        return  orders;
    }
}
