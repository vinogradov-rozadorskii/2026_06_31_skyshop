package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        products = new HashMap<>();
        articles = new HashMap<>();
        fillTestData();
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        Collection<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(articles.values());
        return searchables;
    }

    private void fillTestData() {
        addProduct(new SimpleProduct(UUID.randomUUID(), "Молоко", 100));
        addProduct(new SimpleProduct(UUID.randomUUID(), "Хлеб", 80));
        addProduct(new DiscountedProduct(UUID.randomUUID(), "Сыр", 300, 20));
        addProduct(new FixPriceProduct(UUID.randomUUID(), "Чай"));
        addProduct(new DiscountedProduct(UUID.randomUUID(), "Кофе", 500, 10));
        addProduct(new SimpleProduct(UUID.randomUUID(), "Масло", 200));

        addArticle(new Article(
                UUID.randomUUID(),
                "Молочные продукты",
                "Молоко и сыр богаты кальцием."
        ));

        addArticle(new Article(
                UUID.randomUUID(),
                "Кофе",
                "Кофе помогает взбодриться утром."
        ));

        addArticle(new Article(
                UUID.randomUUID(),
                "Чай",
                "Зеленый чай полезен для здоровья."
        ));
    }

    private void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    private void addArticle(Article article) {
        articles.put(article.getId(), article);
    }
}