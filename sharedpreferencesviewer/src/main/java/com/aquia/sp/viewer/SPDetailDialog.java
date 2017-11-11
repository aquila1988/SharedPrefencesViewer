package com.aquia.sp.viewer;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aquia.sp.viewer.utils.TypeConst;

/**
 * Created by yulong_wang on 2017/11/11 16:03.
 */

public class SPDetailDialog extends Dialog {

    public SPDetailDialog(@NonNull Context context) {
        super(context);
    }

    private TextView titleTextView;
    private TextView typeTextView;

    private EditText keyEditText;
    private EditText valueEditText;

    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sp_detail_layout);
        initializeViewFromXML();
        setViewClickListeners();
        configDialog();

    }

    private void setViewClickListeners() {
        okButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
        typeTextView.setOnClickListener(onClickListener);
    }

    private void initializeViewFromXML() {
        titleTextView = findViewById(R.id.dialog_sp_title_TextView);
        typeTextView = findViewById(R.id.dialog_sp_type_TextView);

        keyEditText = findViewById(R.id.dialog_sp_key_EditText);
        valueEditText = findViewById(R.id.dialog_sp_value_EditText);

        okButton = findViewById(R.id.dialog_sp_ok_Button);
        cancelButton = findViewById(R.id.dialog_sp_cancel_button);
    }




    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == okButton){
                dismiss();
            }
            else if (v == cancelButton){
                dismiss();
            }
            else if (v == typeTextView){

            }
        }
    };


    protected void configDialog() {
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.gravity = Gravity.CENTER;// 设置重力
//        if (gravity != Gravity.CENTER){
//            getWindow().setWindowAnimations(R.style.bottomDialogWindowAnim);
//        }
        getWindow().setAttributes(wl);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

    }


    public void initData(String key, Object obj) {
        keyEditText.setText(key);
        valueEditText.setText(obj.toString());
        String type = TypeConst.getObjectType(obj);
        typeTextView.setText(type);


    }
}
