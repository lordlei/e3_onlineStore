package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    /**
     * 添加内容
     */
    @Override
    public E3Result addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        tbContentMapper.insert(content);


        return E3Result.ok();
    }

    /**
     * 查询内容,显示轮播图
     */
    @Override
    public List<TbContent> selectContentByCategoryId(Long category) {

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(category);
        List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(example);
        return tbContents;

    }

    /**
     * 查看种类内容(查询分页)
     */
    @Override
    public EasyUIDataGridResult getContentListByCategoryId(Integer page, Integer rows, Long categoryId) {
//        查询之前设置分页
        PageHelper.startPage(page,rows);
//        执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //包括查询大文本text
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);

        PageInfo<TbContent> info = new PageInfo<>(list);
        EasyUIDataGridResult gridResult = new EasyUIDataGridResult();
        gridResult.setTotal( info.getTotal());
        gridResult.setRows(list);

        return gridResult;
    }
}
