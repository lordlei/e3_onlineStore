package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

public interface ItemService {
    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getItemList(int page,int rows);

    E3Result saveItem(TbItem item,String desc);

    E3Result selectItemDescById(Long ItemDescId);

    E3Result selectItemById(Long itemId);

    E3Result updateItem(TbItem item, String desc);

    E3Result deleteItemById(String[] ids);

    E3Result instockItemById(String[] ids);


    E3Result reshelfItemById(String[] ids);
}
