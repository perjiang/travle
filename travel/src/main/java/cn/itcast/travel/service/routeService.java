package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.pageBean;

import java.util.List;

/**
 * 路线service
 */
public interface routeService {
    /**
     * 根据类别分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public pageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    public  Route findOne(String rid);
}
