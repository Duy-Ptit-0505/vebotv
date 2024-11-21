package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private List<CartItemResponse> cartItemResponseList;
    private List<PaymentResponse> paymentResponseList;
    private List<ShippingResponse> shippingResponseList;
    private Long totalQuantity;
    private Double totalAmount;
    private Long currentPage;
    private Long totalPage;
    private Long cartID;
 }
