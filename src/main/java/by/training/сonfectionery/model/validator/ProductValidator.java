package by.training.сonfectionery.model.validator;

import java.util.Map;

public interface ProductValidator {

    boolean validateProductData(Map<String, String> productMap);

    boolean validateName(String name);

    boolean validatePrice(String price);

    boolean validateDescription(String description);

    boolean validateWeight(String weight);

    boolean validateProductTypeId(String productTypeId);

}
