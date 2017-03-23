package cn.xie.goods.cart.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xie.goods.book.domain.Book;
import cn.xie.goods.cart.domain.CartItem;
import cn.xie.goods.page.Expression;
import cn.xie.goods.page.PageBean;
import cn.xie.goods.page.PageConstants;
import cn.xie.goods.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 购物车实体层
 * Created by baobiao on 2017/3/11.
 */
public class CartItemDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 生成where子句
     *
     * @param len
     * @return
     */
    public String toWhereSql(int len) {
        StringBuilder stringBuilder = new StringBuilder(" cartItemId in (");
        for (int i = 0; i < len; i++) {
            stringBuilder.append(" ? ");
            if (i < len - 1) {
                stringBuilder.append(" , ");
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /**
     * 批量删除购物车
     *
     * @param cartItemIds
     * @throws SQLException
     */
    public void batchDelete(String cartItemIds) throws SQLException {
        /*
        *
        * 把cartitemIds转换成where子句
        * 1.把cartitemid转换成where子句
        * 2.与deletefrom连接在一起
        * */
        Object[] cartItemIdArray = cartItemIds.split(",");
        String sql = toWhereSql(cartItemIdArray.length);
        qr.update(" delete from t_cartitem where " + sql, cartItemIdArray);
    }

    /**
     * 通过Bid和Uid查询购物车
     *
     * @param Bid
     * @param Uid
     * @return
     * @throws SQLException
     */
    public CartItem findBidAndUid(String Bid, String Uid) throws SQLException {
        String sql = " select * from t_cartitem where bid=? and uid=?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), Bid, Uid);
        CartItem cartItem = toCartItem(map);
        return cartItem;
    }

    /**
     * 更改购物车数量
     *
     * @param cartItemId
     * @param quantity
     * @throws SQLException
     */
    public void updateQuantity(String cartItemId, int quantity) throws SQLException {
        String sql = " update t_cartitem set quantity=? where cartItemId=? ";
        qr.update(sql, quantity, cartItemId);
    }

    /**
     * 通过id查询CartItem
     *
     * @param cartItemId
     * @return
     * @throws SQLException
     */
    public CartItem findByCartItemId(String cartItemId) throws SQLException {
        String sql = " select * from t_cartitem c,t_book b" +
                " where c.bid=b.bid and cartItemId = ? ";
        Map<String, Object> map = qr.query(sql, new MapHandler(), cartItemId);
        return toCartItem(map);
    }

    public void add(CartItem cartItem) throws SQLException {
        String sql = " insert into t_cartitem (cartItemId,quantity,bid,uid) " +
                "values(?,?,?,?) ";
        qr.update(sql, cartItem.getCartItemId(), cartItem.getQuantity(),
                cartItem.getBook().getBid(), cartItem.getUser().getUid());
    }

    /**
     * 通过用户查询购物车条目(条目指书和数量的组合)
     *
     * @param uid
     * @return
     */
    public List<CartItem> findByUser(String uid) throws SQLException {
        String sql = " select * from t_cartitem c,t_book b where" +
                " c.bid=b.bid and uid=? order by c.orderBy  ";//多表查询时order by的列前要加上表名
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), uid);
        return toCartItemList(mapList);
    }

    /**
     * 将map转换成CartItem
     *
     * @param map
     * @return
     */
    private CartItem toCartItem(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        User user = CommonUtils.toBean(map, User.class);
        cartItem.setBook(book);
        cartItem.setUser(user);
        return cartItem;
    }

    /**
     * 转换成List<CartItem>
     *
     * @param mapList
     * @return
     */
    private List<CartItem> toCartItemList(List<Map<String, Object>> mapList) {
        List<CartItem> cartItemList = new ArrayList<CartItem>();
        for (Map<String, Object> map : mapList) {
            cartItemList.add(toCartItem(map));
        }
        return cartItemList;
    }

    /**
     * 加载多条item
     *
     * @param cartItemIds
     * @return
     * @throws SQLException
     */
    public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
        Object[] cartItems = cartItemIds.split(",");
        String whereSql = toWhereSql(cartItems.length);
        String sql = " select * from t_cartitem c,t_book b where c.bid=b.bid and " + whereSql;
        List<CartItem> cartItemList = toCartItemList(qr.query(sql, new MapListHandler(), cartItems));
        return cartItemList;
    }
}
