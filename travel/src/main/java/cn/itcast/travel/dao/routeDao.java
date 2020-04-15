package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface routeDao {
    /**
     * 根据cid查询改类型的所有记录
     */
    public int findTotalCount(int cid,String rname);
    /*
     * 根据cid start pagesize 查询当前页需要显示的数据集合
     */
    public List<Route> findCurrent(int cid,int start,int pageSize,String rname);

    Route findOneByRid(int rid);
}
