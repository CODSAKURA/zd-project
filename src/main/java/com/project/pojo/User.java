package com.project.pojo;

import javax.persistence.*;

@Entity//hibernate的实体类
@Table(name = "tb_user")//映射的表名
public class User {

    @Id//当前属性为表的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键的生成策略（自增长）
    @Column(name = "id")//表的列名
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
