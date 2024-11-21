package six.sportswears.payload.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SportswearRevenueResponseInAMonth {
    private Integer month;
    private Long quantity;
    private Double revenue;
}
