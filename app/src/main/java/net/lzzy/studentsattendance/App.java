package net.lzzy.studentsattendance;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.lzzy.studentsattendance.utils.UserConfigInfoUtils;

/**
 * Created by Administrator on 2016/11/15.
 */

public class App extends Application {
    public boolean isRefresh;
    public boolean isTableMode;
    private ConnectivityManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    public void initConfig() {
        isRefresh = UserConfigInfoUtils.isRefresh(getApplicationContext());
        isTableMode = UserConfigInfoUtils.isCourseTableMode(getApplicationContext());
    }

    public boolean isAvailable() {
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && (manager.getActiveNetworkInfo().isConnected() || manager.getActiveNetworkInfo().isConnectedOrConnecting());
    }


}
