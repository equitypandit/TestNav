package com.test.testnav.other;

/**
 * Created by Administrator on 23-10-2018.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.test.testnav.R;

public class ImageAdapter extends BaseAdapter {
  private Context mContext;

  int width_px = Resources.getSystem().getDisplayMetrics().widthPixels;

  // Constructor
  public ImageAdapter(Context c) {
    mContext = c;
  }

  public int getCount() {
    return mThumbIds.length;
  }

  public Object getItem(int position) {
    return null;
  }

  public long getItemId(int position) {
    return 0;
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView;

    if (convertView == null) {
      imageView = new ImageView(mContext);
      imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, width_px/3));
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setPadding(0, 0, 0, 0);
//            imageView.getAdjustViewBounds();
    } else {
      imageView = (ImageView) convertView;
    }
    imageView.setImageResource(mThumbIds[position]);
    return imageView;
  }

  // Keep all Images in array
  public Integer[] mThumbIds = {
          R.drawable.e1, R.drawable.e2,
          R.drawable.e3, R.drawable.e4,
          R.drawable.e5, R.drawable.e6,
          R.drawable.e7, R.drawable.e8,
          R.drawable.e9, R.drawable.e10,
          R.drawable.e11,R.drawable.e12
  };
}