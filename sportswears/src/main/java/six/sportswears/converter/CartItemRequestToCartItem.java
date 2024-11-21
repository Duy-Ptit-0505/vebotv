package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.CartItem;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.request.CartItemRequest;
import six.sportswears.repository.SportswearRepository;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemRequestToCartItem {
    ModelMapper modelMapper;
    SportswearRepository sportswearRepository;
    public CartItem toCartItem(CartItemRequest cartItemRequest) {
        CartItem cartItem = modelMapper.map(cartItemRequest, CartItem.class);
        Sportswear sportswear = sportswearRepository.findById(cartItemRequest.getSportswear_id()).orElseThrow();
        cartItem.setSportswear(sportswear);
//        orderItem.setStatus(1);
        return cartItem;
    }
}
