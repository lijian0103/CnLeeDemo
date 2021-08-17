package cn.cnlee.app.actionexecutor.action;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2021/8/13
 * @Version 1.0
 */

public class ActionInfo {

    private State mState;

    public enum State {

        ENQUEUED,
        RUNNING,
        SUCCEEDED,
        FAILED,
        BLOCKED,
        CANCELLED;

        public boolean isFinished() {
            return (this == SUCCEEDED || this == FAILED || this == CANCELLED);
        }
    }
}
