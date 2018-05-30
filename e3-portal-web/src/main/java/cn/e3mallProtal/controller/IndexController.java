package cn.e3mallProtal.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.List;

/**
 *首页展示controller,轮播图展示
 */
@Controller
public class IndexController {

    @Value("${CONTENT_CATEGORY}")
    private Long CONTENT_CATEGORY;

    @Autowired
    private ContentService contentService;


   @RequestMapping("/index")
    public String showIndex(Model model) {

       List<TbContent> ad1List = contentService.selectContentByCategoryId(CONTENT_CATEGORY);
       model.addAttribute("ad1List", ad1List);
       return "index";
    }

}
