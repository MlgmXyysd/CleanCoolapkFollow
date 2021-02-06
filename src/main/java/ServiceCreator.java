import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static com.lz233.onetext.tools.utils.CoolapkAuthUtil.getAS;

public final class ServiceCreator {
    private static final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .addInterceptor(new Client())
            .build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.coolapk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    public final <T> T create(Class<T> cls) {
        return retrofit.create(cls);
    }

    static class Client implements Interceptor {
        @Override
        public final Response intercept(Interceptor.Chain it) throws IOException {
            return it.proceed(it.request()
                    .newBuilder()
                    .addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 11; Google AndroidOS/20.1.21) (#Build; Apple; iPhone 12 Pro Max; QKQ1.190828.002 release-keys; 11) +CoolMarket/11.0.1-2102031-universal")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("X-Sdk-Int", "30")
                    .addHeader("X-Sdk-Locale", "zh-CN")
                    .addHeader("X-App-Id", "com.coolapk.market")
                    .addHeader("X-App-Token", getAS())
                    .addHeader("X-App-Version", "11.0.1")
                    .addHeader("X-App-Code", "2102031")
                    // TODO: 更改 header
                    .addHeader("X-App-Device", "AbsVnb7EUMxAzRgsTZsd2bvdGI7UGbn92bnByOEVjO4MkO1gjO3MjO3IjOyEDI7AyOgsjMhhTYxcTO1MWNzUTZjZGM")
                    .addHeader("X-Dark-Mode", "0")
                    .addHeader("X-App-Channel", "coolapk")
                    .addHeader("X-App-Mode", "universal")
                    .addHeader("Cookie", "uid=" + CoolapkConfig.uid + "; username=" + CoolapkConfig.userName + "; token=" + CoolapkConfig.token)
                    .build());
        }
    }
}
