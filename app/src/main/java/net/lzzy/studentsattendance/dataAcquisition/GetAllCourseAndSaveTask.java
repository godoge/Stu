package net.lzzy.studentsattendance.dataAcquisition;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.example.get.Course;
import com.example.get.GetInfoUtils;
import com.example.get.LoginException;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.models.LocalCourse;
import net.lzzy.studentsattendance.utils.CourseFactory;
import net.lzzy.studentsattendance.utils.PromptlyToast;
import net.lzzy.studentsattendance.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import static net.lzzy.studentsattendance.models.LocalCourse.getLocalCourse;


public class GetAllCourseAndSaveTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private CustomDialog progressDialog;
    private static final int MAX_WEEK = 20;
    private static final int MIN_WEEK = 1;
    private int currentWeek = 0;

    public GetAllCourseAndSaveTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressDialog.setTitle((int) (currentWeek / 20f * 100) + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new CustomDialog(context, R.style.custom_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
                PromptlyToast.getInstance(context).show("取消获取");
            }
        });
        progressDialog.setTitle("获取中");
        progressDialog.show();
    }


    private List<LocalCourse> savePreCourse(Course syllabus, int min) throws LoginException {
        List<LocalCourse> preCourses = new ArrayList<>();
        Course current = syllabus;
        int week = Integer.valueOf(syllabus.getWeek());
        for (int i = week - 1; !isCancelled() && i >= min; i--) {
            publishProgress();
            currentWeek++;
            current = GetInfoUtils.getAnotherWeek(current, GetInfoUtils.DAY_PREVIOUS);
            LocalCourse localCourse = getLocalCourse(current);
            preCourses.add(localCourse);

        }
        return preCourses;

    }

    private List<LocalCourse> saveAfterCourse(Course syllabus, int max) throws LoginException {
        List<LocalCourse> afterCourses = new ArrayList<>();
        Course currentCourse = syllabus;
        int week = Integer.valueOf(syllabus.getWeek());
        for (int i = week + 1; !isCancelled() && i <= max; i++) {
            publishProgress();
            currentWeek++;
            currentCourse = GetInfoUtils.getAnotherWeek(currentCourse, GetInfoUtils.DAY_NEXT);
            LocalCourse localCourse = getLocalCourse(currentCourse);
            afterCourses.add(localCourse);

        }

        return afterCourses;
    }


    private void saveAllCourse(Course course) throws LoginException {
        List<LocalCourse> localCourses = new ArrayList<>();
        localCourses.add(getLocalCourse(course));
        localCourses.addAll(saveAfterCourse(course, MAX_WEEK));
        localCourses.addAll(savePreCourse(course, MIN_WEEK));
        CourseFactory.getInstance(context).saveAllLocalCourse(localCourses);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Course syllabus = GetInfoUtils.getNextWeek();
            LocalCourse localCourse = getLocalCourse(syllabus);
            CourseFactory.getInstance(context).addCourse(localCourse);
            saveAllCourse(syllabus);
            return true;
        } catch (LoginException e) {
            e.printStackTrace();
            return false;
        }

    }
}
