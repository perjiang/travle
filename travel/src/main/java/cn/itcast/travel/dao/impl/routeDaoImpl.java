package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.routeDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class routeDaoImpl implements routeDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {
        String sql = "select COUNT(*) from tab_route where 1= 1  ";
        List param = new ArrayList();
        StringBuilder sb = new StringBuilder(sql);
        if(cid != 0){
           sb.append(" and cid = ?");
           param.add(cid);
        }if(rname!=null && rname.length()>0){
            sb.append(" and rname like ?");
            param.add("%"+rname+"%");
        }
        sql = sb.toString();
       return template.queryForObject(sql,Integer.class,param.toArray());
    }

    @Override
    public List<Route> findCurrent(int cid, int start, int pageSize,String rname) {
        String sql ="select * from tab_route where 1=1 ";
        List param = new ArrayList();
        StringBuilder sb = new StringBuilder(sql);
        if(cid != 0){
            sb.append(" and cid = ?");
            param.add(cid);
        }if(rname!=null && rname.length()>0){
            sb.append(" and rname like ?");
            param.add("%"+rname+"%");
        }
        sb.append(" limit ?,?");
        sql = sb.toString();
        param.add(start);
        param.add(pageSize);
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),param.toArray());
    }

    /**
     * 根据rid获取一个route
     * @param rid
     * @return
     */
    @Override
    public Route findOneByRid(int rid) {
        String sql ="select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
