package cn.cnlee.demo.rxjavacase.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class EventResult {
    private String id;
    private int status;
    private String result;
    private String msg;
}
