package by.training.сonfectionery.model.validator;

import by.training.сonfectionery.exception.ServiceException;

import java.util.Map;

public interface ProductValidator {

    boolean validateProductData(Map<String, String> productMap) throws ServiceException;

    boolean validateName(String name);

    boolean validatePrice(String price);

    boolean validateDescription(String description);

    boolean validateWeight(String weight);

    boolean validateProductTypeId(String productTypeId) throws ServiceException;

    boolean validateImage(String image);

}
