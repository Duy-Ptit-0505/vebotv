package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.CartItem;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.response.CartItemResponse;
import six.sportswears.repository.SportswearRepository;

import java.text.NumberFormat;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemToCartItemResponse {
    ModelMapper modelMapper;
    SportswearRepository sportswearRepository;

    public CartItemResponse toCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = modelMapper.map(cartItem, CartItemResponse.class);
        Sportswear sportswear = cartItem.getSportswear();
        cartItemResponse.setSportswear_name(sportswear.getName());
        cartItemResponse.setPrice(sportswear.getPrice());
        cartItemResponse.setTotalAmount(cartItem.getQuantity() * sportswear.getPrice());
        cartItemResponse.setImage(cartItem.getSportswear().getMain_image());
        return cartItemResponse;
    }
}
