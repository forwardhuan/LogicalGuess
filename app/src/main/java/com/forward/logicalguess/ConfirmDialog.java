package com.forward.logicalguess;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ljh on 18-3-23.
 */

public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private static ConfirmDialog mConfirmDialog;
    private OnConfirmListener mConfirmListener;
    private TextView mTitle;
    private Button mCancel;
    private Button mConfirm;

    public static ConfirmDialog getInstance(Context context) {
        if (mConfirmDialog == null) {
            mConfirmDialog = new ConfirmDialog(context);
        }
        return mConfirmDialog;
    }

    private ConfirmDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warn_dialog);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        mTitle = (TextView) findViewById(R.id.title);
        mCancel = (Button) findViewById(R.id.cancel);
        mConfirm = (Button) findViewById(R.id.confirm);
    }

    public void initData() {

    }

    public void initListener() {
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    public void showDialog(String title,
                           String cancel, String confirm, OnConfirmListener dialogListener) {
        show();
        mConfirmListener = dialogListener;
        mTitle.setText(title);
        mCancel.setText(cancel);
        mConfirm.setText(confirm);
    }

    public void showDialog(String title, OnConfirmListener dialogListener) {
        show();
        mConfirmListener = dialogListener;
        mTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (mConfirmListener != null) {
                    mConfirmListener.cancel(mConfirmDialog);
                }
                break;
            case R.id.confirm:
                if (mConfirmListener != null) {
                    mConfirmListener.confirm(mConfirmDialog);
                }
                break;
        }
    }
}