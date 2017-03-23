package cn.xie.goods.cart.service;

import cn.itcast.commons.CommonUtils;
import cn.xie.goods.cart.dao.CartItemDao;
import cn.xie.goods.cart.domain.CartItem;

import java.sql.SQLException;
import java.util.List;

/**
 * 购物车业务层
 * Created by baobiao on 2017/3/11.
 */
public class CartItemService {
    private CartItemDao cartItemDao = new CartItemDao();

    /**
     * 我的购物车功能
     *
     * @param uid
     * @return
     */
    public List<CartItem> myCart(String uid) {
        try {
            return cartItemDao.findByUser(uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加购物车
     *
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        try {
            CartItem cart = cartItemDao.findBidAndUid(cartItem.getBook().getBid(),
                    cartItem.getUser().getUid());
            if (cart == null) {
                cartItem.setCartItemId(CommonUtils.uuid());
                cartItemDao.add(cartItem);
            } else {
                int quantity = cartItem.getQuantity() + cart.getQuantity();
                cartItemDao.updateQuantity(cart.getCartItemId(), quantity);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     */
    public void batchDelete(String cartItemId) {
        try {
            cartItemDao.batchDelete(cartItemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改订单的数量
     *
     * @param cartItemId
     * @param quantity
     * @return
     */
    public CartItem updataQuantity(String cartItemId, int quantity) {
        try {
            cartItemDao.updateQuantity(cartItemId, quantity);
            return cartItemDao.findByCartItemId(cartItemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载多条item
     *
     * @param cartItemIds
     * @return
     */
    public List<CartItem> loadCartItems(String cartItemIds) {
        try {
            return cartItemDao.loadCartItems(cartItemIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
