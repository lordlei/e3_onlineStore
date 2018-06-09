package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${PAGE_ROWS}")
    private Integer PAGE_ROWS;


    @RequestMapping("/search")
    public String search(Model model, String keyword, @RequestParam(defaultValue ="1") int page ) throws Exception {
        //get提交乱码 解决
        keyword=new String(keyword.getBytes("iso8859-1"), "utf-8");
//          执行查询
        SearchResult search = searchService.search(keyword, page, PAGE_ROWS);
//        回显查询条件
         model.addAttribute("query",keyword);
//        总页数
        model.addAttribute("totalPages", search.getTotalPages());
//        当前页数
        model.addAttribute("page", page);
//        总数量
        model.addAttribute("recourdCount", search.getRecoundCount());
//        商品的List集合
        model.addAttribute("itemList", search.getItemList());
//      测试全局异常处理
//        int a=1/0;

//        返回逻辑视图
        return "search";

    }
}
