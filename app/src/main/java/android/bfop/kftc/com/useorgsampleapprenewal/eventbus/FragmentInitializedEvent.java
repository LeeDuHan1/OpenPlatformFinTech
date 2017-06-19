package android.bfop.kftc.com.useorgsampleapprenewal.eventbus;

/**
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
