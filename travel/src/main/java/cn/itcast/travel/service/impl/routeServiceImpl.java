package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.favoriteDao;
import cn.itcast.travel.dao.impl.favoriteDaoImpl;
import cn.itcast.travel.dao.impl.routeDaoImpl;
import cn.itcast.travel.dao.impl.routeImgDaoImpl;
import cn.itcast.travel.dao.impl.sellerDaoImpl;
import cn.itcast.travel.dao.routeDao;
import cn.itcast.travel.dao.routeImg;
import cn.itcast.travel.dao.sellerDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.domain.pageBean;
import cn.itcast.travel.service.routeService;

import java.util.List;

public class routeServiceImpl implements routeService {
    private routeDao dao = new routeDaoImpl();
    private routeImg img = new routeImgDaoImpl();
    private sellerDao seller = new sellerDaoImpl();
    private favoriteDao favorite = new favoriteDaoImpl();
    /**
     * 根据类别分页查询
     * @param cid 类别id
     * @param currentPage  当前页
     * @param pageSize     页面条数
     * @return
     */
    @Override
    public pageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        pageBean<Route> bean = new pageBean<Route>();
        bean.setCurrentPage(currentPage);//当前页码
        bean.setPageSize(pageSize);//每页条数
        int totalCount = dao.findTotalCount(cid,rname);
        bean.setTotalCount(totalCount);//总条数
        int start = (currentPage-1)*pageSize;//当夜开始显示的是第几条记录
        List<Route> list = dao.findCurrent(cid,start,pageSize,rname);
        bean.setList(list);//设置当页的记录集合
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize)+1;
        bean.setTotalPage(totalPage);
        return bean;
    }

    /**
     * 根据cid查询一个route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        Route route = null;
        //查询route
        route = dao.findOneByRid(Integer.parseInt(rid));
        //插叙改route对应的图片集合
        List<RouteImg> imgs = img.findImg(route.getRid());
        route.setRouteImgList(imgs);
        //查询买家信息
        Seller sell = seller.find(route.getSid());
        route.setSeller(sell);
        //查询收藏次数
        int count = favorite.findFavoriteCount(route.getRid());
        route.setCount(count);
        return route;
    }
}
