package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private List<SearchItem> itemList;
    //总页数
    private int totalPages;
    //总条数
    private int recoundCount;

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRecoundCount() {
        return recoundCount;
    }

    public void setRecoundCount(int recoundCount) {
        this.recoundCount = recoundCount;
    }
}
