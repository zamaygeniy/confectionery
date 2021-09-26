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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        EntityTransaction transaction = new EntityTransaction();
        ProductDao productDao = new ProductDaoImpl();
        List<Product> products = new ArrayList<>();
        for(int i =0; i < 20; i ++){
            products.add(new Product.ProductBuilder()
                    .setName("1")
                    .setPrice(1.1)
                    .setDescription("1")
                    .setWeight(1)
                    .setImage("fdkidskfoishiJKkfdj")
                    .setNumberInStock(1)
                    .setProductTypeId(3)
                    .createProduct());
        }
        try{
            transaction.init(productDao);
            for(int i = 0; i < 20; i++){
                productDao.create(products.get(i));
            }
        } finally {
            transaction.end();
        }
//        List<Order> orderList = new LinkedList<>();
//        Order order = new Order();
//        order.setPhone("0000");
//        LocalDate date = LocalDate.now();
//        order.setDate(date);
//        order.setUserId(1);
//        order.setStatus(Order.Status.IN_PROCESS);
//        order.setId(2);
//        order.getDate();
//        OrderDao orderDao = new OrderDaoImpl();
//        try {
//            transaction.initTransaction(orderDao);
//            orderDao.create(order);
//            orderList = orderDao.findAll();
//            System.out.println(orderList.get(1).getDate());
//            orderDao.updateOrderStatus(orderList.get(0), Order.Status.CANCELED);
//        } finally {
//            transaction.endTransaction();
//        }


    }
}
