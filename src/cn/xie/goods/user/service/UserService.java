package cn.xie.goods.user.service;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.xie.goods.user.service.exception.UserException;
import cn.xie.goods.user.dao.UserDao;
import cn.xie.goods.user.domain.User;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 用户模块业务层
 * Created by baobiao on 2017/2/24.
 */
public class UserService {
    private UserDao userDao = new UserDao();

    /*
    * 用户名注册校验
    * */
    public boolean ajaxValidateLoginname(String loginname) {
        try {
            return userDao.ajaxValidateLoginname(loginname);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Email注册校验
    * */
    public boolean ajaxValidateEmail(String email) {
        try {
            return userDao.ajaxValidateEmail(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * 注册
    * */
    public void regist(User user) {
        user.setUid(CommonUtils.uuid());
        user.setStatus(false);
        user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
        try {
            userDao.add(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            /*
         * 3. 发邮件
		 */
        /*
         * 把配置文件内容加载到prop中
		 */
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

		/*
         * 登录邮件服务器，得到session
		 */
        String host = prop.getProperty("host");//服务器主机名
        String name = prop.getProperty("username");//登录名
        String pass = prop.getProperty("password");//登录密码
        Session session = MailUtils.createSession(host, name, pass);

		/*
         * 创建Mail对象
		 */
        String from = prop.getProperty("from");
        String to = user.getEmail();
        String subject = prop.getProperty("subject");
        // MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换。
        // 例如MessageFormat.format("你好{0}, 你{1}!", "张三", "去死吧"); 返回“你好张三，你去死吧！”
        String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
        Mail mail = new Mail(from, to, subject, content);
        /*
         * 发送邮件
		 */
        try {
            MailUtils.send(session, mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 激活功能
     *
     * @param code
     * @throws UserException
     */
    public void activatioin(String code) throws UserException {
        try {
            User user = userDao.findByCode(code);
            if (user == null) {//用户未激活，实现激活
                throw new UserException("无效的激活码！");
            } else if (user.isStatus()) {
                throw new UserException("您已经激活了，不要二次激活！");
            } else {
                userDao.updateStatus(user.getUid(), 1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登录
     *
     * @param formUser
     * @return
     */
    public User login(User formUser) {
        User user = null;
        try {
            user = userDao.findByNameAndPass(formUser.getLoginname(), formUser.getLoginpass());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void updatePass(User user) throws UserException {
        User findUser = null;
        try {
            findUser = userDao.findByNameAndPass(user.getLoginname(), user.getLoginpass());
            userDao.updatePass(user.getLoginname(), user.getNewloginpass());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过用户名和密码查找用户
     *
     * @param user
     * @return
     */
    public User findByNameAndPass(User user) {
        try {
            return userDao.findByNameAndPass(user.getLoginname(), user.getLoginpass());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
