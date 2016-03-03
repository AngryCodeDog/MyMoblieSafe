package example.com.mymobilesafe.security;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import example.com.mymobilesafe.R;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyp on 16-3-2.
 */
public class SelectContactActivity extends Activity {
    private ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_contact_layout);
        initData();
        initView();

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.select_contact_listview);
        listView.setAdapter(new SimpleAdapter(this,getContactsData(),R.layout.contacts_layout_lv_item
        ,new String[]{"name","number"},new int[]{R.id.tv_name,R.id.tv_number}));
    }

    private void initData() {
    }

    private ArrayList<Map<String,String>> getContactsData(){
        ArrayList<Map<String,String>> contactsData = new ArrayList<Map<String,String>>();
        ContentResolver contentResolver = getContentResolver();
        String[] str = {Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
                Phone.PHOTO_ID};
        Cursor cur = contentResolver.query(Phone.CONTENT_URI, str, null, null, null);
        System.out.println("***************************"+Phone.CONTENT_URI);
        while (cur.moveToNext()){
            Map<String,String> data = new HashMap<String,String>();
            data.put("name",cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME)));
            data.put("number",cur.getString(cur.getColumnIndex(Phone.NUMBER)));
            contactsData.add(data);
        }
        cur.close();
        return contactsData;
    }
}
