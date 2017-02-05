package com.atguigu.www.beijingnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.atguigu.www.beijingnews.activity.GuideActivity;
import com.atguigu.www.beijingnews.activity.MainActivity;
import com.atguigu.www.beijingnews.utils.CacheUtils;

public class WelcomeActivity extends AppCompatActivity {

    private RelativeLayout activity_welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity_welcome = (RelativeLayout) findViewById(R.id.activity_welcome);
        setAnimation();

    }

    private void setAnimation() {
        //三个动画 旋转，渐变，缩放
        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);

        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(2000);
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);
        sa.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);

        activity_welcome.startAnimation(set);
        //监听动画播放完成
        set.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //Toast.makeText(WelcomeActivity.this, "动画播放完成", Toast.LENGTH_SHORT).show();
           // Intent intent = new Intent(WelcomeActivity.this,GuideActivity.class);
            //startActivity(intent);
            Boolean startMain = CacheUtils.getBoolean(WelcomeActivity.this, "start_main");
            Intent intent =null;
            if(startMain){
                intent =new Intent(WelcomeActivity.this, MainActivity.class);
            }else{
                intent =new Intent(WelcomeActivity.this, GuideActivity.class);
            }
            startActivity(intent);
            finish();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
