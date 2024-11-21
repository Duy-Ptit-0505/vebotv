package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ShippingResponse {
    private Long id;

    private String shippingMethod;

    private String shippingStatus;

    private Double shippingFee;
}
