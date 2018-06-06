import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

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
}
