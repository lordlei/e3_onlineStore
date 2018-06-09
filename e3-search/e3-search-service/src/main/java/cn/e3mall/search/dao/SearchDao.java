package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;


public interface SearchDao {
     SearchResult search(SolrQuery query) throws SolrServerException;
}
