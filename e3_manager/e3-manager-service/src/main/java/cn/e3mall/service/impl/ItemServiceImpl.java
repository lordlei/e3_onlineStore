package cn.e3mall.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.*;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination topicDestination;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;

    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;


    @Override
    public TbItem getItemById(Long itemId) {
        //逆向工程  通过主键查
//        TbItem tbItem = tbItemMapper.selectByPrimaryKey(741524L);
//        通过 条件查


        //查询缓存
        try {
//            向Redis中添加缓存
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)){
                //把json转换为java对象
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return  tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TbItemExample example = new TbItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
//        通过条件查就会返回list集合 ,取第一个值
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);

        if (tbItems != null && tbItems.size() > 0) {
            try {
//            向Redis中添加缓存
                jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItems.get(0)));
//            设置缓存的过期时间
                jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItems.get(0);
        }
        return null;
    }

    /**
     * 通过商品Id获得商品描述对象
     */
    @Override
    public TbItemDesc getItemDescById(Long itemId) {

        //查询缓存
        try {
//            向Redis中添加缓存
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)){
                //把json转换为java对象
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return  tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        try {
//            向Redis中添加缓存
            jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            //设置过期时间
            jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    /**
     * 查询分页
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
//        查询之前设置分页(0,30)
        PageHelper.startPage(page, rows);
//        执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> info = new PageInfo<>(list);
        EasyUIDataGridResult gridResult = new EasyUIDataGridResult();
        gridResult.setRows(list);
        gridResult.setTotal(info.getTotal());
        return gridResult;
    }

    /**
     * 添加商品
     *
     * @param item
     * @param desc
     * @return
     */
    @Override
    public E3Result saveItem(TbItem item, String desc) {
//        1.生成商品ID
        final long id = IDUtils.genItemId();
//        2.补全Item的属性
        item.setId(id);
//        3.商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
//        向商品表插入数据
        tbItemMapper.insert(item);
// 4、创建一个TbItemDesc对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        // 5、补全TbItemDesc的属性
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        // 6、向商品描述表插入数据
        tbItemDescMapper.insert(tbItemDesc);
        //7.同步索引库,ActiveMq发送id,添加索引库
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(id+"");
            }
        });

        return E3Result.ok();
    }

    /**
     * 回显商品描述
     */
    @Override
    public E3Result selectItemDescById(Long ItemDescId) {
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(ItemDescId);
        return E3Result.ok(tbItemDesc);
    }

    /**
     * 回显商品信息
     */
    @Override
    public E3Result selectItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);

        return E3Result.ok(tbItem);
    }

    /**
     * 修改商品
     */
    @Override
    public E3Result updateItem(TbItem item, String desc) {
//        获取商品ID
        Long itemId = item.getId();
//        设置更新时间
        item.setUpdated(new Date());
//        更新商品
        tbItemMapper.updateByPrimaryKeySelective(item);
//        完善描述
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
//        更新描述
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        return E3Result.ok();
    }

    /**
     * 删除商品
     */
    @Override
    public E3Result deleteItemById(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                tbItemMapper.deleteByPrimaryKey(Long.parseLong(id));
                tbItemDescMapper.deleteByPrimaryKey(Long.parseLong(id));
            }
            return E3Result.ok();
        } else {
            return null;
        }
    }

    /**
     * 下架商品
     */
    @Override
    public E3Result instockItemById(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                TbItem tbItem = new TbItem();
                tbItem.setId(Long.parseLong(id));
                tbItem.setStatus((byte) 2);
                tbItemMapper.updateByPrimaryKeySelective(tbItem);
            }
            return E3Result.ok();
        } else {
            return null;
        }

    }

    /**
     * 上架商品
     */
    @Override
    public E3Result reshelfItemById(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                TbItem tbItem = new TbItem();
                tbItem.setStatus((byte) 1);
                tbItem.setId(Long.parseLong(id));
                tbItemMapper.updateByPrimaryKeySelective(tbItem);
            }
            return E3Result.ok();
        } else {
            return null;
        }

    }
}
