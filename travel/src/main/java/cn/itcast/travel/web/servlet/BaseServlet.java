package cn.itcast.travel.web.servlet;
/**
 * 完成方法的分发
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    //申明一个ObjectMapper
    ObjectMapper mapper = new ObjectMapper();
    //对方法的分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的路径
        String uri = req.getRequestURI();
        //获取请求的方法名称
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        //System.out.println(methodName);
        try {
            //获取方法对象Method(反射的方式)这里的this是指调用这个方法的类并不是BaseServlet
            //忽略访问权限修饰符
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //暴力反射
            //method.setAccessible(true);
            //执行方法
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /**
     * 将对象序列化为json并且放回客户端
     */
    public void writeValue(HttpServletResponse response,Object obj) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }
    /**
     * 将一个对象序列化为json
     * @return json数据
     */
    public String writeValueAsString(HttpServletResponse response,Object obj) throws JsonProcessingException {
        response.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(obj);
        return json;
    }
}
