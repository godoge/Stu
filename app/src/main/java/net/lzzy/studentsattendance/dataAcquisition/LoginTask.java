package net.lzzy.studentsattendance.dataAcquisition;

import android.os.AsyncTask;

import com.example.get.GetInfoUtils;
import com.example.get.LoginException;

import net.lzzy.studentsattendance.models.MyInfo;


public class LoginTask extends AsyncTask<Void, String, Boolean> {
    private String id;
    private String pas;

    protected LoginTask(String id, String pas) {
        this.id = id;
        this.pas = pas;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            GetInfoUtils.login(id, pas);
            if (isCancelled())
                return false;
        } catch (LoginException e) {
            e.printStackTrace();
            if (isCancelled())
                return false;
            publishProgress(e.getMessage());
            return false;
        }
        return true;
    }
}
