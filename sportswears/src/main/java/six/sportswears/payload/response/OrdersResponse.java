package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrdersResponse {
    private Long id;
    private List<OrderItemResponse> orderItemResponseList;
    private Double totalOrder;
    private Long totalQuantityOrder;
    private String shippingDate;
    private String deliveryDate;
    private String paymentMethod;
    private ShippingResponse shippingResponse;
    private String shippingAddress;
    private String paymentDate;
    private String paymentAmount;
    private String createDate;
    private Integer status;
    private String receiver;
    private String phone;
}
