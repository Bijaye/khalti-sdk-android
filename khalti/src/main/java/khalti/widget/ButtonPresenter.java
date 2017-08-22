package khalti.widget;

import android.support.annotation.NonNull;

import com.utila.GuavaUtil;

import khalti.form.api.Config;
import khalti.utils.DataHolder;

class ButtonPresenter implements ButtonContract.Listener {
    @NonNull
    private final ButtonContract.View mPayView;

    ButtonPresenter(@NonNull ButtonContract.View mPayView) {
        this.mPayView = GuavaUtil.checkNotNull(mPayView);
        mPayView.setListener(this);
    }

    @Override
    public void setCustomButtonView() {
        mPayView.setCustomButtonView();
    }

    @Override
    public void setButtonText(String text) {
        mPayView.setButtonText(text);
    }

    @Override
    public void setButtonClick() {
        mPayView.setButtonClick();
    }

    @Override
    public void setConfig(Config config) {
        DataHolder.setConfig(config);
    }

    @Override
    public void openForm() {
        mPayView.openForm();
    }
}
