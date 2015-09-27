package irdevelopers.dietapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;

import DataModel.News;
import Helpers.DownloadTaskHidden;
import Helpers.PathHelper;
import Helpers.Ram;
import Helpers.ShareHelper;
import Intefaces.OnReachEndListener;
import Views.MyWebView;


public class WebActivity extends ActionBarActivity {

    MyWebView webView;
    String offlinePath;
    String onlinePath;
    ProgressBar progressBar;
    String mode;
    News news;
    ImageView sharebutton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().setTitle("بازگشت");
        context = WebActivity.this;
        forceRTLIfSupported();

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        sharebutton = (ImageView) findViewById(R.id.imageViewShare);

        webView = (MyWebView) findViewById(R.id.webView);



        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

       // sharebutton.setVisibility(View.GONE);



        if (Ram.news != null) {
            news = Ram.news;
            Ram.news = null;
            onlinePath = news.url;
            offlinePath = PathHelper.homePath + "/" + news.uid + ".html";
            mode = "news";

            webView.setOnReachEndListener(new OnReachEndListener() {
                @Override
                public void onReach() {
                    if(sharebutton.getVisibility()==View.VISIBLE) return;
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                    sharebutton.startAnimation(animation);
                    sharebutton.setVisibility(View.VISIBLE);


                }

                @Override
                public void onGetAway() {
                    if ( sharebutton.getVisibility()==View.GONE ) return;
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                    sharebutton.startAnimation(animation);
                    sharebutton.setVisibility(View.GONE);

                }
            });

        } else {
            sharebutton.setVisibility(View.GONE);
            onlinePath = getIntent().getStringExtra("onlinePath");
            offlinePath = getIntent().getStringExtra("offlinePath");
            mode = "page";
        }


        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareHelper.share(context,news);
            }
        });
        loadPage();


        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);

            }
        });


        // webView.loadUrl("http://192.168.0.5:85/sky/web.html");

    }

    private void loadPage() {

        progressBar.setVisibility(View.VISIBLE);

        if (mode.equals("news")) {
            File file = new File(offlinePath);
            if (file.exists()) {
                // load from file
                webView.loadUrl("file://" + offlinePath);
            } else {
                //load from web
                webView.loadUrl(onlinePath);
                new DownloadTaskHidden(context).execute(news.url, PathHelper.homePath + "/" + news.uid + ".html");
            }
        } else {
            File file = new File(offlinePath);
            if (file.exists()) {
                webView.loadUrl("file://" + offlinePath);
            } else {
                webView.loadUrl(onlinePath);
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            loadPageOnline();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPageOnline() {
        progressBar.setVisibility(View.VISIBLE);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl(onlinePath);
        new DownloadTaskHidden(context).execute(onlinePath, offlinePath);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
}
