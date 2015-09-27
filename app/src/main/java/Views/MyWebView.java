package Views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import Intefaces.OnReachEndListener;

/**
 * Created by Ali on 9/26/2015.
 */
public class MyWebView extends WebView {
    private OnReachEndListener onReachEndListener;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int height = (int) Math.floor(this.getContentHeight() * this.getScale());
        int webViewHeight = this.getHeight();
        int cutoff = height - webViewHeight - 5; // Don't be too strict on the cutoff point
        if (t >= cutoff) {
            onReachEndListener.onReach();
        }else{
            onReachEndListener.onGetAway();
        }
    }


    public void setOnReachEndListener(OnReachEndListener onReachEndListener){
        this.onReachEndListener = onReachEndListener;
    }
}
