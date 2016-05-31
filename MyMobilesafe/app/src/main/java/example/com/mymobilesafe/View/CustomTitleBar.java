package example.com.mymobilesafe.View;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.mymobilesafe.R;
import example.com.mymobilesafe.home.BaseActivity;

/**
 * Created by zyp on 5/7/16.
 */
public class CustomTitleBar {

    private static BaseActivity mActivity;

    /***
     *
     * @param activity   extends BaseActivity
     * @param title  title text
     * @param isShowBack is true to show back button
     */
    public static void getTitleBar(final BaseActivity activity, String title,boolean isShowBack) {
        mActivity = activity;
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(R.layout.custom_title_bar);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_title_bar);
        TextView textView = (TextView) activity.findViewById(R.id.tv_custom_title_bar_title);
        textView.setText(title);
        ImageView titleBackBtn = (ImageView) activity.findViewById(R.id.iv_custom_title_bar_back);
        if(!isShowBack){
            titleBackBtn.setVisibility(View.GONE);
        }
        titleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.back();
            }
        });
    }
}
