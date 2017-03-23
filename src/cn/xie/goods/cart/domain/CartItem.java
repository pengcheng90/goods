package cn.xie.goods.cart.domain;

import cn.xie.goods.book.domain.Book;
import cn.xie.goods.user.domain.User;

import java.math.BigDecimal;

/**
 * 购物车实体类
 * Created by baobiao on 2017/3/11.
 */
public class CartItem {
    private String cartItemId;//主键
    private int quantity;//数量
    private Book book;//条目对应的图书
    private User user;//所属用户

    //添加小计方法
    public double getSubtotal() {
        /**
         * 使用BigDecimal不会有误差
         * 要求必须使用String类型构造器
         */
        BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
        BigDecimal b2 = new BigDecimal(quantity + "");
        BigDecimal b3 = b1.multiply(b2);
        return b3.doubleValue();
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
