package example.com.mymobilesafe.util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;

import java.io.Serializable;

/**
 * Created by zyp on 16-1-4.
 */
public abstract class Download<Params, Result> {

    private static final int POST_RESULT = 1;
    private Download downloader ;
    private Params[] mParams;
    private Result result;

    public Download(){
        downloader = this;
    }



    public void execute(Params... params){
        this.mParams = params;
        new Thread(){
            @Override
            public void run() {
                final Result result_temp = (Result) downloader.doInbackground(mParams);
                Message msg = new Message();
                msg.what = POST_RESULT;
                Bundle bundle = new Bundle();
                bundle.putSerializable("result_temp",(Serializable)result_temp);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case POST_RESULT:
                    Bundle bundle = msg.getData();
                    Result result_temp = (Result) bundle.getSerializable("result_temp");
                    postResult(result_temp);
                    break;
            }
        }
    };

    public abstract Result doInbackground(Params... params);

    public void postResult(Result result){};
}
