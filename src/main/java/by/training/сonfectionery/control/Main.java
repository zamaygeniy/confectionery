package by.training.сonfectionery.control;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.OrderDao;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.UserDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.OrderDaoImpl;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import by.training.сonfectionery.model.dao.impl.UserDaoImpl;
import by.training.сonfectionery.util.Base64Coder;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {

        /*EntityTransaction transaction = new EntityTransaction();
        ProductDao productDao = new ProductDaoImpl();
        List<Product> products = new ArrayList<>();

        String picture = null;
        try (FileInputStream fileInputStream = new FileInputStream("D:\\web\\Confectionery\\src\\main\\webapp\\img\\user.png")) {
            picture = Base64Coder.encode(fileInputStream);
            for (int i = 0; i < 20; i++) {
                products.add(new Product.ProductBuilder()
                        .setName("1")
                        .setPrice(2)
                        .setDescription("2")
                        .setWeight(2)
                        .setImage(picture)
                        .setProductTypeId(2)
                        .createProduct());
            }
            try {
                transaction.init(productDao);
                for (int i = 0; i < 20; i++) {
                    productDao.create(products.get(i));
                }
            } finally {
                transaction.end();
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }*/
        UserDao userDao = new UserDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        try {
            transaction.init(userDao);
            List<User> list = userDao.findAll();
            for(int i = 0; i < list.size();i++)
                System.out.println(list.get(i).toString());
        } finally {
            transaction.end();
        }

    }
}
