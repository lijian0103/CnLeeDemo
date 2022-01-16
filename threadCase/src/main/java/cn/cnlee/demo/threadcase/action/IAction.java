package cn.cnlee.demo.threadcase.action;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/26
 * @Version 1.0
 */
public interface IAction {
    /**
     * 执行动作
     */
    void executeAction();

    /**
     * 打断动作
     */
    void interruptAction();
}
