package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatController {
    @Autowired
    private ContentCategoryService contentCategoryService;


    /**
     * 查询树(种类)
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id",defaultValue ="0") Long parentId){

        List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);

        return list;

    }

    /**
     * 添加种类
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result addContentCategory(Long parentId, String name){

        E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);

        return e3Result;

    }

    /**
     * 删除种类
     */
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public E3Result deleteContentCategory(Long id){

        E3Result e3Result = contentCategoryService.deleteContentCategory(id);

        return e3Result;
    }

    /**
     * 重命名种类
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public E3Result updateContentCategory(Long id,String name){

        E3Result e3Result = contentCategoryService.updateContentCategory(id, name);

        return e3Result;

    }


}
