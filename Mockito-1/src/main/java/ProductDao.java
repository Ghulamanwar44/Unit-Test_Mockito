public interface ProductDao {

    int getAvailableProducts(Product product);
    int orderProduct(Product product, int orderedQuantity);

}
