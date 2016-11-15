package net.lzzy.studentsattendance.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import net.lzzy.studentsattendance.App;
import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.utils.UserConfigInfoUtils;
import net.lzzy.studentsattendance.view.CustomAlertDialog;

import static android.view.View.GONE;

public class SettingActivity extends AppCompatActivity {

    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        app = (App) getApplication();
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_setting_toolbar);
        setSupportActionBar(toolbar);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_setting_back);
        Button btn_exitLogin = (Button) findViewById(R.id.activity_setting_btn_exitLogin);
        if (!Constants.isLogined)
            btn_exitLogin.setVisibility(GONE);
        else {
            btn_exitLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CheckBox cb = new CheckBox(SettingActivity.this);
                    cb.setText("清除账户信息");
                    final CustomAlertDialog custom_dialog = new CustomAlertDialog(SettingActivity.this);
                    custom_dialog.setTitle("提示");
                    custom_dialog.setMessage("确定退出登录?");
                    custom_dialog.addView(cb, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    custom_dialog.setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constants.isLogined = false;
                            Constants.hasInfo = false;
                            custom_dialog.dismiss();
                            if (cb.isChecked())
                                UserConfigInfoUtils.clearLoginInfo(SettingActivity.this);
                            finish();
                        }
                    });
                    custom_dialog.setNegativeButton("取消", null);
                    custom_dialog.show();
                }
            });
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Switch courseSwitch = (Switch) findViewById(R.id.setting_switch_table_mode);
        courseSwitch.setChecked(app.isTableMode);
        courseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserConfigInfoUtils.setCourseTableMode(SettingActivity.this, isChecked);
                app.isTableMode = isChecked;
            }
        });
        Switch refreshSwitch = (Switch) findViewById(R.id.setting_switch_refresh);
        refreshSwitch.setChecked(app.isRefresh);
        refreshSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserConfigInfoUtils.setRefresh(SettingActivity.this, isChecked);
                app.isRefresh = isChecked;
            }
        });

    }


}
