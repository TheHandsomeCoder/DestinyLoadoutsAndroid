package thehandsomecoder.com.destinyloadouts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.rest.RestService;

import java.io.IOException;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okio.BufferedSink;


@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity
{
    String bungieSignInURI = "https://www.bungie.net/en/User/SignIn/Psnid";
    String PSN_OAUTH_URI = "https://auth.api.sonyentertainmentnetwork.com/login.do";

    @RestService
    DestinyService destinyService; //Inject it



    @Click
    void button() {
       startActivity(new Intent(this, LoginActivity_.class));
    }

    @Click @Background
    void button2(){
        readCookies();
    }


    @Click
    @Background
    void readCookies(){
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        String cookie = cookieManager.getCookie("https://www.bungie.net/");



        HashMap<String, String> cookies = new HashMap<>();
        for(String s : cookie.split(";"))
        {
            String[] keyValue = s.split("=",2);
            cookies.put(keyValue[0].trim(),keyValue[1]);
        }
        Log.d("SplitCookies",cookies.toString());
        setupDestinyService(cookies);
    }

    private void setupDestinyService(HashMap<String, String> cookies) {
        destinyService.setCookie("bungled", cookies.get("bungled"));
        destinyService.setCookie("bungleatk", cookies.get("bungleatk"));
        destinyService.setHeader("X-API-Key", "ecde0f0dd63045399a46764891111647");
        destinyService.setHeader("x-csrf", cookies.get("bungled"));

        Log.v("Loadouts",destinyService.getCurrentUser());
    }
}
