package com.example.mqtest.scanqrcode.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.mqtest.scanqrcode.R;
import com.example.mqtest.scanqrcode.Transform.DensityUtil;

/**
 * Created by Administrator on 2018/2/2.
 */

public class MyDialog extends Dialog {
    Context mContext;

    public MyDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    /*public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*View view = View.inflate(mContext, R.layout.droneinfo_layout, null);
        setContentView(view);

        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
//        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = DensityUtil.dip2dp(mContext, 400);
        layoutParams.width = DensityUtil.dip2dp(mContext, 300);
        window.setAttributes(layoutParams);

        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };

        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        CustomDialog dialog = builder
                .style(R.style.MyDialog)
                .heightdp(400)
                .widthdp(300)
                .cancelTouchout(false)
                .view(R.layout.drone_edit_info)
                .addViewOnClick(R.id.confirm, listener)
                .build();
        dialog.show();
    }
}
