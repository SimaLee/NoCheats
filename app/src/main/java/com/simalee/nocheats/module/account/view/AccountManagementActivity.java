package com.simalee.nocheats.module.account.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.common.util.TakePhotoPickPhotoUtils;
import com.simalee.nocheats.common.view.CircleImageView;
import com.simalee.nocheats.module.MainActivity;
import com.simalee.nocheats.module.account.view.AccountManagement.BirthdayPickerFragment;
import com.simalee.nocheats.module.account.view.AccountManagement.ChangePasswdActivity;
import com.simalee.nocheats.module.account.view.AccountManagement.EditPersonalInfoEvent;
import com.simalee.nocheats.module.account.view.AccountManagement.SetNameActivity;
import com.simalee.nocheats.module.account.view.AccountManagement.SetResumeActivity;
import com.simalee.nocheats.module.account.view.AccountManagement.SetSexActivity;
import com.simalee.nocheats.module.account.view.AccountManagement.SetSignatureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

/**
 * Created by Lee Sima on 2017/6/15.
 */

public class AccountManagementActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "AccountManagement";
    private final int CHANGE_HEAD = 1;
    private final int CHANGE_NAME = 2;
    private final int CHANGE_SEX = 3;
    private final int CHANGE_SIGN = 4;

    private ImageView iv_back;
    private ImageView cv_user_head;
    private RelativeLayout rl_set_head;
    private RelativeLayout rl_set_name;
    private RelativeLayout rl_set_sex;
    private RelativeLayout rl_set_birthday;
    private RelativeLayout rl_set_signature;
    private RelativeLayout rl_set_resume;
    private RelativeLayout rl_change_password;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_birthday;
    private TextView tv_signature;
    private TextView tv_login_out;

    private String user_name;
    private String gender;
    private String birthday;
    private String sign;
    private String info;

    private BirthdayPickerFragment mBirthdayPickerFragment;
    private TakePhotoPickPhotoUtils mTakePhotoPickPhotoUtils;
    private File targetFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        EventBus.getDefault().register(this);
        init();
        getUserMsg();
    }
    public void init() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rl_set_head = (RelativeLayout)findViewById(R.id.rl_set_heads);
        rl_set_name = (RelativeLayout) findViewById(R.id.rl_set_name);
        rl_set_sex = (RelativeLayout) findViewById(R.id.rl_set_sex);
        rl_set_birthday = (RelativeLayout) findViewById(R.id.rl_set_birthday);
        rl_set_signature = (RelativeLayout) findViewById(R.id.rl_set_sign);
        rl_set_resume = (RelativeLayout) findViewById(R.id.rl_set_intro);
        rl_change_password = (RelativeLayout) findViewById(R.id.rl_change_passwd);
        cv_user_head = (ImageView) findViewById(R.id.iv_head);
        tv_name = (TextView) findViewById(R.id.tv_set_name);
        tv_birthday = (TextView) findViewById(R.id.tv_set_birthday);
        tv_sex = (TextView) findViewById(R.id.tv_set_sex);
        tv_signature = (TextView) findViewById(R.id.tv_set_sign);
        tv_login_out =(TextView)findViewById(R.id.tv_logout);

        rl_set_head.setOnClickListener(this);
        rl_set_name.setOnClickListener(this);
        rl_set_sex.setOnClickListener(this);
        rl_set_birthday.setOnClickListener(this);
        rl_set_signature.setOnClickListener(this);
        rl_set_resume.setOnClickListener(this);
        rl_change_password.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);

        mTakePhotoPickPhotoUtils = new TakePhotoPickPhotoUtils(AccountManagementActivity.this,500);
    }

    private void getUserMsg(){
        String u_id = PreferenceUtil.getString(AccountManagementActivity.this,PreferenceUtil.USER_ID);
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
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                String userInf = jsonObject.getString("userInf");
                                LogUtils.d(TAG,userInf);
                                if(msg.equals("1")){
                                    if(userInf.equals("null")){
                                        user_name = Constant.UserInfo.USER_NAME;
                                        gender = Constant.UserInfo.GENDER;
                                        sign = Constant.UserInfo.SIGN;
                                        info = Constant.UserInfo.INTRO;
                                        cv_user_head.setImageResource(R.mipmap.testimage);
                                        tv_name.setText(Constant.UserInfo.USER_NAME);
                                        tv_sex.setText("男");
                                        tv_birthday.setText(Constant.UserInfo.BITTHDAY);
                                        tv_signature.setText(sign);
                                    }else{
                                        JSONObject jsonObject1 = new JSONObject(userInf);
                                        user_name = jsonObject1.getString("u_name");
                                        String user_head_url = jsonObject1.getString("head_logo");
                                        gender = jsonObject1.getString("gender");
                                        birthday = jsonObject1.getString("birthday");
                                        sign = jsonObject1.getString("signature");
                                        info = jsonObject1.getString("introduction");
                                        tv_name.setText(user_name);
                                        Glide.with(AccountManagementActivity.this)
                                                .load(Constant.Url.BASE_URL + user_head_url)
                                                .asBitmap()
                                                .into(cv_user_head);
                                        tv_birthday.setText(birthday);
                                        tv_signature.setText(sign);
                                        if(gender.equals("1")){
                                            tv_sex.setText("男");
                                        }else{
                                            tv_sex.setText("女");
                                        }
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
    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(AccountManagementActivity.this)
                .inflate(R.layout.popupwindow_took_photo, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置点击空白地方消失
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置空白地方的背景色
        WindowManager.LayoutParams lp = AccountManagementActivity.this
                .getWindow().getAttributes();
        lp.alpha = 0.6f;
        AccountManagementActivity.this.getWindow().setAttributes(lp);
        //设置popupWindow消失的时候做的事情 即把背景色恢复
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = AccountManagementActivity.this
                        .getWindow().getAttributes();
                lp.alpha = 1f;
                AccountManagementActivity.this.getWindow().setAttributes(lp);
            }
        });
//        mPopWindow.setAnimationStyle(R.style.AnimationPreview);
        final Button bt_took_photo = (Button) contentView.findViewById(R.id.bt_delete_from_group);
        Button bt_cancel = (Button) contentView.findViewById(R.id.bt_cancel);
        Button bt_pick_photo = (Button) contentView.findViewById(R.id.bt_delete_student);
        bt_took_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                startActivityForResult(mTakePhotoPickPhotoUtils.takePhoto(), Constant.CODE.PICK_FROM_CAMERA);
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });
        bt_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                startActivityForResult(mTakePhotoPickPhotoUtils.pickPhoto(), Constant.CODE.PICK_FROM_FILE);

            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(AccountManagementActivity.this)
                .inflate(R.layout.activity_account_management, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    public void showBirthdayDialog() {

        mBirthdayPickerFragment = new BirthdayPickerFragment();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date = dateFormat.parse(birthday);
//            mBirthdayPickerFragment.setSelectedDate(date);
//        } catch (ParseException pe) {
//            pe.printStackTrace();
//        }

        mBirthdayPickerFragment.show(AccountManagementActivity.this.getSupportFragmentManager(), "date");
        mBirthdayPickerFragment.setOnDatePickerClickListener(new BirthdayPickerFragment.OnDatePickerClickListener() {
            @Override
            public void onCancelClick() {
                mBirthdayPickerFragment.dismiss();
            }

            @Override
            public void onAcceptClick(Date date) {
                setBirthday(date);
            }
        });
    }

    public void setBirthday(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String dateString = df.format(date);
        String todayString = df.format(new Date());
        if (dateString.compareTo(todayString) > 0) {
            Toast.makeText(AccountManagementActivity.this, "请选择小于或等于今天的生日", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_EDIT_USER_BIRTHDAY)
                .addParams("u_id", PreferenceUtil.getString(AccountManagementActivity.this,
                        PreferenceUtil.USER_ID))
                .addParams("birthday", dateString)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("ChangeBirthday", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("0")) {
                                Toast.makeText(AccountManagementActivity.this,
                                        "修改成功", Toast.LENGTH_SHORT).show();
                                tv_birthday.setText(dateString);
                            } else {
                                Toast.makeText(AccountManagementActivity.this,
                                        "由于系统原因，修改失败", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                    }
                });
        mBirthdayPickerFragment.dismiss();
    }
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.rl_set_heads:
                showPopUpWindow();
                break;
            case R.id.rl_set_name:
                bundle.putString("name", user_name);
                Intent a = new Intent(AccountManagementActivity.this, SetNameActivity.class);
                a.putExtras(bundle);
                startActivity(a);
                break;
            case R.id.rl_set_sex:
                Intent b = new Intent(AccountManagementActivity.this, SetSexActivity.class);
                bundle.putString("sex", gender);
                b.putExtras(bundle);
                startActivity(b);
                break;
            case R.id.rl_set_birthday:
                showBirthdayDialog();
                break;
            case R.id.rl_set_sign:
                Intent c = new Intent(AccountManagementActivity.this, SetSignatureActivity.class);
                bundle.putString("signature", sign);
                c.putExtras(bundle);
                startActivity(c);
                break;
            case R.id.rl_set_intro:
                Intent d = new Intent(AccountManagementActivity.this, SetResumeActivity.class);
                bundle.putString("info", info);
                d.putExtras(bundle);
                startActivity(d);
                break;
            case R.id.tv_logout:
                PreferenceUtil.setString(AccountManagementActivity.this,PreferenceUtil.IS_LOGIN,"0");
                Intent login = new Intent(AccountManagementActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
                break;
            case R.id.rl_change_passwd:
                Intent e = new Intent(AccountManagementActivity.this, ChangePasswdActivity.class);
                startActivity(e);
                break;
//            case R.id.image_user:
//                if(!head_url.equals("")) {
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putString("photo_url", head_url.replace(Constant.CODE.FOLDER_THUMBNAIL, Constant.CODE.FOLDER_ORIGINAL));
//                    Intent f = new Intent(EditTeacherPersonalMsgActivity.this, ImageViewActivity.class);
//                    f.putExtras(bundle2);
//                    EditTeacherPersonalMsgActivity.this.startActivity(f);
//                }
//                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != AccountManagementActivity.RESULT_OK) {
            LogUtils.d(TAG,"result not ok");
            return;
        }else {
            switch (requestCode) {
                case Constant.CODE.PICK_FROM_FILE:
                    Uri uriSelectInPhone = data.getData();
                    Intent b = mTakePhotoPickPhotoUtils.startPhotoZoom(uriSelectInPhone);
                    startActivityForResult(b, Constant.CODE.ACTION_CROP);
                    break;
                case Constant.CODE.PICK_FROM_CAMERA:
                    LogUtils.i("147258369", 222 + "");
                    Intent a = mTakePhotoPickPhotoUtils.startPhotoZoom(mTakePhotoPickPhotoUtils.getImageCaptureUri());
                    startActivityForResult(a, Constant.CODE.ACTION_CROP);
                    break;
                case Constant.CODE.ACTION_CROP:
                    postFile();
                    break;

            }
        }
    }
    private void postFile() {
        targetFile = new File(mTakePhotoPickPhotoUtils.getImageCropUri().getPath().toString());
        LogUtils.i(TAG, mTakePhotoPickPhotoUtils.getImageCropUri().getPath().toString());
        if (targetFile.exists()) {
            LogUtils.i(TAG, targetFile.getName() + "已存在");
            //文件上传不上去？？？？原因未知
            OkHttpUtils.post()
                    .addParams("u_id",PreferenceUtil.getString(AccountManagementActivity.this,PreferenceUtil.USER_ID))
                    .addFile("file", targetFile.getName(), targetFile)
                    .url(Constant.Url.URL_EDIT_USER_LOGO)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            LogUtils.d(TAG, e.toString());
                            Toast.makeText(AccountManagementActivity.this,"修改头像失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                final JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                if (msg.equals("0")) {
                                    String head_url = jsonObject.getString("id");
                                    cv_user_head.setImageBitmap(BitmapFactory.decodeFile(targetFile.getAbsolutePath()));
                                    EventBus.getDefault().post(new EditPersonalInfoEvent(1,head_url));
                                    LogUtils.d(TAG,"head_url: "+ head_url);
                                }
                            } catch (JSONException je) {
                                LogUtils.d(TAG,je.toString());
                                je.printStackTrace();
                                Toast.makeText(AccountManagementActivity.this,"修改头像失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(AccountManagementActivity.this,"截图失败",Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeUserInfo(EditPersonalInfoEvent event){
        String msg = event.getMsg();
        switch (event.getType()){
            case CHANGE_HEAD:
                break;
            case CHANGE_NAME:
                tv_name.setText(msg);
                break;
            case CHANGE_SEX:
                tv_sex.setText(msg);
                break;
            case CHANGE_SIGN:
                tv_signature.setText(msg);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
