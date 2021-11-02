package by.training.сonfectionery.model.service;

import by.training.сonfectionery.model.domain.ProductType;
import by.training.сonfectionery.exception.ServiceException;

import java.util.List;

public interface ProductTypeService {
    List<ProductType> findAllProductTypes() throws ServiceException;
}
