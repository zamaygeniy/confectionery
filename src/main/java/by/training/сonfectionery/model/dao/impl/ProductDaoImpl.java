package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.model.domain.Product;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.util.Base64Coder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.сonfectionery.model.dao.ColumnName.*;

public class ProductDaoImpl extends ProductDao {

    private static final String SQL_FIND_ALL_PRODUCTS = """
            SELECT products.id, name, price, description, weight, image
            FROM products
            JOIN product_type ON products.product_type_id = product_type.id; 
            """;
    private static final String SQL_FIND_PRODUCT_BY_ID = """
            SELECT products.id, name, price, description, weight, image, product_type_id
            FROM products
            JOIN product_type ON products.product_type_id = product_type.id
            WHERE products.id = ?;
            """;
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_TYPE_ID = """
            SELECT products.id, name, price, description, weight, image, product_type_id
            FROM products
            JOIN product_type ON products.product_type_id = product_type.id
            """;
    private static final String SQL_DELETE_PRODUCT_BY_ID = """
            DELETE FROM products WHERE id = ?;
            """;
    private static final String SQL_CREATE_PRODUCT = """
            INSERT INTO products (name, price, description, weight, image, product_type_id)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String SQL_UPDATE_PRODUCT = """
            UPDATE products
            SET name = ?, price = ?, description = ?, weight = ?, image = ?, product_type_id = ?
            WHERE products.id = ?;
            """;

    private static final String SQL_GET_NUMBER_OF_RECORDS = """
            SELECT COUNT(*) as count
            FROM products
            """;

    @Override
    public int getNumberOfRecords(List<Integer>productTypeId) throws DaoException {
        StringBuilder query = new StringBuilder(SQL_GET_NUMBER_OF_RECORDS + "WHERE product_type_id IN (");
        query.append("?,".repeat(productTypeId.size()));
        query = new StringBuilder(query.substring(0, query.length() - 1) + ")");
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < productTypeId.size(); i++) {
                statement.setInt(i + 1, productTypeId.get(i));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find number of products with product_type_id", e);
        }
    }

    @Override
    public List<Product> findProductByProductTypeId(int offset, int recordsPerPage, List<Integer> productTypeId, int sortBy) throws DaoException {
        StringBuilder query = new StringBuilder(SQL_FIND_PRODUCT_BY_PRODUCT_TYPE_ID);
        query.append("WHERE product_type_id IN (");
        query.append("?,".repeat(productTypeId.size()));
        switch (sortBy) {
            case -1: {
                query = new StringBuilder(query.substring(0, query.length() - 1) + ")\nORDER BY price DESC");
                break;
            }
            case 1: {
                query = new StringBuilder(query.substring(0, query.length() - 1) + ")\nORDER BY price ASC");
                break;
            }
            default:
                break;
        }
        query.append("\nLIMIT ?, ?;");
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            List<Product> products = new ArrayList<>();
            int i;
            for (i = 0; i < productTypeId.size(); i++) {
                statement.setInt(i + 1, productTypeId.get(i));
            }
            statement.setInt(i + 1, offset);
            statement.setInt(i + 2, recordsPerPage);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = buildProduct(resultSet);
                    products.add(product);
                }

                return products;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find products", e);
        }
    }

    @Override
    public List<Product> findAll(int offset, int recordsPerPage) throws DaoException {
        String query = "SELECT * FROM products limit " + offset + ", " + recordsPerPage;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            List<Product> products = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = buildProduct(resultSet);
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find products", e);
        }
    }


    @Override
    public int getNumberOfRecords() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_NUMBER_OF_RECORDS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find number of products", e);
        }
    }

    @Override
    public List<Product> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS)) {
            List<Product> products = new ArrayList<>();
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
    public void deleteById(Integer id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete product by id", e);
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
            statement.setInt(6, product.getProductTypeId());
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
            statement.setInt(6, product.getProductTypeId());
            statement.setInt(7, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update product", e);
        }
        return oldProduct;
    }

    private Product buildProduct(ResultSet resultSet) throws SQLException {

        return new Product.ProductBuilder()
                .setId(resultSet.getInt(ID))
                .setName(resultSet.getString(NAME))
                .setPrice(resultSet.getDouble(PRICE))
                .setDescription(resultSet.getString(DESCRIPTION))
                .setWeight(resultSet.getInt(WEIGTH))
                .setImage(Base64Coder.encode(resultSet.getBlob(IMAGE).getBinaryStream()))
                .setProductTypeId(resultSet.getInt(PRODUCT_TYPE_ID))
                .createProduct();
    }


}