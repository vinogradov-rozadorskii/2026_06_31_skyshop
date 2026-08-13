package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void searchReturnsEmptyCollectionWhenStorageIsEmpty() {
        when(storageService.getAllSearchables()).thenReturn(List.of());

        Collection<SearchResult> results = searchService.search("Test");

        assertThat(results).isEmpty();
    }

    @Test
    void searchReturnsEmptyCollectionWhenThereAreNoMatches() {
        Product product = new SimpleProduct(
                UUID.randomUUID(), "OtherProduct", 100);
        Collection<Searchable> searchables = List.of(product);
        when(storageService.getAllSearchables()).thenReturn(searchables);

        Collection<SearchResult> results = searchService.search("Test");

        assertThat(results).isEmpty();
    }

    @Test
    void searchReturnsOneResultWhenMatchingObjectExists() {
        Product product = new SimpleProduct(
                UUID.randomUUID(), "TestProduct", 100);
        Collection<Searchable> searchables = List.of(product);
        when(storageService.getAllSearchables()).thenReturn(searchables);

        Collection<SearchResult> results = searchService.search("Test");

        assertThat(results).hasSize(1);
        assertThat(results.iterator().next().getName())
                .isEqualTo("TestProduct");
    }
}