package khalti.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.khalti.R;
import com.utila.ActivityUtil;
import com.utila.EmptyUtil;

import java.util.HashMap;

import khalti.carbonX.widget.FrameLayout;
import khalti.form.CheckOutActivity;
import khalti.form.api.Config;
import khalti.utils.DataHolder;

public class Button extends FrameLayout {
    private Context context;
    private AttributeSet attrs;

    private ButtonContract.Listener listener;
    private android.widget.FrameLayout flContainer;
    private khalti.carbonX.widget.Button btnPay;
    private View customView;

    public Button(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;

        BasicPay basicPay = new BasicPay();
        listener = basicPay.getListener();
        init();
    }

    public void setText(String text) {
        listener.setButtonText(text);
    }

    public void setConfig(Config config) {
        listener.setConfig(config);
    }

    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        DataHolder.setOnSuccessListener(onSuccessListener);
    }

    public void setCustomView(View view) {
        this.customView = view;
        listener.setCustomButtonView();
        listener.setButtonClick();
    }

    private void init() {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.khalti, 0, 0);
        String buttonText = a.getString(R.styleable.khalti_text);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainView = inflater.inflate(R.layout.component_button, this, true);

        btnPay = mainView.findViewById(R.id.btnPay);
        flContainer = mainView.findViewById(R.id.flContainer);
        listener.setButtonText(buttonText);

        listener.setButtonClick();
    }

    public interface OnSuccessListener {
        void onSuccess(HashMap<String, Object> data);
    }

    private class BasicPay implements ButtonContract.View {
        private ButtonContract.Listener listener;

        BasicPay() {
            listener = new ButtonPresenter(this);
        }

        @Override
        public void setCustomButtonView() {
            btnPay.setVisibility(View.GONE);
            flContainer.addView(customView);
        }

        @Override
        public void setButtonText(String text) {
            btnPay.setText(text);
        }

        @Override
        public void setButtonClick() {
            if (EmptyUtil.isNotNull(customView)) {
                flContainer.getChildAt(0).setOnClickListener(view -> listener.openForm());
            } else {
                btnPay.setOnClickListener(view -> listener.openForm());
            }
        }

        @Override
        public void openForm() {
            if (EmptyUtil.isNull(DataHolder.getConfig())) {
                throw new IllegalArgumentException("Config not set");
            }
            if (EmptyUtil.isNull(DataHolder.getOnSuccessListener())) {
                throw new IllegalArgumentException("OnSuccessListener not set");
            }
            ActivityUtil.openActivity(CheckOutActivity.class, context, false, null, true);
        }

        ButtonContract.Listener getListener() {
            return listener;
        }

        @Override
        public void setListener(ButtonContract.Listener listener) {
            this.listener = listener;
        }
    }
}
