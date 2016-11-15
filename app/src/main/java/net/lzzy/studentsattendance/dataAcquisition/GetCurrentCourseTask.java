package net.lzzy.studentsattendance.dataAcquisition;

import android.os.AsyncTask;

import com.example.get.Course;
import com.example.get.GetInfoUtils;
import com.example.get.LoginException;


public class GetCurrentCourseTask extends AsyncTask<Void, String, Course> {


    private String event;
    private String viewState;

    public GetCurrentCourseTask(String event, String viewState) {

        this.event = event;
        this.viewState = viewState;
    }

    @Override
    protected Course doInBackground(Void... params) {
        try {
            return GetInfoUtils.getCurrentCourse(event, viewState);
        } catch (LoginException e) {
            return null;
        }
    }
}
