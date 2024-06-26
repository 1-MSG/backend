package spharos.msg.domain.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.options.service.OptionsServiceV2;
import spharos.msg.domain.orders.converter.OrderProductConverter;
import spharos.msg.domain.orders.converter.OrdersConverter;
import spharos.msg.domain.orders.dto.OrderRequest.OrderProductDetail;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderPrice;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.orders.repository.OrderProductRepository;
import spharos.msg.domain.product.converter.ProductSalesInfoConverter;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductSalesInfo;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.product.repository.ProductSalesInfoRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.ProductNotExistException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final ProductSalesInfoRepository productSalesInfoRepository;
    private final OptionsServiceV2 optionsService;

    @Transactional
    public void saveAllByOrderSheet(OrderSheetDto orderSheetDto, Orders orders) {
        List<OrderProductDetail> orderProductDetails = orderSheetDto.getOrderProductDetails();
        List<OrderProduct> orderProductEntities = new ArrayList<>();

        for (OrderProductDetail detail : orderProductDetails) {
            Product product = findProduct(detail);
            updateProductOrderQuantity(product.getProductSalesInfo(), detail.getOrderQuantity());
            OrderProduct orderProductEntity = createOrderEntity(detail, product, orders);
            orderProductEntities.add(orderProductEntity);
        }

        orderProductRepository.saveAll(orderProductEntities);
    }

    public List<OrderPrice> createOrderPricesByOrderIdV1(Long orderId) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrderId(orderId);
        return orderProducts
            .stream()
            .map(OrdersConverter::toDto)
            .toList();
    }

    public List<OrderPrice> createOrderPricesByOrderIdV2(Long orderId) {
        return orderProductRepository.findOrderPricesByOrderId(orderId);
    }

    private void updateProductOrderQuantity(ProductSalesInfo productSalesInfo, int orderQuantity) {
        ProductSalesInfo updated = ProductSalesInfoConverter
            .toEntity(productSalesInfo, orderQuantity);

        productSalesInfoRepository.save(updated);
    }

    private Product findProduct(OrderProductDetail detail) {
        return productRepository
            .findById(detail.getProductId())
            .orElseThrow(() -> new ProductNotExistException(ErrorStatus.PRODUCT_ERROR));
    }

    private OrderProduct createOrderEntity(OrderProductDetail orderProductDetail,
        Product product, Orders orders) {
        String productOptions = getProductOptions(orderProductDetail);
        return OrderProductConverter.toEntity(orderProductDetail, product, orders, productOptions);
    }

    private String getProductOptions(OrderProductDetail detail) {
        Long productOptionId = detail.getProductOptionId();
        return optionsService.getOptions(productOptionId);
    }
}
