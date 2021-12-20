package cn.cnlee.demo.databindingrecyclerview.data;

import java.util.List;

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
public class Category {
    private String mTitle;
    private String mSubTitle;
    private String mTips;
    private List<Book> mBooks;
}
