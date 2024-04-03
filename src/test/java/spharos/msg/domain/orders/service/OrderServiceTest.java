package spharos.msg.domain.orders.service;

import static org.assertj.core.api.Assertions.assertThat;
import static spharos.msg.domain.orders.dto.OrderRequest.OrderProduct;

import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderResultDto;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.config.QueryDslConfig;

@Import({OrderService.class, QueryDslConfig.class})
@DataJpaTest
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=update"})
class OrderServiceTest {

    /**
     * 현재 올바르지 않은 ORDERS 이므로 테스트를 주석화
     */
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UsersRepository usersRepository;

    private OrderSheetDto orderSheetDto;
    private OrderProduct orderProduct1;
    private OrderProduct orderProduct2;

    private OrderProduct createOrderProduct(Long productId, Long optionId, int quantity,
        int deliveryFee, BigDecimal discountRate, long salePrice, long originPrice) {
        return new OrderProduct(
            productId, optionId, quantity, deliveryFee, discountRate, salePrice, originPrice);
    }

    @BeforeEach
    void before() {
        orderProduct1 = createOrderProduct(1L, 1L, 1, 1000,
            new BigDecimal("34"), 2000, 3000);
        orderProduct2 = createOrderProduct(2L, 2L, 1, 2000,
            new BigDecimal("34"), 2000, 3000);
        orderSheetDto = new OrderSheetDto(1L, "userA", "phone", "부산",
            List.of(orderProduct1, orderProduct2));

        orderService.saveOrder(orderSheetDto);
    }

    @AfterEach
    void after() {
        usersRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 Id로 주문 정보를 가져올 수 있어야 한다.")
    void OrderServiceTest() {
        usersRepository.save(
            Users.builder().userName("ABC").email("tjdvy963@naver.com").loginId("tjdvy963")
                .password("password").address("address").phoneNumber("01023112313").build());
        Users users = usersRepository.findById(1L).get();
        List<OrderHistoryDto> orderHistories = orderService.findOrderHistories(users.getUuid());

        List<Long> result = orderHistories.stream().map(OrderHistoryDto::getTotalPrice).toList();
        Assertions.assertThat(result)
            .hasSize(1)
            .isEqualTo(List.of(7000L));
    }

    @Test
    @DisplayName("생성된 orderResultDto의 주소, 휴대폰 번호가 입력과 일치해야 한다.")
    void 주문_생성_성공_테스트() {
        //when
        OrderResultDto orderResultDto = orderService.saveOrder(orderSheetDto);
        //then
        assertThat(orderResultDto.toString())
            .contains(orderSheetDto.getAddress(), orderSheetDto.getBuyerPhoneNumber());
    }

    @Test
    @DisplayName("totalPrice는 (salePrice * 개수) + deliveryFee의 합이어야 한다.")
    void 총_금액합_테스트() {
        //when
        OrderResultDto orderResultDto = orderService.saveOrder(orderSheetDto);
        //then
        Long price1 = orderProduct1.getSalePrice() * orderProduct1.getOrderQuantity()
            + orderProduct1.getOrderDeliveryFee();
        Long price2 = orderProduct2.getSalePrice() * orderProduct2.getOrderQuantity()
            + orderProduct2.getOrderDeliveryFee();

        assertThat(orderResultDto.getTotalPrice()).isEqualTo(price1 + price2);
    }

    /*
    TODO : 수정해야 하는 로직
    @Test
    @DisplayName("주문자 정보 조회시 모든 정보가 일치해야한다.")
    void 주문자_정보_조회_성공_테스트() {
        OrderUserDto orderUserDto = orderService.findOrderUser("uuid");
        assertThat(orderUserDto.toString()).contains(
            "uuid", "tjdvy963@naver.com", "abcdsd", "01092312316");
    }*/
}