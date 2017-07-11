package com.simalee.nocheats.module.experiencesquare.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseFragment;
import com.simalee.nocheats.common.base.OnFabClickListener;
import com.simalee.nocheats.module.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/14.
 */

public class ExperienceSquareFragment extends BaseFragment implements OnFabClickListener{

    private static final String TAG = ExperienceSquareFragment.class.getSimpleName();

    private Context mContext;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    /**
     * 当前所在的pageindex 用于设置newpost的 spinner
     */
    int currentType = 0;

    public static ExperienceSquareFragment newInstance(){
        Bundle args = new Bundle();
        ExperienceSquareFragment fragment = new ExperienceSquareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO 加载数据
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience_square,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    private void initViews(View view){

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout_1);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        String[] titles = {"首页","金融骗术","电信骗术","网络骗术","街头骗术","其他骗术"};

        for (int i = 0;i < titles.length;i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles[i]));
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG,"选择了：position: "+tab.getPosition());
                currentType = tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        List<Fragment> fragments = new ArrayList<>(titles.length);
        for (int i = 0;i<titles.length;i++){
            fragments.add(PostFragment.newInstance(i));
        }

        //viewpager 是 fragment内部的控件时 绑定的是 getChildFragmentManager()
        //在activity内部控件时才传递getSupportFragmentManager();
        mViewPager.setAdapter(new PageAdapter(getChildFragmentManager(),fragments,
                Arrays.asList(titles)));

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onFloatingActionButtonClick(View view) {
        Intent intent = new Intent(getContext(),NewPostActivity.class);
        intent.putExtra("type",currentType);
        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    class PageAdapter extends FragmentPagerAdapter {
        List<String> mTitles ;
        List<Fragment> mFragments;

        public PageAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles){
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }


    private void showToastShort(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }


}
