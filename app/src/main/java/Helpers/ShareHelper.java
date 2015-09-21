package Helpers;

import android.content.Context;
import android.content.Intent;

import DataModel.News;

/**
 * Created by Ali on 9/13/2015.
 */
public class ShareHelper {

    public static void share(Context context, News news){
        String shareBody = news.title ;
        shareBody+="\n\n";
        shareBody+=news.url;
        shareBody+="\n\n";
        shareBody+="جهت دانلود اپلیکیشن تغذیه و رژیم درمانه کلیک کنید";
        shareBody+="\n\n";
        shareBody+="http://azizidiet.com/app.apk";


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}
