package pers.zhoudi.brand.domain.entity;

import javax.persistence.*;

/**
 * Log实体类
 *
 * @author : 周迪
 * @date : 2024/04/09
 */
@Entity//hibernate的实体类
@Table(name = "tb_log")//映射的表名
public class Log {

    @Id//当前属性为表的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键的生成策略（自增长）
    @Column(name = "id")//表的列名
    private Integer id;

    @Column(name = "log_method")
    private String logMethod;

    @Column(name = "log_parameters")
    private String logParameters;

    @Column(name = "log_time")
    private String logTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public String getLogParameters() {
        return logParameters;
    }

    public void setLogParameters(String logParameters) {
        this.logParameters = logParameters;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", logMethod='" + logMethod + '\'' +
                ", logParameters=" + logParameters +
                ", logTime='" + logTime + '\'' +
                '}';
    }
}
