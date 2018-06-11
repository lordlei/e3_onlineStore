package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;


    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable long itemId,Model model){
        //跟据商品id查询商品信息
        TbItem itemById = itemService.getItemById(itemId);
        //把TbItem转换成Item对象
        Item item = new Item(itemById);
        //根据商品id查询商品描述
        TbItemDesc itemDescById = itemService.getItemDescById(itemId);
        //把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDescById);
        return "item";
    }
}
