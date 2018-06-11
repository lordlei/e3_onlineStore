package cn.e3mall.search.listener;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 配置消息接收的监听器
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage= (TextMessage) message;
        try {
            String id = textMessage.getText();
            Long itemId=Long.parseLong(id);
            Thread.sleep(1000);
            SearchItem searchItem= itemMapper.getItemById(itemId);

            // 2、创建一SolrInputDocument对象。
            SolrInputDocument document = new SolrInputDocument();
            // 3、使用SolrServer对象写入索引库。
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            // 5、向索引库中添加文档。
            solrServer.add(document);
            solrServer.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
