package cn.e3mall.item.listener;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用freemarker与activemq实现添加商品时
 * 实现添加网页静态化
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String stringId = textMessage.getText();
            Long id = Long.parseLong(stringId);
//          取商品信息
            TbItem tbItem = itemService.getItemById(id);
            Item item = new Item(tbItem);
//          取商品描述
            TbItemDesc tbItemDesc = itemService.getItemDescById(id);
//           创建一个数据集,把商品数据封装
            Map data=new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", tbItemDesc);
//            加载模板对象
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
//            创建一个输出流,指定文件的目录,及文件名
            Writer writer = new FileWriter(HTML_GEN_PATH+id + ".html");
//             生成静态页面
            template.process(data,writer);
//            关闭流
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
