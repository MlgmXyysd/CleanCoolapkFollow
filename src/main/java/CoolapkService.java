import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CoolapkService {
    @GET("v6/user/followList")
    Call<JsonObject> getFollowList(@Query("uid") String str, @Query("page") String str2);

    @GET("v6/user/space")
    Call<JsonObject> getUserDetail(@Query("uid") String str);

    @POST("v6/user/unfollow")
    Call<JsonObject> unFollow(@Query("uid") String str);
}
