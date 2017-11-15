package com.aquia.sp.viewer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aquia.sp.viewer.utils.CustomRecyclerView;
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
//    private TextView typeTextView;
    private CustomRecyclerView TypeRecyclerView;

    private EditText keyEditText;
    private EditText valueEditText;

    private Button okButton;
    private Button cancelButton;


    private static final int CHANGE_TYPE_KEY = 1;
    private static final int CHANGE_TYPE_TYPE = 2;
    private String currentType = TypeConst.TYPE_STRING;
    private String lastType, lastKey;
    private boolean isCreate = false;

    private TypeAdapter typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sp_detail_layout);
        initializeViewFromXML();
        setViewClickListeners();
        configDialog();

        typeAdapter = new TypeAdapter(getContext());
        TypeRecyclerView.setAdapter(typeAdapter);
//        typeTextView.setText(currentType);
    }

    public void setTitleText(String text){
        titleTextView.setText(text);
    }

    private void setViewClickListeners() {
        okButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
//        typeTextView.setOnClickListener(onClickListener);
    }

    private void initializeViewFromXML() {
        titleTextView = findViewById(R.id.dialog_sp_title_TextView);
//        typeTextView = findViewById(R.id.dialog_sp_type_TextView);
        TypeRecyclerView = findViewById(R.id.dialog_sp_type_RecyclerView);

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
                else if (!isCreate &&!currentType.equals(typeAdapter.getCurrentType())){
                    showKeyChangedDialog(CHANGE_TYPE_TYPE);
                }else {
                    saveNewData();
                }
            }
            else if (v == cancelButton){
                dismiss();
            }
        }
    };

    private void saveNewData() {
        try {
            putDataToSp(sharedPreferences,typeAdapter.getCurrentType(), keyEditText.getText().toString(),
                    valueEditText.getText().toString());

            if (onDataUpdateListener != null){
                onDataUpdateListener.onDataUIUpdate();
            }
            dismiss();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "数据存储异常，请检查您的输入是否有误", Toast.LENGTH_SHORT).show();
        }

    }

    public void putDataToSp(SharedPreferences sp, String type, String key, String v) throws Exception{
        if (type.equals(TypeConst.TYPE_STRING)){
            sp.edit().putString(key, v).commit();
        }
        else if (type.equals(TypeConst.TYPE_INT)){
            int value = Integer.parseInt(v);
            sp.edit().putInt(key, value).commit();
        }
        else if (type.equals(TypeConst.TYPE_LONG)){
            long value = Long.parseLong(v);
            sp.edit().putLong(key, value).commit();
        }
        else if (type.equals(TypeConst.TYPE_FLOAT)){
            float value = Float.parseFloat(v);
            sp.edit().putFloat(key, value).commit();
        }
        else if (type.equals(TypeConst.TYPE_BOOLEAN)){
            boolean value = Boolean.valueOf(v);
            sp.edit().putBoolean(key, value).commit();
        }
    }


    private void showKeyChangedDialog(int changeType){
        String title = "";
        if (changeType == CHANGE_TYPE_KEY){
            title = String.format("您将【%s】修改为【%s】Key值，之前的Key值将会被删除，确定这样操作吗？",
                    lastKey, keyEditText.getText().toString());
        }else if (changeType == CHANGE_TYPE_TYPE){
            title = String.format("您将【%s】型改为了【%s】型，之前的内容将会被改变，确定这样操作吗？", lastType,
                    typeAdapter.getCurrentType());
        }

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setMessage(title)
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit().remove(lastKey);
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
        typeAdapter.setCurrentType(type);

        currentType = lastType = type;
    }



    private static class TypeAdapter extends RecyclerView.Adapter<TypeViewHolder>{
        private LayoutInflater layoutInflater;
        private String currentType;

        private int defaultColor, selecColor;
        public TypeAdapter(Context context){
            layoutInflater = LayoutInflater.from(context);
            defaultColor = context.getResources().getColor(R.color.color_373737);
            selecColor = context.getResources().getColor(R.color.color_c91566);
        }


        @Override
        public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TypeViewHolder(layoutInflater.inflate(R.layout.item_type_layout, parent,false));
        }

        public void setCurrentType(String type){
            currentType = type;
            notifyDataSetChanged();
        }

        public String getCurrentType() {
            return currentType;
        }

        @Override
        public void onBindViewHolder(TypeViewHolder holder, int position) {
            final String text = TypeConst.getTypeMap().get(position);
            holder.contentTextView.setText(text);

            if (TextUtils.equals(text, currentType)){
                holder.contentTextView.setTextColor(selecColor);
            }else {
                holder.contentTextView.setTextColor(defaultColor);
            }

            holder.contentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentType = text;
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return TypeConst.getTypeMap().size();
        }
    }


    private static class TypeViewHolder extends RecyclerView.ViewHolder{
        public TextView contentTextView;
        public TypeViewHolder(View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.item_type_content_TextView);
        }


    }

}
