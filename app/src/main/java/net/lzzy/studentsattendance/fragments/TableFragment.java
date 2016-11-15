package net.lzzy.studentsattendance.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.get.CourseUtils;
import com.example.tabletimeview.OnTableClassClickListener;
import com.example.tabletimeview.TableTimeView;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.constants.DbConstance;
import net.lzzy.studentsattendance.models.LocalCourse;
import net.lzzy.studentsattendance.utils.CourseFactory;
import net.lzzy.studentsattendance.utils.LocalCourseUtils;

import java.util.List;

public class TableFragment extends Fragment implements OnTableClassClickListener {

    private TableTimeView tableTimeView;
    private LocalCourse localCourse;

    public static TableFragment newInstance(String WEEk) {//静态工厂方泿
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(DbConstance.COL_COURSE_RAW, WEEk);
        fragment.setArguments(args);
        return fragment;
    }

    public void showCourse(LocalCourse course) {

//        String[][] aWeekRawCourse = LocalCourseUtils.getCourseData(localCourse.getRaw());
//        tableTimeView.setData(LocalCourseUtils.getDisplayFormat(aWeekRawCourse));
//        weekView.setClickPosition(week - 1);
//
//        LocalCourse localCourse = CourseFactory.getInstance(getContext()).getByWeek(getArguments().getString(DbConstance.COL_COURSE_WEEK));
//        if (localCourse == null)
//            return;


        String[][] aWeekRawCourse = LocalCourseUtils.getAWeekRaw(course.getRaw());
        tableTimeView.setData(LocalCourseUtils.getDisplayFormat(aWeekRawCourse));

    }


//    private void heightLightCurrentWeek() {
//        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        TextView tv;
//        if (w == 1)
//            for (int i = 1; i <= 6; i++) {
//                tv = tableTimeView.getCourseTextView(7, i);
//                tv.setBackgroundColor(getContext().getResources().getColor(R.color.colorTable));
//            }
//        else
//            for (int i = 1; i <= 6; i++) {
//                tv = tableTimeView.getCourseTextView(w - 1, i);
//                tv.setBackgroundColor(getContext().getResources().getColor(R.color.colorTable));
//            }
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        FrameLayout f = (FrameLayout) view.findViewById(R.id.child_layout);
        tableTimeView = new TableTimeView(getActivity());
        tableTimeView.setTableClassOnCLickListener(this);
        f.addView(tableTimeView);
        String week = getArguments().getString(DbConstance.COL_COURSE_RAW);
        if (week == null)
            return view;
        localCourse = CourseFactory.getInstance(getContext()).getCourse(week);
        if (localCourse != null) {
            tableTimeView.setData(LocalCourseUtils.getDisplayFormat(LocalCourseUtils.getAWeekRaw(localCourse.getRaw())));
        }
        return view;
    }

    public String getDetailDisplay(String aTimeRaw) {
        List<String> list = CourseUtils.getNodeNum(aTimeRaw);
        if (list.size() == 0)
            return "无课程";
        StringBuilder builder = new StringBuilder();
        for (String aTimeRawInfo : list)
            builder.append("时间：").append(CourseUtils.getCourseTime(aTimeRawInfo)).append("\n")
                    .append("节数：").append(CourseUtils.getCourseNod(aTimeRawInfo)).append("\n")
                    .append("教室：").append(CourseUtils.getCourseClassroom(aTimeRawInfo)).append("\n")
                    .append("课程名称：").append(CourseUtils.getCourseName(aTimeRawInfo)).append("\n")
                    .append("老师：").append(CourseUtils.getCourseTeacher(aTimeRawInfo)).append("\n\n");

        return builder.toString();
    }


    @Override
    public void onClick(View view, int week, int aClass) {
        String aTimeCourse = LocalCourseUtils.getAWeekRaw(localCourse.getRaw())[week - 1][aClass - 1];
        new AlertDialog.Builder(getContext()).setTitle("课程详情")
                .setMessage(getDetailDisplay(aTimeCourse))
                .show();


    }
}

