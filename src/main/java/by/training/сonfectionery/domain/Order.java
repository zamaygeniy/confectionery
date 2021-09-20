package by.training.сonfectionery.domain;

import java.time.LocalDate;

public class Order extends Entity {
    private LocalDate date;
    private int userId;
    private OrderStatus orderStatus;

    public Order() {

    }

    public Order(int id, LocalDate date, int userId, OrderStatus orderStatus) {
        super(id);
        this.setDate(date);
        this.setUserId(userId);
        this.setOrderStatus(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus(){
        return orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public enum OrderStatus {
        IN_PROCESS("in_process", 1),
        CANCELED("canceled", 2),
        DONE("done", 3);

        private String value;
        private int id;

        OrderStatus(String value, int id) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public int getId(){
            return id;
        }
    }

    public static class OrderBuilder{
        Order order;

        public OrderBuilder(){
            order = new Order();
        }

        public Order.OrderBuilder setId(int id) {
            order.setId(id);
            return this;
        }

        public Order.OrderBuilder setDate(LocalDate date) {
            order.setDate(date);
            return this;
        }

        public Order.OrderBuilder setUserId(int id) {
            order.setUserId(id);
            return this;
        }

        public Order.OrderBuilder setOrderStatus(OrderStatus status) {
            order.setOrderStatus(status);
            return this;
        }

        public Order createOrder() {
            return order;
        }
    }


}
