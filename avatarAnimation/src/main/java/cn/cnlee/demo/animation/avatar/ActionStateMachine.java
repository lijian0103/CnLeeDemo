package cn.cnlee.demo.animation.avatar;

import android.os.Message;
import android.util.Log;

import com.saic.statemachinejar.State;
import com.saic.statemachinejar.StateMachine;

public class ActionStateMachine extends StateMachine {
    private static final String TAG = ActionStateMachine.class.getSimpleName();

    private static final int MSG_TO_IDLE = 0; //turn idle
    public static final int MSG_TO_WORK = 1; //turn work

    private final State mPendingState = new PendingState(); //pending state
    private final State mRunningState = new RunningState(); //running state

    protected ActionStateMachine(String name) {
        super(name);
        init();
    }

    private void init() {
        addState(mPendingState, null);
        addState(mRunningState, mPendingState);

        setInitialState(mPendingState);
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
            Log.d(TAG, "[PendingState] processMessage");
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
            Log.d(TAG, "[RunningState] processMessage");
            if (msg.what == MSG_TO_IDLE) {
                turnToWork();
                return HANDLED;
            }
            return NOT_HANDLED;
        }
    }
}
