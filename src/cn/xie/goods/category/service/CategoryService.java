package cn.xie.goods.category.service;

import cn.xie.goods.category.dao.CategoryDao;
import cn.xie.goods.category.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类模块业务层
 * Created by baobiao on 2017/3/9.
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    /**
     * 查询所有分类
     * @return
     */
    public List<Category> findAll() {
        try {
            return categoryDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
