package pers.zhoudi.project.domain.entity;

import java.util.List;

/**
 * PageBean实体类（目的：分页查询使用）
 *
 * @author : 周迪
 * @date : 2024/03/30
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
