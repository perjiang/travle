package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
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

@WebServlet("/registerUserServlet")
public class registerUserServlet extends HttpServlet {
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
            UserServiceImpl service = new UserServiceImpl();
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
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //json发送给前端(设置context-type)
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
