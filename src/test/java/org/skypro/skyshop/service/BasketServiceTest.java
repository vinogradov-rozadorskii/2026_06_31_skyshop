package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProductThrowsExceptionWhenProductDoesNotExist() {
        UUID invalidId = UUID.randomUUID();
        when(storageService.getProductById(invalidId))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> basketService.addProduct(invalidId));
    }

    @Test
    void addProductCallsProductBasketWhenProductExists() {
        UUID validId = UUID.randomUUID();
        Product product =
                new SimpleProduct(validId, "TestProduct", 100);
        when(storageService.getProductById(validId))
                .thenReturn(Optional.of(product));

        basketService.addProduct(validId);

        verify(productBasket, times(1)).addProduct(validId);
    }

    @Test
    void getUserBasketReturnsEmptyBasketWhenProductBasketIsEmpty() {
        when(productBasket.getProducts()).thenReturn(Map.of());

        UserBasket userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems()).isEmpty();
        assertThat(userBasket.getTotal()).isZero();
    }

    @Test
    void getUserBasketReturnsItemsAndCorrectTotalWhenProductsExist() {
        UUID productId = UUID.randomUUID();
        int quantity = 3;
        int price = 100;
        Product product =
                new SimpleProduct(productId, "TestProduct", price);

        when(productBasket.getProducts())
                .thenReturn(Map.of(productId, quantity));
        when(storageService.getProductById(productId))
                .thenReturn(Optional.of(product));

        UserBasket userBasket = basketService.getUserBasket();

        assertThat(userBasket.getItems()).hasSize(1);
        assertThat(userBasket.getItems().get(0).getProduct())
                .isEqualTo(product);
        assertThat(userBasket.getItems().get(0).getQuantity())
                .isEqualTo(quantity);
        assertThat(userBasket.getTotal())
                .isEqualTo(price * quantity);
    }
}