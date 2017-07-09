package com.simalee.nocheats.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.base.BaseFragment;
import com.simalee.nocheats.common.base.OnFabClickListener;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.common.view.CircleImageView;
import com.simalee.nocheats.module.account.view.AccountManagement.EditPersonalInfoEvent;
import com.simalee.nocheats.module.account.view.AccountManagementActivity;
import com.simalee.nocheats.module.experiencesquare.view.PreviousPostActivity;
import com.simalee.nocheats.module.topicsquare.view.PreviousTopicActivity;
import com.simalee.nocheats.module.assistant.AssistantActivity;
import com.simalee.nocheats.module.experiencesquare.view.ExperienceSquareFragment;
import com.simalee.nocheats.module.topicsquare.view.TopicSquareFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final int CHANGE_HEAD = 1;
    private final int CHANGE_NAME = 2;
    private final int FINISH = 6;

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    Toolbar mToolbar;
    private CircleImageView cv_user_head;
    private TextView tv_user_name;

    private View rl_user;

    /**
     * 用于记录当前是哪个fragment 响应fab的操作
     */
    private BaseFragment currentFragment;

    //按两次返回键退出程序
    private static final int TIME_INTERVAL = 2000;//2000ms内重复点击返回键退出
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initOkhttp();
        EventBus.getDefault().register(this);
        initViews();
        getUserMsg();
    }

    private void initOkhttp(){
        //配置OkHttp
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d(TAG,"fab onClick");
                if (currentFragment != null && currentFragment instanceof ExperienceSquareFragment){
                    ((OnFabClickListener) currentFragment).onFloatingActionButtonClick(view);

                }else if (currentFragment != null && currentFragment instanceof TopicSquareFragment){
                    ((OnFabClickListener) currentFragment).onFloatingActionButtonClick(view);
                }

            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rl_user =  navigationView.getHeaderView(0);
        rl_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"点击了用户头像",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AccountManagementActivity.class);
                drawer.closeDrawers();
                startActivity(intent);
            }
        });
        cv_user_head = (CircleImageView)rl_user.findViewById(R.id.image_user);
        tv_user_name = (TextView)rl_user.findViewById(R.id.tv_user_name);

        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        ExperienceSquareFragment fragment = ExperienceSquareFragment.newInstance();
        currentFragment = fragment;
        mTransaction.replace(R.id.content,fragment,"experiencesqurae");
        mTransaction.commit();
    }

    private void getUserMsg(){
        String u_id = PreferenceUtil.getString(MainActivity.this,PreferenceUtil.USER_ID);
        LogUtils.d(TAG,u_id);
        if(!u_id.equals("")) {
            OkHttpUtils.post()
                    .url(Constant.Url.URL_GET_USER_INFORMATION)
                    .addParams("u_id",u_id)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            LogUtils.e(TAG, "couldn't not get user info:" + e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtils.d(TAG, "user_info: " + response);
                            //Log.i(TAG, response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                String userInf = jsonObject.getString("userInf");
                                LogUtils.d(TAG,userInf);
                                if(msg.equals("0")){
                                    if(userInf.equals("null")){
                                        cv_user_head.setImageResource(R.mipmap.testimage);
                                        tv_user_name.setText(Constant.UserInfo.USER_NAME);
                                    }else{
                                        JSONObject jsonObject1 = new JSONObject(userInf);
                                        Log.i(TAG, jsonObject1.getString("u_name"));
                                        String user_name = jsonObject1.getString("u_name");
                                        String user_head_url = jsonObject1.getString("head_logo");
                                        tv_user_name.setText(user_name);
                                        Glide.with(MainActivity.this)
                                                .load(Constant.Url.BASE_URL + user_head_url)
                                                .asBitmap()
                                                .into(cv_user_head);
                                    }
                                }else{
                                    LogUtils.d(TAG,"获取个人信息失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }
    }
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }else{
                Toast.makeText(getBaseContext(),"再按一次退出应用",Toast.LENGTH_SHORT).show();
                mBackPressed = System.currentTimeMillis();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_homepage) {
            // Handle the camera action
            Toast.makeText(MainActivity.this,"主页按钮",Toast.LENGTH_SHORT).show();
            ExperienceSquareFragment fragment = ExperienceSquareFragment.newInstance();

            currentFragment = fragment;

            fragmentTransaction.replace(R.id.content,fragment,"experiencesquare");
            mToolbar.setTitle("经历广场");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_topic_square) {

            Toast.makeText(MainActivity.this,"点击了话题广场",Toast.LENGTH_SHORT).show();
            TopicSquareFragment fragment = TopicSquareFragment.newInstance();

            currentFragment = fragment;

            fragmentTransaction.replace(R.id.content,fragment,"topicsquare");
            mToolbar.setTitle("话题广场");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_your_post) {

            Toast.makeText(MainActivity.this,"点击了你的帖子",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,PreviousPostActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_your_topic) {

            Toast.makeText(MainActivity.this,"点击了你的主题",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,PreviousTopicActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_assistant) {

            Intent intent = new Intent(MainActivity.this,AssistantActivity.class);
            startActivity(intent);

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeInfo(EditPersonalInfoEvent event){
        int type =event.getType();
        String msg = event.getMsg();
        switch (type){
            case CHANGE_HEAD:
                Log.d(TAG, "receive url " + msg);
                Glide.with(MainActivity.this)
                        .load(Constant.Url.BASE_URL + msg)
                        .asBitmap()
                        .into(cv_user_head);
                break;
            case CHANGE_NAME:
                tv_user_name.setText(msg);
                break;
            case FINISH:
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
