package com.aquila.shared.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aquia.sp.viewer.SPListActivity;
import com.aquia.sp.viewer.utils.CLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button ;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.main_jump_Button);

        button.setOnClickListener(this);


        sharedPreferences = getSharedPreferences("testDemo", MODE_PRIVATE);

        sharedPreferences.edit().putBoolean("boolean_1", true).commit();
        sharedPreferences.edit().putInt("startCount", sharedPreferences.getInt("startCount", 0) +1).commit();
        sharedPreferences.edit().putString("text_Content", "多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死").commit();
        CLog.syso("启动次数:"+sharedPreferences.getInt("startCount", 0) + "");

        for (String key:sharedPreferences.getAll().keySet()){
            CLog.syso("key="+key);
        }
        for (Object obj: sharedPreferences.getAll().values()){
            CLog.syso("value = " + obj.toString());
        }


    }

    @Override
    public void onClick(View v) {
        if (v == button){
            Intent intent = new Intent(this, SPListActivity.class);
            startActivity(intent);
        }
    }
}
