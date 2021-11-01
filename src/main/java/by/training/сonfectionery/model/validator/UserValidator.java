package by.training.сonfectionery.model.validator;

import java.util.Map;

public interface UserValidator {

    boolean validateUserData(Map<String, String> userMap);

    boolean validateFirstName(String firstName);

    boolean validateLastName(String lastName);

    boolean validateEmail(String email);

    boolean validatePassword(String password);

}