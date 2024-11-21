package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.OrderItem;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.response.OrderItemResponse;
import six.sportswears.repository.SportswearRepository;

import java.text.NumberFormat;
import java.util.Locale;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemToOrderItemResponse {
    ModelMapper modelMapper;
    SportswearRepository sportswearRepository;
    public String editPrice(Double number) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(number);
    }
    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse orderItemResponse = modelMapper.map(orderItem, OrderItemResponse.class);
        Sportswear sportswear = orderItem.getSportswear();
        orderItemResponse.setSportswear_name(sportswear.getName());
        orderItemResponse.setTotalAmount(editPrice(orderItem.getTotal_price()));
        orderItemResponse.setImage(orderItem.getSportswear().getMain_image());
        orderItemResponse.setSportswearID(sportswear.getId());

        return orderItemResponse;
    }
}
