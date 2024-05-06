package ru.mirea.MeatMarket.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.repositories.ProductRepo;
import ru.mirea.MeatMarket.services.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProductByName() {
        Product expectedProduct = new Product();
        expectedProduct.setName("TestProduct");

        when(productRepo.findByName("TestProduct")).thenReturn(Optional.of(expectedProduct));

        Product actualProduct = productService.getProductByName("TestProduct");

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void testGetProductById() {
        Product expectedProduct = new Product();
        expectedProduct.setId(1L);

        when(productRepo.findById(1L)).thenReturn(Optional.of(expectedProduct));

        Product actualProduct = productService.getProductById(1L);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();

        List<Product> expectedProducts = Arrays.asList(product1, product2);

        when(productRepo.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllProducts();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testAddProduct() {
        Product productToAdd = new Product();
        productToAdd.setId(1L);

        productService.addProduct(productToAdd);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepo, times(1)).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        assertEquals(productToAdd, capturedProduct);
    }

    @Test
    void testDeleteProductById() {
        when(productRepo.deleteProductById(1L)).thenReturn(Optional.of(1));

        int deletedCount = productService.deleteProductById(1L);

        assertEquals(1, deletedCount);
    }

    @Test
    void testSortProducts() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("Product1", 10.0, 1.5, "Category1"));
        expectedProducts.add(new Product("Product2", 20.0, 2.0, "Category2"));

        when(productRepo.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.sortProducts("name", false);

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void testFilterAndSortProducts() {
        List<Product> allProducts = new ArrayList<>();
        allProducts.add(new Product("Product1", 10.0, 1.5, "Category1"));
        allProducts.add(new Product("Product2", 20.0, 2.0, "Category2"));

        when(productRepo.findAll()).thenReturn(allProducts);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product("Product1", 10.0, 1.5, "Category1"));

        List<Product> actualProducts = productService.filterAndSortProducts("Category1", "name", "asc");

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetAllCategories() {
        Product product1 = new Product();
        product1.setCategory("Category1");

        Product product2 = new Product();
        product2.setCategory("Category2");

        Product product3 = new Product();
        product3.setCategory("Category1");

        List<String> expectedCategories = Arrays.asList("Category1", "Category2");

        when(productRepo.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

        List<String> actualCategories = productService.getAllCategories();

        assertEquals(expectedCategories.size(), actualCategories.size());
        assertEquals(expectedCategories, actualCategories);
    }
}
