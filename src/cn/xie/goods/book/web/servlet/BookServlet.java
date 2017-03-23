package cn.xie.goods.book.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.xie.goods.book.domain.Book;
import cn.xie.goods.book.service.BookService;
import cn.xie.goods.page.PageBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图书web层
 * Created by baobiao on 2017/3/10.
 */
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();


    /**
     * 通过分类查询
     *
     * @return
     */
    public String findByCategory(HttpServletRequest req, HttpServletResponse resp) {

         /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
        int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
        String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
        String cid = req.getParameter("cid");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
        PageBean<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/book/list.jsp";
    }

    /**
     * 通过作者查询
     *
     * @return
     */
    public String findByAuthor(HttpServletRequest req, HttpServletResponse resp) {

         /*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
        int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
        String url = getUrl(req);

        /*
		 * 3. 获取查询条件，author
		 */
        String author = req.getParameter("author");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
        PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/book/list.jsp";
    }

    /**
     * 按出版社查询
     *
     * @return
     */
    public String findByPress(HttpServletRequest req, HttpServletResponse resp) {

         /*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
        int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
        String url = getUrl(req);
		/*
		 * 3. 获取查询条件，press
		 */
        String press = req.getParameter("press");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
        PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/book/list.jsp";
    }

    /**
     * 按书名查询
     *
     * @return
     */
    public String findByBname(HttpServletRequest req, HttpServletResponse resp) {

         /*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
        int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
        String url = getUrl(req);
		/*
		 * 3. 获取查询条件，bname
		 */
        String bname = req.getParameter("bname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
        PageBean<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/book/list.jsp";
    }
    /**
     * 按Bid查询
     *
     * @return
     */
    public String findByBid(HttpServletRequest req, HttpServletResponse resp) {
        int pc = getPc(req);
        String bid = req.getParameter("bid");
        Book book = bookService.findByBid(bid, pc);
        req.setAttribute("book", book);
        return "f:/jsps/book/desc.jsp";
    }
    /**
     * 多条件查询
     *
     * @return
     */
    public String findByCombination(HttpServletRequest req, HttpServletResponse resp) {

         /*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
        int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
        String url = getUrl(req);
		/*
		 * 3. 获取查询条件
		 */
        Book criteria= CommonUtils.toBean(req.getParameterMap(),Book.class);
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
        PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/book/list.jsp";
    }

    /**
     * 获取当前页码
     *
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
        int pc = 1;
        String param = req.getParameter("pc");
        if (param != null && !param.trim().isEmpty()) {
            try {
                pc = Integer.parseInt(param);
            } catch (RuntimeException e) {
            }
        }
        return pc;
    }

    /**
     * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
     *
     * @param req
     * @return
     */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
        int index = url.lastIndexOf("&pc=");
        if (index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }
}
