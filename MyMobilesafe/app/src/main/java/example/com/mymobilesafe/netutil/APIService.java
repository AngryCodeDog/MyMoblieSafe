package example.com.mymobilesafe.netutil;

import example.com.mymobilesafe.bean.JokeResponse;
import example.com.mymobilesafe.bean.SRResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by zyp on 8/17/17.
 */

public interface APIService {
    String MYKEY = "1aa45c675faa8308ed3fa4459eed3095";

    @GET("robot/index")
    Call<SRResponse> requestSmartRoboot(@Query("info") String info,
                                        @Query("dtype") String dtype,
                                        @Query("loc") String loc,
                                        @Query("userid") String userid ,
                                        @Query("key") String key);

    @GET("xiaohua/text")
    @Headers("Authorization:APPCODE b9803ce79fe0415790718d2a6378a385")
    Call<JokeResponse> reqJoke(@Query("pagenum") String pagenum,
                               @Query("pagesize") String pagesize,
                               @Query("sort") String sort);
}
