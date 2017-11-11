package com.aquia.sp.viewer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aquia.sp.viewer.utils.StaticMethodsUtility;
import com.aquia.sp.viewer.utils.TypeConst;

/**
 * Created by yulong_wang on 2017/11/11 16:03.
 */

public class SPDetailDialog extends Dialog {

    public SPDetailDialog(@NonNull Context context, SharedPreferences sharedPreferences) {
        super(context);
        this.sharedPreferences = sharedPreferences;
    }
    private SharedPreferences sharedPreferences;
    private TextView titleTextView;
    private TextView typeTextView;

    private EditText keyEditText;
    private EditText valueEditText;

    private Button okButton;
    private Button cancelButton;


    private static final int CHANGE_TYPE_KEY = 1;
    private static final int CHANGE_TYPE_TYPE = 2;
    private String currentType = TypeConst.TYPE_STRING;
    private String lastType, lastKey;
    private boolean isCreate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sp_detail_layout);
        initializeViewFromXML();
        setViewClickListeners();
        configDialog();
        typeTextView.setText(currentType);
    }

    public void setTitleText(String text){
        titleTextView.setText(text);
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

    public void setIsCreate(boolean create) {
        isCreate = create;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == okButton){
                if (!isCreate && !keyEditText.getText().toString().equals(lastKey)){
                    showKeyChangedDialog(CHANGE_TYPE_KEY);
                }
                else if (!isCreate &&!currentType.equals(typeTextView.getText().toString())){
                    showKeyChangedDialog(CHANGE_TYPE_TYPE);
                }else {
                    saveNewData();
                }
            }
            else if (v == cancelButton){
                dismiss();
            }
            else if (v == typeTextView){

            }
        }
    };

    private void saveNewData() {
        StaticMethodsUtility.putDataToSp(sharedPreferences, currentType,
                keyEditText.getText().toString(), valueEditText.getText().toString());

        if (onDataUpdateListener != null){
            onDataUpdateListener.onDataUIUpdate();
        }
        dismiss();
    }


    private void showKeyChangedDialog(int changeType){
        String title = "";
        if (changeType == CHANGE_TYPE_KEY){
            title = String.format("您将【%s】修改为【%s】Key值，之前的Key值将会被删除，确定这样操作吗？",
                    lastKey, keyEditText.getText().toString());
        }else if (changeType == CHANGE_TYPE_TYPE){
            title = "您已经修改当前存储的类型，之前的内容将会被改变？";
        }

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setMessage(title)
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit().remove(lastKey).apply();
                        saveNewData();
                    }
                }).setNegativeButton("取消",null)
                .create();
        alertDialog.show();
    }




    protected void configDialog() {
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.gravity = Gravity.CENTER;// 设置重力
        getWindow().setAttributes(wl);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

    }

    OnDataUpdateListener onDataUpdateListener;

    public void setOnDataUpdateListener(OnDataUpdateListener onDataUpdateListener) {
        this.onDataUpdateListener = onDataUpdateListener;
    }



    public void initData(String key, Object obj) {
        lastKey = key;

        keyEditText.setText(key);
        valueEditText.setText(obj.toString());
        String type = TypeConst.getObjectType(obj);
        typeTextView.setText(type);

        currentType = lastType = type;


    }
}
