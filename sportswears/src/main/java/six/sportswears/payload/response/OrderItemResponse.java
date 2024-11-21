package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private String sportswear_name;
    private String image;
    private String size;
    private Long quantity_ordered;
    private Double unitPrice;
    private String totalAmount;
    private Long sportswearID;
    private Integer ratingValue;
    private String comment;
}
