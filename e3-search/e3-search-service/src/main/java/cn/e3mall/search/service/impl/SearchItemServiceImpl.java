package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 将商品数据导入索引库
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public E3Result importAllItems() {
        try {
//        查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
//           导入索引库
            for (SearchItem item : itemList) {
                SolrInputDocument document = new SolrInputDocument();
//                向文档中添加域
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
//              写入索引库
                solrServer.add(document);
            }
//           提交
            solrServer.commit();
//           返回成功
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
//            返回失败
            return E3Result.build(500, "导入索引失败");
        }
    }
}
