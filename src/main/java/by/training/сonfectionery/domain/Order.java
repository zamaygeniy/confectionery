package by.training.сonfectionery.domain;

import java.time.LocalDate;

public class Order extends Entity {
    private LocalDate date;
    private String phone;
    private int userId;
    private Status status;
    private double cost;

    public Order() {
    }

    public double getCost() {
        return cost;
    }

    public Status getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public enum Status {
        WAITING_FOR_CONFIRMATION("waiting_for_confirmation", 1),
        IN_PROCESS("in_process", 2),
        DONE("done", 3),
        CANCELLED("cancelled", 4);


        private String value;
        private int id;

        Status(String value, int id) {
            this.value = value;
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }
    }

    public static class OrderBuilder {
        Order order;

        public OrderBuilder() {
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

        public Order.OrderBuilder setStatus(Status status) {
            order.setStatus(status);
            return this;
        }

        public Order.OrderBuilder setPhone(String phone) {
            order.setPhone(phone);
            return this;
        }

        public Order.OrderBuilder setCost(double cost){
            order.setCost(cost);
            return this;
        }

        public Order createOrder() {
            return order;
        }
    }


}
