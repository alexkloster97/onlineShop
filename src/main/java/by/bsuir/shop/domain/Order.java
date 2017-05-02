package by.bsuir.shop.domain;

import java.util.Date;

/**
 * Good order
 */
public class Order {
    private Integer orderId;
    private Date date;
    private Boolean submited;
    private Boolean delivered;
    private Integer number;
    private User user;
    private Good good;

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSubmited() {
        return submited;
    }

    public void setSubmited(Boolean submited) {
        this.submited = submited;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        if (delivered != null ? !delivered.equals(order.delivered) : order.delivered != null) return false;
        if (good != null ? !good.equals(order.good) : order.good != null) return false;
        if (number != null ? !number.equals(order.number) : order.number != null) return false;
        if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
        if (submited != null ? !submited.equals(order.submited) : order.submited != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (submited != null ? submited.hashCode() : 0);
        result = 31 * result + (delivered != null ? delivered.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (good != null ? good.hashCode() : 0);
        return result;
    }
}
