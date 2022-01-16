package cn.cnlee.demo.threadcase.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/16
 * @Version 1.0
 */
@Builder
@ToString
@Getter
@Setter
@Accessors(prefix = {"m"})
public class Action {
    String mId;
    String mName;
    boolean mLoop;
    @Builder.Default
    int mLoopCount = 1;
}
