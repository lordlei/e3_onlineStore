package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

/**
 * 为了不影响逆向工程中tbItem
 * 所以继承 重写这个pojo
 */
public class Item extends TbItem {

    public Item(){}


    public Item(TbItem tbItem) {
        this.setBarcode(tbItem.getBarcode());
        this.setCid(tbItem.getCid());
        this.setCreated(tbItem.getCreated());
        this.setId(tbItem.getId());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setStatus(tbItem.getStatus());
        this.setTitle(tbItem.getTitle());
        this.setUpdated(tbItem.getUpdated());
    }



    public String[] getImages() {
        String images = this.getImage();
        if (images != null && !images.equals("")) {
            String[] imgs = images.split(",");
            return imgs;
        }else {
            return null;
        }
    }
}
