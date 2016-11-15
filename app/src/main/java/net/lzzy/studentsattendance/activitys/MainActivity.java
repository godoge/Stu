package net.lzzy.studentsattendance.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64DataException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.constants.Constants;
import net.lzzy.studentsattendance.dataAcquisition.LoginTask;
import net.lzzy.studentsattendance.encrypt.PasswordUtils;
import net.lzzy.studentsattendance.fragments.CourseFragment;
import net.lzzy.studentsattendance.fragments.MeFragment;
import net.lzzy.studentsattendance.models.MenuTag;
import net.lzzy.studentsattendance.utils.PromptlyToast;
import net.lzzy.studentsattendance.utils.UserConfigInfoUtils;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_me;
    private ImageView iv_course;
    private TextView tv_me;
    private TextView tv_course;
    private LinearLayout layout_me;
    private LinearLayout layout_course;
    private FragmentManager fManager;
    private View[] menuViews = new View[2];
    private boolean mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initialize();
        mode = UserConfigInfoUtils.isCourseTableMode(this);

        if (UserConfigInfoUtils.isAutoLogin(this))
            try {
                login(UserConfigInfoUtils.getID(this), PasswordUtils.unlock(UserConfigInfoUtils.getPassword(this)));

            } catch (UnsupportedEncodingException | Base64DataException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mode != UserConfigInfoUtils.isCourseTableMode(this)) {
            getSupportFragmentManager().getFragments().clear();
            recreate();
        }
    }

    public interface OnMenuClick {
        void onMenuClick();
    }

    private void login(String id, String psd) {
        new LoginTask(id, psd) {
            @Override
            protected void onPostExecute(Boolean isLogin) {
                super.onPostExecute(isLogin);
                if (!isLogin) {
                    PromptlyToast.getInstance(MainActivity.this).show("自动登录失败");
                    UserConfigInfoUtils.setSaveAutoLoginState(MainActivity.this, false);
                    return;
                }
                Constants.isLogined = true;
            }
        }.execute();

    }


    private void initViews() {
        iv_me = (ImageView) findViewById(R.id.custom_tab_bar_iv_me);
        iv_course = (ImageView) findViewById(R.id.custom_tab_bar_iv_course);
        tv_me = (TextView) findViewById(R.id.custom_tab_bar_tv_me);
        tv_course = (TextView) findViewById(R.id.custom_tab_bar_tv_course);
        layout_me = (LinearLayout) findViewById(R.id.custom_tab_bar_layout_me);
        layout_course = (LinearLayout) findViewById(R.id.custom_tab_bar_layout_course);
        menuViews[0] = layout_course;
        menuViews[1] = layout_me;
        layout_me.setOnClickListener(this);
        layout_course.setOnClickListener(this);
        iv_course.setImageResource(R.drawable.ic_tab_bar_course_normal);
        tv_course.setTextColor(getResources().getColor(R.color.colorTabBarTextNormal));
        iv_me.setImageResource(R.drawable.ic_tab_bar_me_normal);
        tv_me.setTextColor(getResources().getColor(R.color.colorTabBarTextNormal));
    }


    private void initialize() {

        fManager = getSupportFragmentManager();
        FragmentTransaction trans = fManager.beginTransaction();
        CourseFragment fragCou = new CourseFragment();
        MeFragment fragMe = new MeFragment();
        MenuTag meTag = new MenuTag();
        meTag.setFragment(fragMe);
        meTag.setIv(iv_me);
        meTag.setTv(tv_me);
        meTag.setNormalImgRes(R.drawable.ic_tab_bar_me_normal);
        meTag.setPressedImgRes(R.drawable.ic_tab_bar_me_pressed);
        meTag.setNormalTvRes(getResources().getColor(R.color.colorTabBarTextNormal));
        meTag.setPressedTvRes(getResources().getColor(R.color.colorTabBarTextPressed));

        MenuTag couTag = new MenuTag();
        couTag.setFragment(fragCou);
        iv_course.setImageResource(R.drawable.ic_tab_bar_course_pressed);
        tv_course.setTextColor(getResources().getColor(R.color.colorTabBarTextPressed));
        couTag.setIv(iv_course);
        couTag.setTv(tv_course);
        couTag.setNormalImgRes(R.drawable.ic_tab_bar_course_normal);
        couTag.setPressedImgRes(R.drawable.ic_tab_bar_course_pressed);
        couTag.setNormalTvRes(getResources().getColor(R.color.colorTabBarTextNormal));
        couTag.setPressedTvRes(getResources().getColor(R.color.colorTabBarTextPressed));
        layout_me.setTag(meTag);
        layout_course.setTag(couTag);
        trans.hide(fragMe);
        trans.add(R.id.activity_main_fragment_content, fragCou);
        trans.add(R.id.activity_main_fragment_content, fragMe);
        trans.commit();

    }

    @Override
    public void onClick(View v) {
        selectMenu(v);
    }

    private void selectMenu(View selectedView) {
        MenuTag pressedTag = (MenuTag) selectedView.getTag();
        FragmentTransaction trans = fManager.beginTransaction();
        for (View v : menuViews) {
            MenuTag tag = (MenuTag) v.getTag();
            if (v.equals(selectedView)) {
                ((OnMenuClick) pressedTag.getFragment()).onMenuClick();
                trans.show(pressedTag.getFragment());
                pressedTag.getIv().setImageResource(pressedTag.getPressedImgRes());
                pressedTag.getTv().setTextColor(pressedTag.getPressedTvRes());
            } else {
                trans.hide(tag.getFragment());
                tag.getIv().setImageResource(tag.getNormalImgRes());
                tag.getTv().setTextColor(tag.getNormalTvRes());
            }
        }
        trans.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
