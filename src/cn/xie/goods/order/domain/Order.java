package cn.xie.goods.order.domain;

import cn.xie.goods.user.domain.User;

/**
 * Created by baobiao on 2017/3/21.
 */
public class Order {
    private String oid;//主键
    private String ordertime;//下单时间
    private double total;//总计
    private int status;//订单状态：1，未付款，2，已付款但未发货，3，已发货未确认收获，
    // 4，确认收货了交易成功，5.已取消
    private String address;//收获地址
    private User user;//订单的所有者

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", total=" + total +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
