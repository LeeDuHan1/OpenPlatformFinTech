package android.bfop.kftc.com.useorgsampleapprenewal.eventbus;

/**
 * Fragment가 초기화 될 때 MainActivity 와 통신할 목적으로 EventBus 파라미터 클래스 정의
 *
 * Created by LeeHyeonJae on 2017-06-19.
 */
public class FragmentInitializedEvent {

    private boolean backArrowOnActionBar;

    private Class clazz;

    public FragmentInitializedEvent(Class clazz, boolean backArrowOnActionBar) {
        this.clazz = clazz;
        this.backArrowOnActionBar = backArrowOnActionBar;
    }

    public boolean isBackArrowOnActionBar() {
        return backArrowOnActionBar;
    }

    public Class getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "FragmentInitializedEvent{" +
                "backArrowOnActionBar=" + backArrowOnActionBar +
                ", clazz=" + clazz +
                '}';
    }
}
