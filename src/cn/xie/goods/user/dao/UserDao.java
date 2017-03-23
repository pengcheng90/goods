package cn.xie.goods.user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import cn.xie.goods.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Objects;

/**
 * 用户模块持久层
 * Created by baobiao on 2017/2/24.
 */
public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    /*
    *校验用户名是否注册
    * */
    public boolean ajaxValidateLoginname(String loginname) throws SQLException {

        String sql = "select count(1) from t_user where loginname=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
        return number.intValue() == 0;
    }
    /*
    *校验邮箱是否注册
    * */

    public boolean ajaxValidateEmail(String email) throws SQLException {
        String sql = "select count(1) from t_user where email=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), email);
        return number.intValue() == 0;
    }

    /*
    * 添加用户
    * */
    public void add(User user) throws SQLException {
        String sql = "insert into t_user values(?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(), user.getEmail(), user.isStatus(), user.getActivationCode()};
        qr.update(sql, params);
    }

    /*
    * 修改用户状态
    * */
    public void updateStatus(String uid, int status) throws SQLException {
        String sql = "update t_user set status = ? where uid = ?";
        qr.update(sql, status, uid);
    }

    /*
    * 通过激活码查询用户
    * */
    public User findByCode(String code) throws SQLException {
        String sql = "select from t_user  where uid = ?";
        return qr.query(sql, new BeanHandler<User>(User.class), code);
    }

    /*
    * 通过名称和密码查询用户
    * */
    public User findByNameAndPass(String loginname, String loginpass) throws SQLException {
        String sql = "select * from t_user where loginname=? and loginpass=?";
        User user = qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
        return user;
    }

    /*
      * 修改密码
      * */
    public void updatePass(String loginname, String newpass) throws SQLException {
        String sql = "update  t_user set loginpass=? where loginname=?";
        qr.update(sql, newpass, loginname);
    }
}
