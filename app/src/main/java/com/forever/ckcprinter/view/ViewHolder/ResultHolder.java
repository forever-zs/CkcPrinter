package com.forever.ckcprinter.view.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.modle.bean.Result;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.tv_resPracticeDate)
    TextView tvResPracticeDate;
    @Bind(R.id.tv_resAccuracy)
    TextView tvResAccuracy;
    @Bind(R.id.tv_resSpeed)
    TextView tvResSpeed;
    @Bind(R.id.tv_resCorrectCount)
    TextView tvResCorrectCount;
    @Bind(R.id.rl_baseShare)
    LinearLayout rlBaseShare;

    public ResultHolder(View paramView) {
        super(paramView);
        ButterKnife.bind(this, paramView);
    }

    public void setData(Result paramResult, Context paramContext) {
        this.tvResPracticeDate.setText(paramResult.getPracticeDate().toLocaleString());
        this.tvResAccuracy.setText("正确率：" + paramResult.getAccuracy() + "%");
        this.tvResCorrectCount.setText("正确数：" + paramResult.getCorrectCount());
        this.tvResSpeed.setText("速度：" + paramResult.getSpeed() + "字/分钟");
    }
}
