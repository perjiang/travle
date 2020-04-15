package cn.itcast.travel.web.servlet;
/**
 * 完成账号的激活功能
 */

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class activeUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){
            //调用方法进行激活
            UserService service = new UserServiceImpl();
            Boolean flag = service.active(code);
            String mess = null;
            if(flag){
                mess = "注册成功,请<a href='login.html'>登录</a>";
            }else {
                mess ="注册失败请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(mess);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
