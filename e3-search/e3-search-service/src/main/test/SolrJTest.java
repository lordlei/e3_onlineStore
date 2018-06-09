import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import javax.management.Query;
import java.util.List;
import java.util.Map;

public class SolrJTest {

    /**
     * 添加文档至索引库
     * @throws Exception
     */
    @Test
    public void importTest() throws Exception {
        // 1、把solrJ的jar包添加到工程。
        // 2、创建一个SolrServer对象。创建一个和sorl服务的连接。HttpSolrServer。
        //如果不带Collection默认连接Collection1
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        // 3、创建一个文档对象。SolrInputDocument。
        SolrInputDocument document = new SolrInputDocument();
        // 4、向文档对象中添加域。必须有一个id域。而且文档中使用的域必须在schema.xml中定义。
        document.addField("id", "123");
        document.addField("item_title", "123");
        // 5、把文档添加到索引库
        httpSolrServer.add(document);
        // 6.commit
        httpSolrServer.commit();
    }

    /**
     * 查询索引
     */
    @Test
    public void query() throws SolrServerException {
        //        创建httpsolrService 的对象
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        //        创建查询对象
        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setQuery("*:*");
        solrQuery.set("q", "*:*");
//        执行查询
        QueryResponse query = httpSolrServer.query(solrQuery);
//        获取结果
        SolrDocumentList list = query.getResults();
        System.out.println("查询的总数"+list.getNumFound());
        for (SolrDocument document :list) {
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_category_name"));
        }
    }

    /**
     * 复杂查询索引 高亮查询
     */
    @Test
    public void queryfuza() throws SolrServerException {
//        创建httpsolrService 的对象
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
//        创建查询对象
        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setQuery("*:*");
        solrQuery.set("q", "手机");
//        打开高亮
        solrQuery.setHighlight(true);
//        设置高亮查询的域
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
//        执行查询
        QueryResponse query = httpSolrServer.query(solrQuery);
//        获取结果
        SolrDocumentList list = query.getResults();
        System.out.println("查询的总数"+list.getNumFound());
//        获取高亮的查询结果
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
        for (SolrDocument document :list) {
            System.out.println(document.get("id"));
            //根据高亮的结果的id,与域名查询内容
            List<String> strings = highlighting.get(document.get("id")).get("item_title");
            String title = "";
            if (strings!=null&&strings.size()>0){
                //不为空则获取高亮title
                title = strings.get(0);
            }else{
                //为空则获取查询结果的title
                title = (String) document.get("item_title");
            }
            System.out.println(title);
            System.out.println(document.get("item_sell_point"));
            System.out.println(document.get("item_price"));
            System.out.println(document.get("item_image"));
            System.out.println(document.get("item_category_name"));
        }
    }
}
