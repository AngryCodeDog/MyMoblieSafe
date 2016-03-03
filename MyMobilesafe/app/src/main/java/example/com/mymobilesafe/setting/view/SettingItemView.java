package example.com.mymobilesafe.setting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import example.com.mymobilesafe.R;

/**
 * Created by zyp on 16-1-10.
 */
public class SettingItemView extends RelativeLayout{

    private TextView tv_title;
    private TextView tv_descripe;
    private CheckBox cb_update;

    private String title;
    private String desctiption_on;
    private String desctiption_off;

    private void initView(Context context) {
        View.inflate(context, R.layout.setting_layout_item,this);
        tv_title = (TextView) findViewById(R.id.setting_item_tv_update);
        tv_descripe = (TextView) findViewById(R.id.setting_item_tv_decrip);
        cb_update = (CheckBox) findViewById(R.id.setting_item_cb);

        tv_title.setText(title);
    }



    public SettingItemView(Context context) {
        super(context);
        initView(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","text_title");
        desctiption_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","description_on");
        desctiption_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","description_off");
        initView(context);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @SuppressLint("NewApi")
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }


    public boolean isChecked(){
        return cb_update.isChecked();
    }

    public void setOncheck(){
        tv_descripe.setText(desctiption_on);
        cb_update.setChecked(true);
    }
    public void setOffCheck(){
        tv_descripe.setText(desctiption_off);
        cb_update.setChecked(false);
    }


}
