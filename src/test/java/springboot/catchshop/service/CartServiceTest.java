package springboot.catchshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springboot.catchshop.domain.Cart;
import springboot.catchshop.domain.Product;
import springboot.catchshop.domain.Role;
import springboot.catchshop.domain.User;
import springboot.catchshop.dto.CartResponseDto;
import springboot.catchshop.repository.CartRepository;
import springboot.catchshop.repository.ProductRepository;
import springboot.catchshop.repository.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// CartService Test
// author: soohyun, last modified: 22.02.13

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired CartRepository cartRepository;
    @Autowired CartService cartService;
    @Autowired UserRepository userRepository;
    @Autowired ProductRepository productRepository;
    @Autowired EntityManager em;

    private User user;
    private Product product;
    private int count = 1;

    @BeforeEach
    public void beforeEach() {
        user = new User("user1", "user1", "user1", "01012345678", "road1", "detail1", "11111", "USER", LocalDateTime.now());
        userRepository.save(user);
        product = new Product();
        product.changePrice(10000);
        productRepository.save(product);
    }

//    @DisplayName("장바구니 생성")
//    class addCart {
//
//        @Test
//        @DisplayName("성공")
//        public void success() {
//            // when
//            Long addId = cartService.addCart(user.getId(), product.getId(), count);
//
//            // then
//            Optional<Cart> findCart = cartRepository.findById(addId);
//
//            assertEquals(findCart.get().getId(), addId);
//            assertEquals(findCart.get().getUserId(), user.getId());
//            assertEquals(findCart.get().getProduct(), product);
//            assertEquals(findCart.get().getCartCount(), count);
//        }
//    }

    @Test
    @DisplayName("장바구니 생성")
    public void addCart() throws Exception {

        // when
        Long addId = cartService.addCart(user.getId(), product.getId(), count);

        // then
        Optional<Cart> findCart = cartRepository.findById(addId);

        assertEquals(findCart.get().getId(), addId);
        assertEquals(findCart.get().getUserId(), user.getId());
        assertEquals(findCart.get().getProduct(), product);
        assertEquals(findCart.get().getCartCount(), count);
    }

    @Test
    @DisplayName("장바구니 조회")
    public void cartList() throws Exception {

        // given
        cartService.addCart(user.getId(), product.getId(), count);

        // when
        CartResponseDto carts = cartService.cartList(user.getId());

        // then
        assertEquals(carts.getCartList().size(), 1);
        assertEquals(carts.getCartList().get(0).getCartCount(), 1);
        assertEquals(carts.getCartList().get(0).getTotalProductPrice(), 10000);
        assertEquals(carts.getTotalAllProductPrice(), 10000);
        assertEquals(carts.getDeliveryPrice(), 3000);
        assertEquals(carts.getTotalPayPrice(), 13000);
    }

    @Test
    @DisplayName("장바구니 수정")
    public void updateCart() {

        // given
        Long cartId = cartService.addCart(user.getId(), product.getId(), count);

        // when
        Long updateId = cartService.updateCart(cartId, 2);

        // then
        Optional<Cart> findCart = cartRepository.findById(updateId);

        assertEquals(findCart.get().getId(), updateId);
        assertEquals(findCart.get().getUserId(), user.getId());
        assertEquals(findCart.get().getProduct(), product);
        assertEquals(findCart.get().getCartCount(), 2);
    }

    @Test
    @DisplayName("장바구니 삭제")
    public void deleteCart() {

        // given
        Long cartId = cartService.addCart(user.getId(), product.getId(), count);

        // when
        Long deleteId = cartService.deleteCart(cartId);

        // then
        Optional<Cart> findCart = cartRepository.findById(deleteId);

        assertEquals(findCart, Optional.empty());
    }
}