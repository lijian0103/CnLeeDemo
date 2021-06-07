package cn.cnlee.test.javamethod.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description object bean
 * @Author cnlee
 * @Date 2021/6/7
 * @Version 1.0
 */
@Builder
@Getter
@Setter
@ToString
public class ObjectBean {
    private int id;
    private String msg;
}
