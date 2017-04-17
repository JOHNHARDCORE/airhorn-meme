package com.example.nope.fuckandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import java.lang.String;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

/**
 * Created by ASS-NIGGA69 on 12/18/2016.
 */
public class CustomAdapter<S> extends ArrayAdapter{

    LayoutInflater mInflater;
    int width;
    int height;
    int realEstate;
    public CustomAdapter(Context context, int resource, String[] objects){
        super(context, resource, objects);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int barHeight = getStatusBarHeight(context);
        width = size.x;
        height = size.y;
        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[]{ android.R.attr.actionBarSize });
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        realEstate = height - mActionBarSize - barHeight;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;

        int itemCount = getCount();
        int minHeight = realEstate / itemCount;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        LayoutParams params = view.getLayoutParams();

        params.height = minHeight;
        view.setLayoutParams(params);

        String str = (String) getItem(position);

        Log.i("CustomAdapter", str);

        TextView tv = (TextView) view;

        if(tv == null)
        {
            Log.i("CustomAdapter", "is null");
        }

        tv.setText(str);
        return view;

    }
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
