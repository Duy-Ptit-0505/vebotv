package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import six.sportswears.model.Orders;
import six.sportswears.payload.response.ListOrderResponse;
import six.sportswears.payload.response.OrdersResponse;


import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListOrdersToListOrderResponse {
    OrdersToOrdersResponse ordersToOrdersResponse;
    public ListOrderResponse toListOrderResponse(List<Orders> ordersList) {
        ListOrderResponse listOrderResponse = new ListOrderResponse();
        List<OrdersResponse> ordersResponseList = new ArrayList<>();
        for(Orders x : ordersList) {
            ordersResponseList.add(ordersToOrdersResponse.toOrdersResponse(x));
        }
        listOrderResponse.setOrdersResponseList(ordersResponseList);
        return  listOrderResponse;
    }

}
