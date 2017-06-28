package com.simalee.nocheats.module.experiencesquare.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseFragment;

/**
 * Created by Lee Sima on 2017/6/9.
 */

public class FirstFragment extends BaseFragment {

    private static final String KEY_TEXT = "key";

    private String text = "nothing";
    public static Fragment newInstance(String text){
        Fragment instance = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TEXT,text);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            text = getArguments().getString(KEY_TEXT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout,null);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
