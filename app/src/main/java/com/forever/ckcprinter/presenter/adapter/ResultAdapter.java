package com.forever.ckcprinter.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.modle.bean.Result;
import com.forever.ckcprinter.view.ViewHolder.ResultHolder;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultHolder>
{
  private Context context;
  private ResultHolder resultHolder;
  private List<Result> resultList;

  public ResultAdapter(List<Result> paramList, Context paramContext)
  {
    this.resultList = paramList;
    this.context = paramContext;
  }

  public int getItemCount()
  {
    return this.resultList.size();
  }

  public void onBindViewHolder(ResultHolder paramResultHolder, int paramInt)
  {
    paramResultHolder.setData((Result)this.resultList.get(paramInt), this.context);
  }

  public ResultHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    return new ResultHolder(LayoutInflater.from(this.context).inflate(R.layout.item_result, paramViewGroup, false));
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.presenter.adapter.ResultAdapter
 * JD-Core Version:    0.6.0
 */