package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchService {
    SearchResult search(String keyWord,int page,int rows) throws SolrServerException;
}
