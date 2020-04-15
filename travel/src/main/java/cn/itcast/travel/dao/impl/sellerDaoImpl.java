package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.sellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class sellerDaoImpl implements sellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据sid查询买家信息
     * @paramsid
     * @return
     */
    @Override
    public Seller find(int sid) {
        String sql = "SELECT * from tab_seller where sid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
