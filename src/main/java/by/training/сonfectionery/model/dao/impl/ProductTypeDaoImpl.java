package by.training.сonfectionery.model.dao.impl;

import by.training.сonfectionery.domain.ProductType;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.ProductTypeDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.сonfectionery.model.dao.ColumnName.ID;
import static by.training.сonfectionery.model.dao.ColumnName.TYPE;

public class ProductTypeDaoImpl extends ProductTypeDao {

    private static final String SQL_FIND_ALL_PRODUCT_TYPES = """
            SELECT id, type
            FROM product_type
            """;


    @Override
    public List<ProductType> findAll() throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_PRODUCT_TYPES)) {
            List<ProductType> productTypes = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductType productType = buildProductType(resultSet);
                    productTypes.add(productType);
                }
                return productTypes;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all product types", e);
        }
    }

    @Override
    public Optional<ProductType> findById(Integer id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) throws DaoException {
    }

    @Override
    public boolean create(ProductType productType) throws DaoException {
        return false;
    }

    @Override
    public ProductType update(ProductType productType) throws DaoException {
        return null;
    }

    private ProductType buildProductType(ResultSet resultSet) throws SQLException {

        return new ProductType.ProductTypeBuilder()
                .setId(resultSet.getInt(ID))
                .setType(resultSet.getString(TYPE))
                .createProductType();
    }

}
