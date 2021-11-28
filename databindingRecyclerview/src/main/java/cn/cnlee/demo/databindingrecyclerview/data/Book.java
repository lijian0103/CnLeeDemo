package cn.cnlee.demo.databindingrecyclerview.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/11/27
 * @Version 1.0
 */
@Builder
@ToString
@Getter
@Setter
@Accessors(prefix = "m")
public class Book {
    private String mImg;
    private String mName;
    private String mStatus;
}
