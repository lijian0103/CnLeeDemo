package cn.cnlee.test.javamethod;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.cnlee.test.javamethod.bean.ObjectBean;
import cn.cnlee.test.javamethod.thread.ICallBack;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 测试局部变量生命周期
 * @Author cnlee
 * @Date 2022/6/30
 * @Version 1.0
 */
public class TestLocalVariableCase {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);

    @Getter
    @Setter
    private ObjectBean mObjectBean;

    public static void main(String[] args) {
        TestLocalVariableCase tLvc = new TestLocalVariableCase();
        tLvc.testMethod(1);
        tLvc.testMethod(2);
        tLvc.testMethod(3);
    }


    private void testMethod(int id) {
        mObjectBean = ObjectBean.builder().id(id).msg("msg_" + id).build();
        System.err.println("testMethod1. " + mObjectBean);
        ObjectBean objectBean = mObjectBean;
        testCallback(objectBean.getId(), objectBean.getMsg(), new ICallBack() {
            @Override
            public void onCallBack(int id, String msg) {
                System.err.println("test callback onCallBack. id: " + id + " ,msg: " + msg
                        + " | testMethod onCallBack. " + objectBean);
            }
        });
        scheduledExecutorService.schedule(() -> {
            System.err.println("testMethod2. " + id + " | " + objectBean + " ,mObjectBean: " + mObjectBean);
        }, 1000l, TimeUnit.MILLISECONDS);
    }

    private void testCallback(int id, String msg, ICallBack callBack) {
        scheduledExecutorService.schedule(() -> {
            System.err.println("test callback run");
            callBack.onCallBack(id, msg);
        }, 2000l, TimeUnit.MILLISECONDS);
    }
}
