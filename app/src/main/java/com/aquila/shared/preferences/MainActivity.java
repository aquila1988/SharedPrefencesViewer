package com.aquila.shared.preferences;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aquila.sp.viewer.SPFileListActivity;
import com.aquila.sp.viewer.utils.CLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button gotoSPButton;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoSPButton = findViewById(R.id.main_jump_Button);

        gotoSPButton.setOnClickListener(this);


        sharedPreferences = getSharedPreferences("testDemo", MODE_PRIVATE);

        sharedPreferences.edit().putBoolean("boolean_1", true).commit();
        sharedPreferences.edit().putInt("startCount", sharedPreferences.getInt("startCount", 0) +1).commit();
        sharedPreferences.edit().putString("text_Content", "多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死多撒后的卡上看撒反对哈是否会啊数据库恢复就卡死").commit();

        SharedPreferences sp = getSharedPreferences("file2", MODE_PRIVATE);
        sp.edit().putString("test", "我的测试文件2").commit();

        SharedPreferences sp2 = getSharedPreferences("大萨达", MODE_PRIVATE);
        sp2.edit().putString("test122", "我的测试文件2").commit();


        CLog.debug("启动次数:"+sharedPreferences.getInt("startCount", 0) + "");

        for (String key:sharedPreferences.getAll().keySet()){
            CLog.debug("key="+key);
        }
        for (Object obj: sharedPreferences.getAll().values()){
            CLog.debug("value = " + obj.toString());
        }


    }

    @Override
    public void onClick(View v) {
        if (v == gotoSPButton){
            // 这个设置是在release下的APP也可以跳转过去
            SPFileListActivity.setIsReleaseCanJump(true);
            // 这里的this参数就是Activity自己，如果是在
            SPFileListActivity.gotoSPFileListActivity(this);
        }
    }
}
