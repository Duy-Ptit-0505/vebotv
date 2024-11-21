package six.sportswears.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrdersRequest {
    private Long shipping_id;
    private Long payment_id;
    private String fullName;
    private String address;
    private String phone;
    private Long cartID;
}
