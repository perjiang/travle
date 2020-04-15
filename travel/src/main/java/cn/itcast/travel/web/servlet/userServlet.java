package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
@WebServlet("/user/*")
public class userServlet extends BaseServlet {
    //申明一个私有的userService
    private UserService service = new UserServiceImpl();

    /**
     * 注册方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        String code = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode == null || checkcode.equalsIgnoreCase(code) == false) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
            //将info对象序列化为json
            String json = writeValueAsString(response, info);
            System.out.println(json);
            response.getWriter().write(json);
            return;
        } else {
            //获取数据
            Map<String, String[]> map = request.getParameterMap();
            //封装对象
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用注册方法
            Boolean flag = service.regist(user);
            //封装返回给前端的数据
            ResultInfo info = new ResultInfo();
            //返回数据
            if (flag) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("注册失败,改用户已经存在");
            }
            //将info对象序列化为json
            String json = writeValueAsString(response, info);
            response.getWriter().write(json);
        }
    }

    /**
     * 登录方法
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取验证码
        String code = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode == null || checkcode.equalsIgnoreCase(code) == false) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
            //将info对象序列化为json
            String json = writeValueAsString(response, info);
            response.getWriter().write(json);
            return;
        } else {
            //获取数据并且封装为user对象
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用方法获取对象
            User u = service.login(user);
            request.getSession().setAttribute("user", u);
            ResultInfo info = new ResultInfo();
            //如果查询出来的对象不存在，返回false
            if (u == null) {
                info.setFlag(false);
                info.setErrorMsg("用户名密码错误");
                //如果查询出来的对象存在，但是没有激活
            }
            if (u != null && !u.getStatus().equals("Y")) {
                info.setFlag(false);
                info.setErrorMsg("改账号未激活，请去激活");
                //如果查询出来的对象存在，并且激活了
            }
            if (u != null && u.getStatus().equals("Y")) {
                info.setFlag(true);
            }
            //响应数据
            String json = writeValueAsString(response,info);
            response.getWriter().write(json);
        }
    }

    /**
     * 查找当前登录的用户名在header显示
     * @param request
     * @param response
     * @throws IOException
     */
    public void find(HttpServletRequest request,HttpServletResponse response) throws IOException {
        User user =(User) request.getSession().getAttribute("user");
        response.setContentType("application/json;charset=utf-8");
       writeValue(response,user);
    }

    /**
     * 激活方法
     * @param request
     * @param response
     * @throws IOException
     */
    public void active(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        if(code!=null){
            //调用方法进行激活
            Boolean flag = service.active(code);
            String mess = null;
            if(flag){
                mess = "注册成功,请<a href='"+request.getContextPath()+"/login.html'>登录</a>";
            }else {
                mess ="注册失败请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(mess);
        }
    }

    /**
     * 退出方法
     */
    public void exit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //销毁seesion
        request.getSession().invalidate();
        //重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");

    }
}
