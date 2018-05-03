package com.forever.ckcprinter.presenter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;

public class UserGuideAdapter extends PagerAdapter
{
  private Context context;
  private ArrayList<ImageView> imageList = new ArrayList();

  public UserGuideAdapter(ArrayList<Bitmap> paramArrayList, Context paramContext)
  {
    this.context = paramContext;
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      ImageView localImageView = new ImageView(paramContext);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      localLayoutParams.weight = 11.0F;
      localImageView.setLayoutParams(localLayoutParams);
      localImageView.setScaleType(ScaleType.FIT_XY);
      localImageView.setImageBitmap((Bitmap)paramArrayList.get(i));
      this.imageList.add(localImageView);
    }
  }

  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    paramViewGroup.removeView((View)this.imageList.get(paramInt));
  }

  public int getCount()
  {
    return this.imageList.size();
  }

  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    paramViewGroup.addView((View)this.imageList.get(paramInt));
    return this.imageList.get(paramInt);
  }

  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    return paramView == paramObject;
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.presenter.adapter.UserGuideAdapter
 * JD-Core Version:    0.6.0
 */