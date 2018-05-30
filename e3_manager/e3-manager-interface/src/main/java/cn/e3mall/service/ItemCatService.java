package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.pojo.TbItem;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getCatList(long parentId);

}
