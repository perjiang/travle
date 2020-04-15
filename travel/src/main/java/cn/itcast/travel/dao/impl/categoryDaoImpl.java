package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.categoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class categoryDaoImpl implements categoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查询所有的旅游项目
     * @return 所有项目的 list集合
     */
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return  template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));

    }
}
