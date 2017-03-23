package cn.xie.goods.cart.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.xie.goods.book.domain.Book;
import cn.xie.goods.cart.dao.CartItemDao;
import cn.xie.goods.cart.domain.CartItem;
import cn.xie.goods.cart.service.CartItemService;
import cn.xie.goods.category.service.CategoryService;
import cn.xie.goods.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 购物车web层
 * Created by baobiao on 2017/3/11.
 */
public class CartItemServlet extends BaseServlet {
    private CartItemService cartItemService = new CartItemService();

    /**
     * 添加购物车模块
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map map = req.getParameterMap();
        CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        User user = (User) req.getSession().getAttribute("sessionUser");
        cartItem.setBook(book);
        cartItem.setUser(user);
        cartItemService.add(cartItem);
        return myCart(req, resp);
    }

    /**
     * 我的购物车
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        List<CartItem> cartItemList = cartItemService.myCart(uid);
        req.setAttribute("cartItemList", cartItemList);
        return "f:/jsps/cart/list.jsp";
    }

    /**
     * 批量删除
     */
    public String batchDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cartItemIds = req.getParameter("cartItemIds");
        cartItemService.batchDelete(cartItemIds);
        return myCart(req, resp);
    }

    /**
     * 修改订单数量
     *
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cartItemId = req.getParameter("cartItemId");
        int quantity = Integer.parseInt((String) req.getParameter("quantity"));
        CartItem cartItem = cartItemService.updataQuantity(cartItemId, quantity);
        // 给客户端返回一个json对象
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
        sb.append(",");
        sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
        sb.append("}");

        resp.getWriter().print(sb);
        return null;
    }

    /**
     * 加载多条item
     *
     * @param req
     * @param resp
     * @return
     */
    public String loadCartItems(HttpServletRequest req, HttpServletResponse resp) {
        String cartItemIds = req.getParameter("cartItemIds");
        List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
        req.setAttribute("cartItemList", cartItemList);
        return "f:/jsps/cart/showitem.jsp";
    }
}
