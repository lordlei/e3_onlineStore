import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class testPageHelper {
    @Test
    public void fun(){
//        初始化spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
//          从spring对象中获取mapper对象
        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
//          执行sql语句之前设置分页信息使用pagehelper的startPage方法
        PageHelper.startPage(1,10);
//        执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
//        取分页信息,pageInfo 1.总记录数 2.总页数 3.当前页码
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPages());
        System.out.println(list.size());


    }

}
