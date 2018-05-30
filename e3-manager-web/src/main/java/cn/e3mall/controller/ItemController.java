package cn.e3mall.controller;



import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import javassist.runtime.Desc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping("item/{itemId}")
    @ResponseBody//@ResponseBody返回的对象自动封装成json
    // @PathVariable是用来对指定请求的URL路径里面的变量
    public TbItem getItemById(@PathVariable Long itemId){

        TbItem tbItem = itemService.getItemById(itemId);

        return tbItem;

    }

    /**
     * 查询分页
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){

        EasyUIDataGridResult list = itemService.getItemList(page, rows);

        return list;
    }
    /**
     * 添加商品
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result saveItem(TbItem item,String desc){
        E3Result e3Result = itemService.saveItem(item, desc);
        return e3Result;
    }

    /**
     * 回显商品描述
     */
    @RequestMapping("/rest/item/query/item/desc/{ItemDescId}")
    @ResponseBody
    public E3Result selectItemDesc(@PathVariable Long ItemDescId){
        E3Result e3Result = itemService.selectItemDescById(ItemDescId);
        return e3Result;
    }

    /**
     * 回显商品信息
     */
    @RequestMapping("/rest/item/param/item/query/{ItemId}")
    @ResponseBody
    public E3Result selectItem(@PathVariable Long ItemId){
        E3Result e3Result = itemService.selectItemById(ItemId);
        return e3Result;
    }

    /**
     * 修改商品信息
     */
    @RequestMapping("/rest/item/update")
    @ResponseBody
    public E3Result updateItem(TbItem item,String desc){
        E3Result e3Result = itemService.updateItem(item, desc);
        return e3Result;
    }

    /**
     * 修改商品信息
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItem(String[] ids){
        E3Result e3Result = itemService.deleteItemById(ids);
        return e3Result;
    }
    /**
     * 下架商品
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public E3Result instockItem(String[] ids){
        E3Result e3Result = itemService.instockItemById(ids);
        return e3Result;
    }

    /**
     * 上架商品
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public E3Result reshelfItem(String[] ids){
        E3Result e3Result = itemService.reshelfItemById(ids);
        return e3Result;
    }


}
