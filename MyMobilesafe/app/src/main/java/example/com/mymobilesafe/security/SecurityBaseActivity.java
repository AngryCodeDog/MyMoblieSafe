package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import example.com.mymobilesafe.activity.BaseActivity;
import example.com.mymobilesafe.util.GloabalTools;

/**
 * Created by zyp on 16-2-28.
 */
public abstract class SecurityBaseActivity extends BaseActivity {

    private GestureDetector gestureDetector ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //屏蔽斜着滑动的手势
                if(Math.abs(e2.getRawY() - e1.getRawY()) > 200){
                    //如果y轴距离大于200，则说明是斜着滑的，不处理
                    return true;
                }
                if((e2.getRawX() - e1.getRawX()) > 200){
                    //表示手势是从左滑到右，显示上一个画面
                    showPre();
                    return true;
                }

                if((e1.getRawX() - e2.getRawX()) > 200){
                    //表示手势是从右到左，显示下一个画面
                    showNext();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }


    public abstract void showNext();
    public abstract void showPre();

    public void pre(View v){
        showPre();

    }
    public void next(View v){
        showNext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void saveBindSimCardState(String sim){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GloabalTools.BIND_SIM_CARD_NUMBER,sim);
        editor.commit();
    }
}
