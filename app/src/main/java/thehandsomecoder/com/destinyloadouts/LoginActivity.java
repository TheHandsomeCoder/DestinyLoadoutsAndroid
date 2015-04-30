package thehandsomecoder.com.destinyloadouts;

import android.content.Context;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;


import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {


    @RestService
    DestinyService destinyService; //Inject it

    Context context;

    @ViewById(R.id.webView)
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    public void showLoginToast(String url)
    {
        Toast toast = Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG);
        toast.show();
    }

    @Background
    void searchDestiny() {
        Log.d("Loadouts", destinyService.getCurrentUser());
    }


    @Click
    @Background
    void readCookies(){
        CookieManager cookieManager = CookieManager.getInstance();
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

    @AfterViews
    void updateWebViews() {
        //myWebView.loadUrl("https://www.bungie.net/en/User/SignOut?bru=%2F");
        WebSettings settings = myWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://www.bungie.net/en/User/SignIn/Psnid");
        myWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("Loadouts URL", url);
                return null;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }


    private void readCookiesFromFile(Context context) {
        File file = getCookieDirectory(context);
        if(file == null)
        {
            return;
        }
        else
        {
            File[] files = file.listFiles();
            for( File f : files)
            {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(f));
                    String s = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        s += line;
                    }
                    Log.v("CookieFile", s);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File getCookieDirectory(Context context)
    {
        File filesDirectory = context.getFilesDir();
        File cookieDirectory = new File((new StringBuilder().append(filesDirectory.getPath()).append('/').append("cookies").append('/').toString()));
        cookieDirectory.mkdirs();
        return cookieDirectory;
    }
}



