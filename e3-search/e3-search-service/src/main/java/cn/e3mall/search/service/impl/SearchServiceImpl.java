package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * 查询商品
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Value("${DEFAULT_FIELD}")
    private String DEFAULT_FIELD;

    @Override
    public SearchResult search(String keyWord, int page, int rows) throws SolrServerException {
//       创建一个solrQuery对象
        SolrQuery solrQuery = new SolrQuery();
//        设置查询条件
        solrQuery.set("q",keyWord);
//        设置分页
        solrQuery.setStart((page - 1)*rows);
        solrQuery.setRows(rows);
//        设置默认搜索域
        solrQuery.set("df", DEFAULT_FIELD);
//         设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");
//        执行查询
        SearchResult searchResult = searchDao.search(solrQuery);
         //计算总页数
        int recourdCount = searchResult.getRecoundCount();
        int pages = recourdCount / rows;
        if (recourdCount % rows > 0) pages++;
//       设置总页数
        searchResult.setTotalPages(pages);

        return searchResult;
    }
}
