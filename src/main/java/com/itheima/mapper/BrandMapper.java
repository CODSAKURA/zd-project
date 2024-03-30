package com.itheima.mapper;

import com.itheima.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BrandMapper {

    /**
     * 添加数据
     * 注：MyBatis使用一种称为"参数映射"的过程。SQL语句中的 `#{brandName}`, `#{companyName}`, `#{ordered}`, `#{description}`, 和
     * `#{status}`，MyBatis会检查 `Brand` 类，从 `brand` 参数中获取这些属性的值，并将它们填充于SQL语句。因此，你不需要在 `add` 方法中显式
     * 地将每个属性作为单独的参数传递。MyBatis负责将参数对象（在这种情况下是 `brand`）的属性映射到SQL语句。这是MyBatis的一个便利特性，简化了代码
     * 并使其更易维护。
     */
    @Insert("insert into tb_brand values(null, #{brandName}, #{companyName}, #{ordered}, #{description}, #{status})")
    void add(Brand brand);

    /**
     * 删除数据
     */
    @Delete("delete from tb_brand where id = #{id}")
    void delete(int id);

    /**
     * 更新数据
     */
    @Update("update tb_brand set brand_name = #{brandName}, company_name = #{companyName}, ordered = #{ordered}, description = #{description}, status = #{status} where id = #{id}")
    void update(Brand brand);

    /**
     * 批量删除
     */
    void deleteByIds(@Param("ids") int[] ids);

    /**
     * 分页条件查询
     */
    List<Brand> selectByPageAndCondition(@Param("begin") int begin, @Param("size") int size, @Param("brand") Brand brand);

    /**
     * 条件查询总记录数
     */
    int selectTotalCountByCondition(Brand brand);
}
