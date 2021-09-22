package by.training.сonfectionery.control;

import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.User;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.OrderDao;
import by.training.сonfectionery.model.dao.UserDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.OrderDaoImpl;
import by.training.сonfectionery.model.dao.impl.UserDaoImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException {
        EntityTransaction transaction = new EntityTransaction();
        List<Order> orderList = new LinkedList<>();
        OrderDao orderDao = new OrderDaoImpl();
        try {
            transaction.initTransaction(orderDao);
            orderList = orderDao.findAll();
            System.out.println(orderList.get(0).getDate());
            orderDao.updateOrderStatus(orderList.get(0), Order.Status.CANCELED);
        } finally {
            transaction.endTransaction();
        }


    }
}
