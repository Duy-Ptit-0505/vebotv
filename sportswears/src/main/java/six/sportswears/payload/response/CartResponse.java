package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartResponse {
    private List<CartItemResponse> cartItemResponseList;
    private Double totalAmount;
    private Long totalQuantity;
    private Long currentPage;
    private Long totalPage;
}
