package pers.brand.domain.entity;

import java.util.List;

/**
 * 分页查询的JavaBean
 */
public class PageBean<T> {

    //总记录数
    private int totalCount;

    //当前页数据
    private List<T> row;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRow() {
        return row;
    }

    public void setRow(List<T> row) {
        this.row = row;
    }
}
