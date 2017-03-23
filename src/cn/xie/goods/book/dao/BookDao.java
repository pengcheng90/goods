package cn.xie.goods.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xie.goods.book.domain.Book;
import cn.xie.goods.category.domain.Category;
import cn.xie.goods.page.Expression;
import cn.xie.goods.page.PageBean;
import cn.xie.goods.page.PageConstants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图书持久层
 * Created by baobiao on 2017/3/10.
 */
public class BookDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 通过分类查询
     *
     * @param cid
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByCategory(String cid, int pc) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression(" cid ", " = ", cid));
        return findByCriteria(expressionList, pc);
    }

    /**
     * 通过书名查询(模糊查询)
     *
     * @param bname
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByBname(String bname, int pc) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression(" bname ", " like ",
                "%" + bname + "%"));
        return findByCriteria(expressionList, pc);
    }

    /**
     * 通用查询方法
     *
     * @param expressions
     * @return
     * @throws SQLException
     */
    private PageBean<Book> findByCriteria(List<Expression> expressions, int pc)
            throws SQLException {
        /*
         * 1. 得到ps
		 */
        int ps = PageConstants.PS;//每页记录数
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        for (Expression expression : expressions) {
            whereSql.append(" and " + expression.getName()).append(" " + expression.getOperator());
            if (expression.getOperator() != null) {
                whereSql.append(" ? ");
                params.add(expression.getValue());
            }
        }
       /*
         * 3. 总记录数
		 */
        String sql = "select count(*) from t_book" + whereSql;
        Number number = (Number) qr.query(sql, new ScalarHandler(), params.toArray());
        int tr = number.intValue();//得到了总记录数

        //查询图书
        sql = "select * from t_book" + whereSql + " order by orderBy limit ?,?";
        params.add((pc - 1) * ps);//当前页首行记录的下标
        params.add(ps);//一共查询几行，就是每页记录数

        List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class),
                params.toArray());

        /*
         * 5. 创建PageBean，设置参数
		 */
        PageBean<Book> pageBean = new PageBean<Book>();
        pageBean.setPs(PageConstants.PS);
        pageBean.setTr(tr);
        pageBean.setPc(pc);
        pageBean.setBeanList(beanList);
        return pageBean;
    }

    /**
     * 通过作者查询
     *
     * @param author
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByAuthor(String author, int pc) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression(" author ", " like ",
                "%" + author + "%"));
        return findByCriteria(expressionList, pc);
    }

    /**
     * 多条件组合查询
     *
     * @param criteria
     * @param pc
     * @return
     */
    public PageBean<Book> findByCombination(Book criteria, int pc) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression(" bname ", " like ",
                "%" + criteria.getBname() + "%"));
        expressionList.add(new Expression(" author ", " like ",
                "%" + criteria.getAuthor() + "%"));
        expressionList.add(new Expression(" press ", " like ",
                "%" + criteria.getPress() + "%"));
        return findByCriteria(expressionList, pc);
    }

    /**
     * 通过出版社查询
     *
     * @param press
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByPress(String press, int pc) throws SQLException {
        List<Expression> expressionList = new ArrayList<Expression>();
        expressionList.add(new Expression(" press ", " like ",
                "%" + press + "%"));
        return findByCriteria(expressionList, pc);
    }

    /**
     * 通过Bid查询
     *
     * @param bid
     * @param pc
     * @return
     * @throws SQLException
     */
    public Book findByBid(String bid, int pc) throws SQLException {
        String sql = " select * from t_book where bid=? ";
        //包含了很多的属性，还有一个cid属性
        Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
        //把map中除了cid以外的其他属性映射到Book对象中
        Book book = CommonUtils.toBean(map, Book.class);
        //把Map中的cid映射到Category中，这个Category中只有cid这个属性
        Category category = CommonUtils.toBean(map, Category.class);
        //两者建立关系
        book.setCategory(category);
        return book;
    }
}
