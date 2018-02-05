package com.example.mqtest.scanqrcode.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mqtest.scanqrcode.Transform.DensityUtil;

/**
 * Created by Administrator on 2018/2/2.
 */

public class CustomDialog extends Dialog {

    private Context mContext;
    private int height, width;
    private boolean cancelTouchout;
    private View view;

    public CustomDialog(Builder builder) {
        super(builder.mContext);
        this.mContext = builder.mContext;
        this.height = builder.height;
        this.width = builder.width;
        this.cancelTouchout = builder.cancelTouchout;
        this.view = builder.view;
    }

    public CustomDialog(Builder builder, int resStyle) {
        super(builder.mContext, resStyle);
        this.mContext = builder.mContext;
        this.height = builder.height;
        this.width = builder.width;
        this.cancelTouchout = builder.cancelTouchout;
        this.view = builder.view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }

    public static final class Builder{
        private Context mContext;
        private int height, width;
        private boolean cancelTouchout;
        private View view;
        private int resStyle = -1;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder view(int resView) {
          //  LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //  view = inflater.inflate(resView, null);
            view = LayoutInflater.from(mContext).inflate(resView, null);
            return this;
        }

        public Builder heightpx(int val) {
            height = val;
            return this;
        }

        public Builder widthpx(int val) {
            width = val;
            return this;
        }
        public Builder heightdp(int val) {
            height = DensityUtil.dip2dp(mContext,val);
            return this;
        }

        public Builder widthdp(int val) {
            width = DensityUtil.dip2dp(mContext,val);
            return this;
        }

        public Builder heightDimenRes(int dimenRes) {
            height = mContext.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes) {
            width = mContext.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder addViewOnClick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public Builder addTextView(int viewRes, String date) {
            ((TextView) (view.findViewById(viewRes))).setText(date);
            return this;
        }

        public Builder addEditText(int viewRes, String date) {
            ((EditText) (view.findViewById(viewRes))).setText(date);
            return this;
        }

        public Builder addButtonText(int viewRes, String date) {
            ((Button) (view.findViewById(viewRes))).setText(date);
            return this;
        }

        public String getEditText(int viewRes) {
            return ((EditText) (view.findViewById(viewRes))).getText().toString();
        }

        public CustomDialog build() {
            if (resStyle != -1) {
                return new CustomDialog(this,resStyle);
            } else {
                return new CustomDialog(this);
            }
        }

    }
}
