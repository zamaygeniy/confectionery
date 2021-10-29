package by.training.сonfectionery.model.validator.impl;

import by.training.сonfectionery.domain.ProductType;
import by.training.сonfectionery.exception.ServiceException;
import by.training.сonfectionery.model.service.ProductTypeService;
import by.training.сonfectionery.model.service.impl.ProductTypeServiceImpl;
import by.training.сonfectionery.model.validator.ProductValidator;

import java.util.List;
import java.util.Map;

import static by.training.сonfectionery.control.command.RequestParameter.*;
import static by.training.сonfectionery.control.command.RequestParameter.PASSWORD;

public class ProductValidatorImpl implements ProductValidator {
    private static ProductValidatorImpl instance = new ProductValidatorImpl();

    private static final String REGEX_NAME = "(?=^.{1,32}$)^([A-zА-яЁё`'.-])+$";
    private static final String REGEX_WEIGHT = "^\\d{1,5}$";
    private static final String REGEX_PRICE = "^\\d*\\.{0,1}\\d+|\\d+\\.{0,1}\\d*$";
    private static final String EMPTY_LINE = "";

    private ProductValidatorImpl() {
    }

    public static ProductValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateProductData(Map<String, String> productMap) throws ServiceException {
        boolean result = true;
        if (!validateName(productMap.get(NAME))) {
            productMap.put(NAME, EMPTY_LINE);
            result = false;
        }
        if (!validatePrice(productMap.get(PRICE))) {
            productMap.put(PRICE, EMPTY_LINE);
            result = false;
        }
        if (!validateDescription(productMap.get(DESCRIPTION))) {
            productMap.put(DESCRIPTION, EMPTY_LINE);
            result = false;
        }
        if (!validateWeight(productMap.get(WEIGHT))) {
            productMap.put(WEIGHT, EMPTY_LINE);
            result = false;
        }
        if (!validateProductTypeId(productMap.get(PRODUCT_TYPE_ID))) {
            productMap.put(PRODUCT_TYPE_ID, EMPTY_LINE);
            result = false;
        }
        if (!validateImage(productMap.get(IMAGE))) {
            productMap.put(IMAGE, EMPTY_LINE);
            result = false;
        }
        return result;
    }

    @Override
    public boolean validateImage(String image){
        return image != null;
    }

    @Override
    public boolean validateName(String name) {
        return name.matches(REGEX_NAME);
    }

    @Override
    public boolean validatePrice(String price) {
        return price.matches(REGEX_PRICE);
    }

    @Override
    public boolean validateDescription(String description) {
        return true;
    }

    @Override
    public boolean validateWeight(String weight) {
        return weight.matches(REGEX_WEIGHT);
    }

    @Override
    public boolean validateProductTypeId(String productTypeId) throws ServiceException {
        ProductTypeService productTypeService = ProductTypeServiceImpl.getInstance();
        List<ProductType> productTypes = productTypeService.findAllProductTypes();
        for (ProductType productType : productTypes) {
            if (Integer.parseInt(productTypeId) == productType.getId()) {
                return true;
            }
        }
        return false;
    }
}
