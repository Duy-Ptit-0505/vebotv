package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.CartItem;
import six.sportswears.model.OrderItem;
import six.sportswears.repository.SportswearRepository;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemToOrderItem {
    ModelMapper modelMapper;

    public OrderItem toOrderItem(CartItem cartItem) {
        OrderItem orderItem = modelMapper.map(cartItem, OrderItem.class);
        orderItem.setId(null);
        orderItem.setQuantity_ordered(cartItem.getQuantity());
        orderItem.setTotal_price(orderItem.getQuantity_ordered() * orderItem.getSportswear().getPrice());
        return orderItem;
    }
}
