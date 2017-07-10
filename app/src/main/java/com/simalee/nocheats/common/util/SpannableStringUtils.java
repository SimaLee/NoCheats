package com.simalee.nocheats.common.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;

/**
 * Created by Lee Sima on 2017/6/27.
 */

public class SpannableStringUtils {

    private SpannableStringUtils(){
        throw new UnsupportedOperationException("SpannableStringUtils can't be instantiated");
    }

    public static SpannableStringBuilder getReplySpannableString(final ReplyReplyEntity entity){

        SpannableStringBuilder resBuilder = new SpannableStringBuilder();

        SpannableString subjectName = new SpannableString(entity.getSubjectName()+" ");
        subjectName.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                LogUtils.d("PostDetail",entity.getSubjectName() + "is clicked");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        },0,entity.getSubjectName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resBuilder.append(subjectName);

        //是否楼主
        if (entity.isHost()){
            SpannableString isHost = new SpannableString("楼主 ");
            isHost.setSpan(new ForegroundColorSpan(Color.BLUE),0,2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            resBuilder.append(isHost);
        }

        if (entity.getObjectName() != null && !("null".equals(entity.getObjectName()))){
            // 回复 某某某 :
            SpannableString replyToObjectName = new SpannableString(" 回复 "+ entity.getObjectName()+" :");
            resBuilder.append(replyToObjectName);
        }else{
            resBuilder.append(" : ");
        }

        resBuilder.append(entity.getContent());

        return resBuilder;
    }


}
