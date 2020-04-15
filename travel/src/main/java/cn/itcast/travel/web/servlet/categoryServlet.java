package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.categoryService;
import cn.itcast.travel.service.impl.categoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class categoryServlet extends BaseServlet {
    private categoryService service = new categoryServiceImpl();
    /**
     * 查询所有的旅游种类
     */
    public void findAll(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<Category> categories = service.findAll();
        response.setContentType("application/json;charset=utf-8");
        super.writeValue(response,categories);
    }
}
