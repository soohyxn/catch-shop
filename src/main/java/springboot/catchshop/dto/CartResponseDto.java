package springboot.catchshop.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

// Cart Response Dto (조회시 장바구니 화면 세팅에 필요한 Dto)
// author: soohyun, last modified: 22.02.14

@Data
@Getter
public class CartResponseDto {

    private List<CartListDto> cartList; // 장바구니 목록
    private Long totalAllProductPrice; // 전체 상품 가격
    private Long deliveryPrice; // 배송비
    private Long totalPayPrice; // 최종 결제 금액

    // 생성 메서드
    public CartResponseDto(List<CartListDto> cartList) {
        this.cartList = cartList;
        this.totalAllProductPrice = calTotalAllProductPrice();
        this.deliveryPrice = calDeliveryPrice();
        this.totalPayPrice = totalAllProductPrice + deliveryPrice;
    }

    // 계산 메서드
    private Long calTotalAllProductPrice() {
        Long price = 0L;

        for (CartListDto cartListDto : cartList) {
            price += cartListDto.getTotalProductPrice();
        }

        return  price;
    }

    private Long calDeliveryPrice() {
        if (totalAllProductPrice >= 50000) {
            return 0L;
        } else {
            return 3000L;
        }
    }

}
