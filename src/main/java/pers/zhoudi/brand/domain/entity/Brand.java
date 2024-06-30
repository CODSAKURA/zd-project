package pers.zhoudi.brand.domain.entity;

import javax.persistence.*;

/**
 * Brand实体类
 *
 * @author : 周迪
 * @date : 2024/04/09
 */
@Entity//hibernate的实体类
@Table(name = "tb_brand")//映射的表名
public class Brand {
    @Id//当前属性为表的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键的生成策略（自增长）
    @Column(name = "id")//表的列名
    private Integer id;

    // 品牌名称
    @Column(name = "brand_name")
    private String brandName;

    // 企业名称
    @Column(name = "company_name")
    private String companyName;

    // 排序字段
    @Column(name = "ordered")
    private Integer ordered;

    // 描述信息
    @Column(name = "description")
    private String description;

    // 状态：0：禁用  1：启用
    @Column(name = "status")
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    // 逻辑视图
    public String getStatusStr() {
        if (status == null) {
            return "未知";
        }
        return status == 0 ? "禁用" : "启用";
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "brandName='" + brandName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
                ", status=" + status;
    }
}
