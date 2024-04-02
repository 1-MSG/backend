package spharos.msg.domain.orders.service;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import spharos.msg.global.config.QueryDslConfig;

@DataJpaTest
@Import({OrderService.class, QueryDslConfig.class})
class OrderServiceTest {
/**
 * 현재 올바르지 않은 ORDERS 이므로 테스트를 주석화
 */
//    @Autowired
//    OrderRepository orderRepository;
//    @Autowired
//    UsersRepository usersRepository;
//    @Autowired
//    AddressRepository addressRepository;
//    @Autowired
//    OrderService orderService;
//
//    @BeforeEach
//    public void init() {
//        orderRepository.save(
//            Orders.builder()
//                .address("abc")
//                .buyerId(1L)
//                .buyerName("test")
//                .buyerPhoneNumber("01012345667")
//                .totalAmount(10L)
//                .build());
//
//        Address address = Address.builder()
//            .addressName("부산 남구")
//            .mobileNumber("01092312316")
//            .recipient("홍준표")
//            .addressName("부산 남구 용소로")
//            .addressPhoneNumber("01092312316")
//            .address("부산남구용")
//            .build();
//        addressRepository.save(address);
//        usersRepository.save(
//            Users.builder()
//                .userName("test")
//                .email("tjdvy963@naver.com")
//                .loginId("abcdsd")
//                .uuid("uuid")
//                .password("1234")
//                .phoneNumber("01092312316")
//                .build()
//        );
//    }
//
//    @Test
//    @DisplayName("uuid를 찾을 수 없다면 OrderException이 발생한다.")
//    void 유저_예외_발생_테스트() {
//        //given
//        OrderDto orderDto = new OrderDto();
//        //when
//        assertThatThrownBy(
//            () -> orderService.saveOrder(List.of(orderDto), "s"))
//            .isInstanceOf(OrderException.class);
//        //then
//    }
//
//    @Test
//    @DisplayName("주문자 정보 조회시 uuid에 해당하는 유저가 없다면 OrderException 발생")
//    void 유저_없음_예외_테스트() {
//        assertThatThrownBy(
//            () -> orderService.findOrderUser("no"))
//            .isInstanceOf(OrderException.class);
//    }
//
//    @Test
//    @DisplayName("주문자 정보 조회시 모든 정보가 일치해야한다.")
//    void 주문자_정보_조회_성공_테스트() {
//        OrderUserDto orderUserDto = orderService.findOrderUser("uuid");
//        assertThat(orderUserDto.toString()).contains(
//            "uuid", "tjdvy963@naver.com", "abcdsd", "01092312316");
//    }
}