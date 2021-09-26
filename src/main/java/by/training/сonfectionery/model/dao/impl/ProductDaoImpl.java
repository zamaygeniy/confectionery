package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.util.Base64Coder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static by.training.сonfectionery.model.dao.ColumnName.*;

public class ProductDaoImpl extends ProductDao {

    private static final String SQL_FIND_ALL_PRODUCTS = """
            SELECT id, name, price, description, weight, image, number_in_stock
            FROM products
            JOIN product_type ON products.product_type_id = product_type.id; 
            """;
    private static final String SQL_FIND_PRODUCT_BY_ID = """
            SELECT id, name, price, description, weight, image, number_in_stock
            FROM products
            JOIN product_type ON products.product_type_id = product_type.id;
            WHERE products.id = ?;
            """;
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_TYPE_ID = """
            SELECT id, name, price, description, weight, image, number_in_stock
            FROM products
            JOIN product_type ON products.product_type_id = product_type.id;
            WHERE product_type_id = ?;
            """;
    private static final String SQL_DELETE_PRODUCT_BY_ID = """
            DELETE FROM products WHERE id = ?;
            """;
    private static final String SQL_CREATE_PRODUCT = """
            INSERT INTO products (name, price, description, weight, image, number_in_stock, product_type_id)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_PRODUCT = """
            UPDATE products
            SET name = ?, price = ?, description = ?, weight = ?, image = ?, number_in_stock = ?, product_type_id = ?
            WHERE products.id = ?;
            """;
    private static final String SQL_UPDATE_PRODUCT_NUMBER_IN_STOCK = """
            UPDATE products
            SET number_in_stock = ?
            WHERE id = ?;
            """;

    private static final String SQL_ADD_PRODUCT_TO_CART = """
            INSERT INTO orders_has_products (orders_id, products_id, amount)
            VALUES (?, ?, ?);
            """;


    @Override
    public List<Product> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS)) {
            List<Product> products = new LinkedList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = buildProduct(resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all products", e);
        }
    }

    @Override
    public Optional<Product> findById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = buildProduct(resultSet);
                    return Optional.of(product);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to find product by id", e);
        }
    }


    @Override
    public boolean deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete product by id", e);
        }
    }

    /*@Override
    public boolean create(Product product) throws DaoException {
        throw new UnsupportedOperationException("This method unavailable in ProductDao," +
                " use method create(Product product, String password)");
    }*/

    @Override
    public boolean addProductToCart(Product product, int numberOfProduct, Order order) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_PRODUCT_TO_CART)) {
            statement.setInt(1, order.getId());
            statement.setInt(2, product.getId());
            statement.setInt(3, numberOfProduct);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to add product to cart");
        }
    }

    @Override
    public boolean create(Product product) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getWeight());
            statement.setBlob(5, Base64Coder.decode(product.getImage()));
            statement.setInt(6, product.getNumberInStock());
            statement.setInt(7, product.getProductTypeId());
            boolean result = statement.executeUpdate() == 1;
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getInt(1));
                }
                return result;
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to create product", e);
        }
    }

    @Override
    public Product update(Product product) throws DaoException {
        Product oldProduct = findById(product.getId()).
                orElseThrow(() -> new DaoException("Failed to update product, product's id was not found"));
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getWeight());
            statement.setBlob(5, Base64Coder.decode(product.getImage()));
            statement.setInt(6, product.getNumberInStock());
            statement.setInt(7, product.getProductTypeId());
            statement.setInt(8, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update product", e);
        }
        return oldProduct;
    }


    @Override
    public Optional<Product> findProductByProductTypeId(int productTypeId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_TYPE_ID)) {
            statement.setInt(1, productTypeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = buildProduct(resultSet);
                    return Optional.of(product);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to find product by product type id", e);
        }
    }

    @Override
    public boolean updateProductNumberInStock(Product product, int newNumberInStock) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_NUMBER_IN_STOCK)) {
            statement.setInt(1, newNumberInStock);
            statement.setInt(2, product.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Failed to update product number in stock", e);
        }
    }


    private Product buildProduct(ResultSet resultSet) throws SQLException {

        return new Product.ProductBuilder()
                .setId(resultSet.getInt(ID))
                .setName(resultSet.getString(NAME))
                .setPrice(resultSet.getDouble(PRICE))
                .setDescription(resultSet.getString(DESCRIPTION))
                .setWeight(resultSet.getInt(WEIGTH))
                .setImage(resultSet.getBlob(IMAGE) == null ? "" : Base64Coder.encode(resultSet.getBlob(IMAGE).getBinaryStream()))
                .setNumberInStock(resultSet.getInt(NUMBER_IN_STOCK))
                .setProductTypeId(resultSet.getInt(PRODUCT_TYPE_ID))
                .createProduct();
    }

    @Override
    public List<Product> findAll(int offset, int noOfRecords) throws DaoException {
        String query = "SELECT * FROM products limit " + offset + ", " + noOfRecords;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            List<Product> products = new LinkedList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = buildProduct(resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all products", e);
        }
    }

}