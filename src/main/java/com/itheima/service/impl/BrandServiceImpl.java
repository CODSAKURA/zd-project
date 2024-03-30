package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceImpl implements BrandService {

    //统一：获取SqlSessionFactory对象
    private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public void add(Brand brand){
        // 获取openSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取brandMapper
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        // 调用方法
        brandMapper.add(brand);

        // 提交事务
        sqlSession.commit();

        //释放资源
        sqlSession.close();
    }

    public void delete(int id){
        // 获取openSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取brandMapper
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        // 调用方法
        brandMapper.delete(id);

        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
    }

    public void update(Brand brand){
        // 获取openSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取brandMapper
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        // 调用方法
        brandMapper.update(brand);

        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
    }

    public void deleteByIds(int[] ids){
        // 获取openSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取brandMapper
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        // 调用方法
        brandMapper.deleteByIds(ids);

        // 提交事务
        sqlSession.commit();

        // 释放资源
        sqlSession.close();
    }

    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        // 获取openSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取brandMapper
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);

        // 调用方法
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        // 处理brand条件【手动添加模糊表达式】
        brand.setBrandName("%" + brand.getBrandName() + "%");
        brand.setCompanyName("%" + brand.getCompanyName() + "%");

        List<Brand> rows = brandMapper.selectByPageAndCondition(begin, size, brand);
        int totalCount = brandMapper.selectTotalCountByCondition(brand);

        PageBean<Brand> pageBean = new PageBean<Brand>();
        pageBean.setRow(rows);
        pageBean.setTotalCount(totalCount);

        // 释放资源
        sqlSession.close();

        // 返回pageBean
        return pageBean;
    }
}
