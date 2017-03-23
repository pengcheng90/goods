package cn.xie.goods.category.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xie.goods.category.domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分类的持久层
 * Created by baobiao on 2017/3/9.
 */
public class CategoryDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查询分类
     *
     * @return
     */
    public List<Category> findAll() throws SQLException {
        //1.查询一级分类
        String sql = " select * from t_category where pid is null";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
        List<Category> parents = toCategoryList(mapList);

        //2.查询二级分类
        for (Category parent : parents) {
            List<Category> children = findByAll(parent.getCid());
            parent.setChildren(children);
        }
        return parents;
    }

    private List<Category> findByAll(String cid) throws SQLException {
        String sql = " select * from t_category where pid=?";
        List<Map<String, Object>> categoryList = qr.query(sql, new MapListHandler(), cid);
        List<Category> categories = toCategoryList(categoryList);
        return categories;
    }

    private Category toCategory(Map<String, Object> map) {

        Category category = CommonUtils.toBean(map, Category.class);
        if (map.get("pid") != null) {
            Category parent = new Category();
            parent.setCid((String) map.get("pid"));
            category.setParent(parent);
        }
        return category;
    }

    private List<Category> toCategoryList(List<Map<String, Object>> mapList) {

        List<Category> categoryList = new ArrayList<Category>();
        for (Map<String, Object> map : mapList) {
            categoryList.add(toCategory(map));
        }
        return categoryList;
    }
}
