package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.model.Payment;
import six.sportswears.model.Shipping;
import six.sportswears.payload.response.CartItemResponse;
import six.sportswears.payload.response.CheckoutResponse;
import six.sportswears.payload.response.PaymentResponse;
import six.sportswears.payload.response.ShippingResponse;
import six.sportswears.repository.PaymentRepository;
import six.sportswears.repository.ShippingRepository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListCartItemToCheckoutResponse {
    CartItemToCartItemResponse cartItemToCartItemResponse;
    ShippingRepository shippingRepository;
    PaymentRepository paymentRepository;
    PaymentToPaymentResponse paymentToPaymentResponse;
    ShippingToShippingResponse shippingToShippingResponse;

    public CheckoutResponse toCheckoutResponse(List<CartItem> cartItemList, Long noPage, Long cartID){
        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);


        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        for(CartItem x : cartItemList) {
            cartItemResponseList.add(cartItemToCartItemResponse.toCartItemResponse(x));
        }


        Pageable pageable = PageRequest.of((int)(noPage -1L), 5);
        long start = pageable.getOffset();
        long end;
        if(start + pageable.getPageSize() > cartItemResponseList.size()) {
            end = (long) cartItemResponseList.size();
        } else {
            end = start + pageable.getPageSize();
        }
        cartItemResponseList = cartItemResponseList.subList(Math.toIntExact(start), Math.toIntExact(end));

        Page<CartItemResponse> orderItemResponsePage = new PageImpl<>(cartItemResponseList, pageable, cartItemList.size());


        List<Payment> paymentList= paymentRepository.findAllByPaymentStatus(1);
        List<Shipping> shippingList = shippingRepository.findAllByShippingStatus(1);
        List<PaymentResponse> paymentResponseList = new ArrayList<>();
        List<ShippingResponse> shippingResponseList = new ArrayList<>();
        for(Payment x : paymentList) {
            paymentResponseList.add(paymentToPaymentResponse.toPaymentResponse(x));
        }
        for(Shipping x : shippingList) {
            shippingResponseList.add(shippingToShippingResponse.toShippingResponse(x));
        }
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setCartItemResponseList(cartItemResponseList);
        checkoutResponse.setShippingResponseList(shippingResponseList);
        checkoutResponse.setPaymentResponseList(paymentResponseList);
        checkoutResponse.setTotalQuantity(cart.getTotalQuantity());
        checkoutResponse.setTotalAmount(cart.getCartAmount());
        checkoutResponse.setCurrentPage(noPage);
        checkoutResponse.setTotalPage((long) orderItemResponsePage.getTotalPages());
        checkoutResponse.setCartID(cartID);
        return  checkoutResponse;
    }
}
