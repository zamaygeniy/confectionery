package by.training.сonfectionery.control.command.impl;

import by.training.сonfectionery.control.command.Command;
import by.training.сonfectionery.control.command.PagePath;
import by.training.сonfectionery.control.command.Router;
import by.training.сonfectionery.domain.Order;
import by.training.сonfectionery.domain.Product;
import by.training.сonfectionery.exception.CommandException;
import by.training.сonfectionery.exception.DaoException;
import by.training.сonfectionery.model.dao.OrderDao;
import by.training.сonfectionery.model.dao.ProductDao;
import by.training.сonfectionery.model.dao.impl.EntityTransaction;
import by.training.сonfectionery.model.dao.impl.OrderDaoImpl;
import by.training.сonfectionery.model.dao.impl.ProductDaoImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

public class GetOrders implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        int page = 1;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        OrderDao orderDao = new OrderDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        EntityTransaction transaction = new EntityTransaction();
        int recordsPerPage = 12;
        try {
            transaction.initTransaction(orderDao, productDao);
            List<Order> orders = orderDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
            Map<Order, Map<Product, Integer>> ordersMap = new HashMap<>();
            for (Order order : orders) {
                Map<Integer, Integer> productsInOrder;
                productsInOrder = orderDao.findProductsInOrders(order.getId());
                Map<Product, Integer> productAmount = new HashMap<>();
                for (Map.Entry<Integer, Integer> product : productsInOrder.entrySet()) {
                    Optional<Product> optionalProduct = productDao.findById(product.getKey());
                    if (optionalProduct.isPresent()) {
                        productAmount.put(optionalProduct.get(), product.getValue());
                    } else {
                        throw new CommandException("There is no such product");
                    }
                }
                ordersMap.put(order, productAmount);
            }
            System.out.println(ordersMap);
            int noOfRecords = orderDao.getNumberOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("ordersMap", ordersMap);
            request.setAttribute("noOfOrdersPages", noOfPages);
            request.setAttribute("currentOrdersPage", page);
            return new Router(PagePath.ORDERS_PAGE, Router.RouteType.FORWARD);
        } catch (DaoException e) {
            throw new CommandException("Failed", e);
        } finally {
            try {
                transaction.end();
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
    }
}
