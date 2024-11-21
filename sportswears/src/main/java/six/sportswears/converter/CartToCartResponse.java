package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.payload.response.CartItemResponse;
import six.sportswears.payload.response.CartResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartToCartResponse {
    ModelMapper modelMapper;
    CartItemToCartItemResponse cartItemToCartItemResponse;
    public CartResponse toCartResponse(List<CartItem> cartItemList, Long noPage) {



            Cart cart = new Cart();
            cart.setCartItemList(cartItemList);


            List<CartItemResponse> cartItemResponseList = new ArrayList<>();
            for(CartItem x : cartItemList) {
                cartItemResponseList.add(cartItemToCartItemResponse.toCartItemResponse(x));
            }


            Pageable pageable = PageRequest.of((int)(noPage -1L), 10);
            long start = pageable.getOffset();
            long end;
            if(start + pageable.getPageSize() > cartItemResponseList.size()) {
                end = (long) cartItemResponseList.size();
            } else {
                end = start + pageable.getPageSize();
            }
            cartItemResponseList = cartItemResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));

            Page<CartItemResponse> orderItemResponsePage = new PageImpl<>(cartItemResponseList, pageable, cartItemList.size());


            CartResponse cartResponse = new CartResponse();
            cartResponse.setCartItemResponseList(cartItemResponseList);
            cartResponse.setTotalAmount(cart.getCartAmount());
            cartResponse.setTotalQuantity(cart.getTotalQuantity());
            cartResponse.setCurrentPage(noPage);
            cartResponse.setTotalPage((long) orderItemResponsePage.getTotalPages());
            return cartResponse;

    }
}
