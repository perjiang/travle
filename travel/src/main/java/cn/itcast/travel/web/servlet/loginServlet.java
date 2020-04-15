package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        String code = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode =(String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode == null || checkcode.equalsIgnoreCase(code)==false){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //json发送给前端(设置context-type)
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }else {
            //获取数据并且封装为user对象
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            UserService service = new UserServiceImpl();
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
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置响应格式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
