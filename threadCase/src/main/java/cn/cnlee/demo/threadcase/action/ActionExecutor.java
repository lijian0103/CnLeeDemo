package cn.cnlee.demo.threadcase.action;

import java.util.LinkedList;
import java.util.List;

import cn.cnlee.demo.threadcase.bean.Action;
import cn.cnlee.demo.threadcase.mock.Haizi;
import cn.cnlee.demo.threadcase.mock.IPlayAsset;

/**
 * @Description TODO
 * @Author cnlee
 * @Date 2022/1/26
 * @Version 1.0
 */
public class ActionExecutor {
    private LinkedList<Action> mActions = new LinkedList<>();
    private ExecutorState mCurrentState;
    private Action mCurrentAction;

    public void execute(List<Action> actionList) {
        RequestExecutor.getInstance().getRequestHandler().post(() -> {
            if (mCurrentState == ExecutorState.RUNNING) {
                //当准备执行时，当前状态为running，先清空旧动作列表mActions
                mActions.clear();
                //添加新动作入列表
                mActions.addAll(actionList);
                //开始从头执行
                Action action = mActions.poll();
                if (action != null) {
                    doAction(action);
                } else {
                    //TODO 返回结果
                    finish();
                }
            }
            mCurrentState = ExecutorState.RUNNING;
        });

    }

    private void doAction(Action action) {
        Haizi.play_anim_clip(action.getName(), new IPlayAsset() {
            @Override
            public void onPlayEnd(boolean state) {
                int loopCount = action.getLoopCount();
                if (loopCount > 0) {
                    // 如果循环次数大于0，先将次数减1，则继续调用doAction
                    action.setLoopCount(--loopCount);
                    doAction(action);
                } else {
                    // TODO 可能有问题，mAction可能已经变了
                    //执行完动作后，检查动作list中是否还有action
                    Action nextAction = mActions.poll();
                    if (nextAction != null) {
                        doAction(nextAction);
                    } else {
                        //TODO 返回结果
                        finish();
                    }
                }
            }
        });
        mCurrentAction = action;
    }

    public void finish() {
        mCurrentAction = null;
        mActions.clear();
        mCurrentState = ExecutorState.IDLE;
    }

}
