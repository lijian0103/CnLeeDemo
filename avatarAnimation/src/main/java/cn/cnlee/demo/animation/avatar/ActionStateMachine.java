package cn.cnlee.demo.animation.avatar;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.saic.statemachinejar.State;
import com.saic.statemachinejar.StateMachine;

import java.io.Serializable;
import java.util.List;

public class ActionStateMachine extends StateMachine {
    private static final String TAG = ActionStateMachine.class.getSimpleName();

    private static final String OPTIONS_BUNDLE_KEY = "options_bundle_key";
    private static final String REPEAT_BUNDLE_KEY = "repeat_bundle_key";

    private static final int MSG_TO_IDLE = 0; //turn idle
    public static final int MSG_TO_WORK = 1; //turn work
    public static final int MSG_TO_ADD = 2; //to add behavior

    private final State mDefaultState = new DefaultState(); //default state
    private final State mPendingState = new PendingState(); //pending state
    private final State mRunningState = new RunningState(); //running state

    protected ActionStateMachine(String name) {
        super(name);
        init();
    }

    private void init() {
        addState(mDefaultState, null);
        addState(mPendingState, mDefaultState);
        addState(mRunningState, mPendingState);

        setInitialState(mDefaultState);
    }

    /**
     * get ActionStateMachine instance.
     *
     * @return instance
     */
    public static ActionStateMachine makeStateMachine() {
        ActionStateMachine actionStateMachine = new ActionStateMachine("avatar");
        actionStateMachine.start();
        return actionStateMachine;
    }

    public void turnToWork() {
        transitionTo(mPendingState);
        sendMessage(MSG_TO_WORK);
    }

    class DefaultState extends State {
        @Override
        public void enter() {
            Log.d(TAG, "[DefaultState] enter");
        }

        @Override
        public void exit() {
            Log.d(TAG, "[DefaultState] exit");
        }

        @Override
        public boolean processMessage(Message msg) {
            Log.e(TAG, "[DefaultState] processMessage. msg.what: " + msg.what);
            if (msg.what == MSG_TO_ADD) {
                Bundle data = msg.getData();
                BehaviorExecuteManager.getInstance().addBehavior((List<AvatarOption>) data.getSerializable(OPTIONS_BUNDLE_KEY), data.getBoolean(REPEAT_BUNDLE_KEY));
                return HANDLED;
            }
            return NOT_HANDLED;
        }
    }

    /**
     * pending state class.
     */
    class PendingState extends State {

        @Override
        public void enter() {
            Log.d(TAG, "[PendingState] enter");
        }

        @Override
        public void exit() {
            Log.d(TAG, "[PendingState] exit");
        }

        @Override
        public boolean processMessage(Message msg) {
            Log.d(TAG, "[PendingState] processMessage. msg.what: " + msg.what);
            if (msg.what == MSG_TO_WORK) {
                BehaviorExecuteManager behaviorExecuteManager = BehaviorExecuteManager.getInstance();
                AvatarOption avatarOption = behaviorExecuteManager.getNextAction();
                if (avatarOption != null) {
                    transitionTo(mRunningState);
                    Log.d(TAG, "get avatar by avatarType[ " + avatarOption.getAvatarType() + " ] in option");
                    //TODO get avatar by avatarType in option
                    new Avatar().performAction(avatarOption, (option, status, errMsg) -> {
                        behaviorExecuteManager.notifyActionExecuteRes(option, status, errMsg);
                        sendMessage(MSG_TO_IDLE);
                    });
                }
            }
            return NOT_HANDLED;
        }
    }


    /**
     * running state class.
     */
    class RunningState extends State {
        @Override
        public void enter() {
            Log.d(TAG, "[RunningState] enter");
        }

        @Override
        public void exit() {
            Log.d(TAG, "[RunningState] exit");
        }

        @Override
        public boolean processMessage(Message msg) {
            Log.d(TAG, "[RunningState] processMessage. msg.what: " + msg.what);
            if (msg.what == MSG_TO_IDLE) {
                turnToWork();
                return HANDLED;
            }
            return NOT_HANDLED;
        }
    }

    public void addBehavior(List<AvatarOption> options, boolean needRepeat) {
        Message msg = new Message();
        msg.what = MSG_TO_ADD;
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPTIONS_BUNDLE_KEY, (Serializable) options);
        bundle.putBoolean(REPEAT_BUNDLE_KEY, needRepeat);
        msg.setData(bundle);
        sendMessage(msg);
    }
}
