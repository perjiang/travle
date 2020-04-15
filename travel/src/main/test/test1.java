
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class test1 {
    @Test
    public void test2(){
        String  a = "/user/add?code=123";
        String substring = a.substring(a.lastIndexOf("/") + 1, a.indexOf("?"));
        System.out.println(substring);
    }
    @Test
    public void test3() throws JsonProcessingException {
//        List<User> list = new ArrayList<User>();
//        for(int i=0;i<5;i++){
//            User u = new User();
//            u.setName("1");
//            list.add(u);
//        }
   String list = "{'name':123}";
   //"{'name':'123'}";
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(list);
        System.out.println(s);
    }

}
