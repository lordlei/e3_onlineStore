package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;


    /**
     * 查询树(种类)
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        // 1、取查询参数id，parentId
        // 2、根据parentId查询tb_content_category，查询子节点列表。
        TbContentCategoryExample example = new TbContentCategoryExample();
        //设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        // 3、得到List<TbContentCategory>
        List<TbContentCategory> categoryList = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> treeList = new ArrayList<>();
        // 4、把列表转换成List<EasyUITreeNode>ub
        for (TbContentCategory tbContentCategory : categoryList) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            treeList.add(easyUITreeNode);
        }
        return treeList;
    }

    /**
     * 添加种类
     */
    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        // 1、接收两个参数：parentId、name
        // 2、向tb_content_category表中插入数据。
        // a)创建一个TbContentCategory对象
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        // b)补全TbContentCategory对象的属性
        contentCategory.setIsParent(false);//新建的子节点一定是叶子节点
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        contentCategory.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        contentCategory.setStatus(1);
        // c)向tb_content_category表中插入数据
        tbContentCategoryMapper.insert(contentCategory);
        // 3、判断父节点的isparent是否为true，不是true需要改为true。
        TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentCategory.getIsParent()) {
            parentCategory.setIsParent(true);

            //更新父节点
            tbContentCategoryMapper.updateByPrimaryKeySelective(parentCategory);
        }
        // 4、需要主键返回。
        // 5、返回E3Result，其中包装TbContentCategory对象
        return E3Result.ok(contentCategory);
    }

    /**
     * 删除种类(如果包含子节点,递归删除全部)
     */
    @Override
    public E3Result deleteContentCategory(Long id) {
        //获取节点
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
//        if (tbContentCategory.getIsParent()) {
//            return E3Result.build(500, "请逐一删除子节点");
//        } else {
        if (tbContentCategory.getParentId() != 0) {
            //通过节点的父id查询父节点
            TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());

            //判断父节点是否有多个子节点
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(tbContentCategory.getParentId());
            //执行查询
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            //判断是否为多个节点 1个则将父节点改为子节点 ,
//                               1个以上表示还是父节点
            if (list.size() == 1) {
                parentCategory.setIsParent(false);
                tbContentCategoryMapper.updateByPrimaryKeySelective(parentCategory);
            }
            //判断是否为目录节点
            if (tbContentCategory.getIsParent()) {
                //递归删除所有节点下的所有字节点及目录下的
                delete(id);
                //删除节点
                tbContentCategoryMapper.deleteByPrimaryKey(id);
            } else {
                //删除节点
                tbContentCategoryMapper.deleteByPrimaryKey(id);
            }
        }
        return E3Result.ok();

    }


    /**
     * 子节点调用递归删除
     */
    public void delete(Long id) {
        //查询子节点集合
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        //遍历集合,逐一删除
        for (TbContentCategory tbContentCategory : list) {
            //判断是否为叶子节点
            if (tbContentCategory.getIsParent()) {
                //删除目录节点 使用递归
                delete(tbContentCategory.getId());
                //递归只删除了子节点,子节点删完后需要删除原本的父节点
                tbContentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
            } else {
                //删除叶子节点
                tbContentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
            }
        }
    }

    /**
     * 重命名
     */
    @Override
    public E3Result updateContentCategory(Long id, String name) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategory.setName(name);
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return E3Result.ok();
    }
}
