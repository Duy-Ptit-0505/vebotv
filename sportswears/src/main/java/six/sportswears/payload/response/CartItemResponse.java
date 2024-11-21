package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private String sportswear_name;
    private String image;
    private String size;
    private Long quantity;
    private Double price;
    private Double totalAmount;
}
