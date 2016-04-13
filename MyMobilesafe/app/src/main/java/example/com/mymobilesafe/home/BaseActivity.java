package example.com.mymobilesafe.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import example.com.mymobilesafe.util.GloabalTools;

/**
 * Created by zyp on 3/26/16.
 * 所有的Activity的基类
 */
public class BaseActivity extends Activity {
    public SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences(GloabalTools.CONFIG,MODE_PRIVATE);
        super.onCreate(savedInstanceState);
    }
}
