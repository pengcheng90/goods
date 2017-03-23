package cn.xie.goods.order.domain;

import cn.xie.goods.book.domain.Book;

/**
 * Created by baobiao on 2017/3/21.
 */
public class OrderItem {
    private String orderItemId;//主键
    private int quantity;//数量
    private double subtotal;//小计
    private Book book;//所关联的Book
    private Order order;//所属的订单

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId='" + orderItemId + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", book=" + book +
                ", order=" + order +
                '}';
    }
}
