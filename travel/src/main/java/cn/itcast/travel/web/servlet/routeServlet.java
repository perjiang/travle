package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.impl.favoriteServiceImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.domain.pageBean;
import cn.itcast.travel.service.favoriteService;
import cn.itcast.travel.service.impl.routeServiceImpl;
import cn.itcast.travel.service.routeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class routeServlet extends BaseServlet {
    /**
     * 分页查询
     * @param request
     * @param response
     */
    private routeService service = new routeServiceImpl();
    favoriteService favorite = new favoriteServiceImpl();
    public void pageQuery(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //接收前端传递的参数(全是字符串)
        String currentPageStr = request.getParameter("currentPage");//当前页
        String pageSizeStr = request.getParameter("pageSize");//每页显示条数
        String cidStr = request.getParameter("cid");//分类id
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //对字符串处理为int
        int currentPage = 0;
        int pageSize = 0;
        int cid = 0;
        if (currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        if (pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        if(cidStr != null && cidStr.length()>0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //调用方法
        pageBean<Route> bean = service.pageQuery(cid, currentPage, pageSize,rname);
        //封装数据
        writeValue(response,bean);
    }
    /**
     * 查询某一条线路的详情
     */
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取参数
        String rid = request.getParameter("rid");
        //调用方法
        Route route = service.findOne(rid);
        //封装参数
        writeValue(response,route);

    }
    /**
     *
     * 查找改线路是否被收藏
     */
    public void findfavorite(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user == null){
            uid = 0;
        }else {
            uid = user.getUid();
        }
        Boolean flag = favorite.findFavoriteByRidAndUid((int) Integer.parseInt(rid), uid);
        writeValue(response,flag);
    }
    public void addFavorite(HttpServletRequest request,HttpServletResponse response){
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user == null){
            return;
        }else {
            uid = user.getUid();
        }
        //调用方法
        favorite.addFavorite((int) Integer.parseInt(rid), uid);
    }
}
