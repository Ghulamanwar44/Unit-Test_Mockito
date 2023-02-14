import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MockCreationTest {

    @Mock
    private ProductDao productDao;
    @Mock
    private Product product;
    @Mock
    private ProductService productService;
    private int purchaseQuantity = 15;

    @BeforeEach
    public void setUpMock() {
        product = mock(Product.class);
        productDao = mock(ProductDao.class);
        productService = new ProductService();
        // product = mock(Product.class);
        // productDao = mock(ProductDao.class);
        productService.setProductDao(productDao);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(product);
        assertNotNull(productDao, "Successfully mocked! ");
    }

    // Stubbing means simulating the behavior of a mock object’s method
//    @Test
//    @DisplayName("Stub Test")
//    public void testBuy() throws InsufficientProductsException {
//        when(productDao.getAvailableProducts(product)).thenReturn(30); //  we are stubbing getAvailableProducts(product) of ProductDao to return 30.
//        assertEquals(30,productDao.getAvailableProducts(product));
//        productService.buy(product,5);
//        verify(productDao).orderProduct(product,5);
    // Our objective is to test ProductService, and until now we only mocked Product and ProductDao and stubbed getAvailableProducts() of ProductDao.

    //  First, we want to verify whether it’s calling the orderProduct() of ProductDao with the required set of parameters.

    @Test
    public void testBuy() throws InsufficientProductsException {
        when(productDao.getAvailableProducts(product)).thenReturn(30);
        assertEquals(30, productDao.getAvailableProducts(product));
        productService.buy(product, 5);
        verify(productDao).orderProduct(product, 5);
    }


    @Test
    public void testBuyDemo() throws InsufficientProductsException {
        int availableQuantity = 30;
        System.out.println("Stubbing getAvailableProducts(product) to return " + availableQuantity);
        when(productDao.getAvailableProducts(product)).thenReturn(availableQuantity);
        System.out.println("Calling ProductService.buy(product," + purchaseQuantity + ")");
        productService.buy(product, purchaseQuantity);
        System.out.println("Verifying ProductDao(product, " + purchaseQuantity + ") is called");
        verify(productDao).orderProduct(product, purchaseQuantity);
        System.out.println("Verifying getAvailableProducts(product) is called at least once");
        verify(productDao, atLeastOnce()).getAvailableProducts(product);
        System.out.println("Verifying order of method calls on ProductDao: First call getAvailableProducts() followed by orderProduct()");
        InOrder order = inOrder(productDao);
        order.verify(productDao).getAvailableProducts(product);
        order.verify(productDao).orderProduct(product, purchaseQuantity);
    }


    @Test
    public void purchaseWithInsufficientAvailableQuantity() throws InsufficientProductsException {
        int availableQuantity = 3;
        System.out.println("Stubbing getAvailableProducts(product) to return " + availableQuantity);
        when(productDao.getAvailableProducts(product)).thenReturn(availableQuantity);

        assertThrows(InsufficientProductsException.class, () -> {
            System.out.println("productService.buy(product" + purchaseQuantity + ") should throw InsufficientProductsException");
            productService.buy(product, purchaseQuantity);
        });

        System.out.println("InsufficientProductsException has been thrown");
        verify(productDao, times(0)).orderProduct(product, purchaseQuantity);
        System.out.println("Verified orderProduct(product, " + purchaseQuantity + ") is not called");
    }

}
//    @Test
//    public void purchaseWithInsufficientAvailableQuantity() throws InsufficientProductsException {
//        int availableQuantity = 3;
//        System.out.println("Stubbing getAvailableProducts(product) to return " + availableQuantity);
//        when(productDao.getAvailableProducts(product)).thenReturn(availableQuantity);
//
//        try {
//            assertThrows(InsufficientProductsException.class, () -> {
//                System.out.println("productService.buy(product" + purchaseQuantity + ") should throw InsufficientProductsException");
//                productService.buy(product, purchaseQuantity);
//            }catch(InsufficientProductsException e){
//                System.out.println("InsufficientProductsException has been thrown");
//                verify(productDao, times(0)).orderProduct(product, purchaseQuantity);
//                System.out.println("Verified orderProduct(product, " + purchaseQuantity + ") is not called");
//                throw e;
//            });
//
//
//        }
//    }