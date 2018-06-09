package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询商品,索引
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery query) throws SolrServerException {
//      1.执行查询
        QueryResponse queryResponse = solrServer.query(query);
//      2.返回结果对象
        SolrDocumentList documentList = queryResponse.getResults();
//      3.查询总结果数
        long numFound = documentList.getNumFound();
        SearchResult searchResult = new SearchResult();
//       设置总数
        searchResult.setRecoundCount((int) numFound);
//      4.创建item集合
        List<SearchItem> itemList = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        for (SolrDocument solrDocument: documentList ) {
            //取商品信息
            SearchItem searchItem = new SearchItem();
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));

//           取高亮的结果
            List<String> strings = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (strings != null && strings.size() > 0) {
                title = strings.get(0);
            }else{
                title = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(title);
//          添加到集合中
            itemList.add(searchItem);
        }
        searchResult.setItemList(itemList);

        //返回SearchResult
        return searchResult;
    }
}
