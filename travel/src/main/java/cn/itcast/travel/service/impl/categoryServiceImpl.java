package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.categoryDao;
import cn.itcast.travel.dao.impl.categoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.categoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class categoryServiceImpl implements categoryService {
    private categoryDao dao = new categoryDaoImpl();

    /**
     * 这个方法是查询所有的旅游类型
     * 注意:由于后面的需求需要传递旅游类型的id所以在获取redis客户端数据的时候一定要将分数(也就是cid)一并获取
     * @return
     */
    @Override
    public List<Category> findAll() {
        List<Category> list = null;
        //从redis获取数据
        Jedis jedis = JedisUtil.getJedis();//获取jedis客户端
        //Set<String> categorys = jedis.zrange("category", 0, -1); //使用sortedset排序查询
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);//查询cid和cname也就是把category里的分数和值都查出来
        //判断集合是否为空
        if(categorys == null || categorys.size() == 0){
            System.out.println("数据库获取数据");
            //从数据库查询
            list = dao.findAll();
            for(int a=0;a<list.size();a++){
                //将集合的数据存放到redis
                jedis.zadd("category",list.get(a).getCid(),list.get(a).getCname());
            }
        }else{
            System.out.println("redis获取数据");
            list = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category cate = new Category();
                cate.setCname(tuple.getElement());
                cate.setCid((int)tuple.getScore());
                list.add(cate);
            }
        }
        return  list;
    }
}
