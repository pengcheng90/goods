package cn.xie.goods.order.web.servlet;

import cn.itcast.servlet.BaseServlet;
import cn.xie.goods.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by baobiao on 2017/3/21.
 */
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    public String order(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
