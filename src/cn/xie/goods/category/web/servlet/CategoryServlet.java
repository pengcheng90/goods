package cn.xie.goods.category.web.servlet;

import cn.itcast.servlet.BaseServlet;
import cn.xie.goods.category.domain.Category;
import cn.xie.goods.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 分类模块web层
 * Created by baobiao on 2017/3/9.
 */
public class CategoryServlet extends BaseServlet {
    //依赖service层
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("parents", categoryList);
        return "f:/jsps/left.jsp";
    }
}
