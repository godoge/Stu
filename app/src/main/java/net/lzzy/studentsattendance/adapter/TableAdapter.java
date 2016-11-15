package net.lzzy.studentsattendance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.lzzy.studentsattendance.fragments.TableFragment;
import net.lzzy.studentsattendance.models.LocalCourse;

import java.util.List;


public class TableAdapter extends FragmentStatePagerAdapter {
    private List<LocalCourse> datas;

    public TableAdapter(FragmentManager manager, List<LocalCourse> datas) {
        super(manager);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        if (datas.size() == 0)
            return new TableFragment();
        return TableFragment.newInstance(position + 1 + "");
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof TableFragment)
            return FragmentStatePagerAdapter.POSITION_NONE;
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
