package cn.xie.goods.book.service;

import cn.xie.goods.book.dao.BookDao;
import cn.xie.goods.book.domain.Book;
import cn.xie.goods.page.Expression;
import cn.xie.goods.page.PageBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图书业务层
 * Created by baobiao on 2017/3/10.
 */
public class BookService {
    private BookDao bookDao = new BookDao();

    /**
     * 通过分类查询业务
     *
     * @param cid
     * @param pc
     * @return
     */
    public PageBean<Book> findByCategory(String cid, int pc) {
        try {
            return bookDao.findByCategory(cid, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过作者查询
     *
     * @param author
     * @param pc
     * @return
     */

    public PageBean<Book> findByAuthor(String author, int pc) {
        try {
            return bookDao.findByAuthor(author, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过出版社查询
     *
     * @param press
     * @param pc
     * @return
     */
    public PageBean<Book> findByPress(String press, int pc) {
        try {
            return bookDao.findByPress(press, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过书名查询
     *
     * @param bname
     * @param pc
     * @return
     */
    public PageBean<Book> findByBname(String bname, int pc) {
        try {
            return bookDao.findByBname(bname, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 多条件组合查询
     *
     * @param criteria
     * @param pc
     * @return
     */
    public PageBean<Book> findByCombination(Book criteria, int pc) {
        try {
            return bookDao.findByCombination(criteria, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过Bid查询
     *
     * @param bid
     * @param pc
     * @return
     */
    public Book findByBid(String bid, int pc) {
        try {
            return bookDao.findByBid(bid, pc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
