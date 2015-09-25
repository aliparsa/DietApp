package Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import Views.TextViewFont;
import irdevelopers.dietapp.R;

/**
 * Created by Ali on 9/24/2015.
 */
public class ActionBarHelper {
    public static View getActionbar(Context context,String title,String subtilte){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout actionbar = (RelativeLayout) inflater.inflate(R.layout.item_group, null);
        TextViewFont v_title = (TextViewFont) actionbar.findViewById(R.id.action_bar_title);
        TextViewFont v_subtitle = (TextViewFont) actionbar.findViewById(R.id.action_bar_title);

        v_title.setText(title);
        v_subtitle.setText(subtilte);


        return null;
    }
}
