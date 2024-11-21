package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.OrderItem;
import six.sportswears.model.Orders;
import six.sportswears.payload.response.OrderItemResponse;
import six.sportswears.payload.response.OrdersResponse;
import six.sportswears.repository.ShippingRepository;
import six.sportswears.utils.ConvertDate;


import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrdersToOrdersResponse {
    ModelMapper modelMapper;
    ShippingRepository shippingRepository;
    OrderItemToOrderItemResponse orderItemToOrderItemResponse;
    ShippingToShippingResponse shippingToShippingResponse;
    public OrdersResponse toOrdersResponse(Orders orders) {
        OrdersResponse ordersResponse = modelMapper.map(orders, OrdersResponse.class);
        List<OrderItem> orderItemList = orders.getOrderItemList();
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        Double amount = 0.0;
        for(OrderItem x : orderItemList) {
            amount += x. getTotal_price();
            orderItemResponseList.add(orderItemToOrderItemResponse.toOrderItemResponse(x));
        }

        ordersResponse.setShippingResponse(shippingToShippingResponse.toShippingResponse(orders.getShipping()));

        ordersResponse.setOrderItemResponseList(orderItemResponseList);
        ordersResponse.setTotalOrder(amount);
        ordersResponse.setTotalQuantityOrder((long) orderItemList.size());
        ordersResponse.setCreateDate(ConvertDate.convertDate(orders.getOrder_date()));
        ordersResponse.setReceiver(orders.getContact());

        return ordersResponse;
    }
}
