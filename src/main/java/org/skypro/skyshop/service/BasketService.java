package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    @Autowired
    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(NoSuchProductException::new);

        productBasket.addProduct(product.getId());
    }

    public UserBasket getUserBasket() {
        List<BasketItem> items = productBasket.getProducts()
                .entrySet()
                .stream()
                .map(entry -> new BasketItem(
                        storageService.getProductById(entry.getKey())
                                .orElseThrow(NoSuchProductException::new),
                        entry.getValue()
                ))
                .toList();

        return new UserBasket(items);
    }
}