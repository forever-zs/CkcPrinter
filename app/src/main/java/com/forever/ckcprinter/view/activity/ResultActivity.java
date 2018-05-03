package com.forever.ckcprinter.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.Result;
import com.forever.ckcprinter.presenter.adapter.ResultAdapter;
import com.forever.ckcprinter.utils.PrefUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rv_result)
    RecyclerView rvResult;
    private LinearLayoutManager linearLayoutManager;
    private ResultAdapter resultAdapter;
    private List<Result> resultList;


    private void initView() {
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                ResultActivity.this.finish();
            }
        });
        String str = PrefUtils.getString(this, getIntent().getStringExtra("flag"), "NULL");
        if (str.equals("NULL")){
            resultList = new ArrayList<>();
        }
        else{
            Type type = new TypeToken<ArrayList<Result>>() {}.getType();
            this.resultList = new Gson().fromJson(str, type);
            this.resultAdapter = new ResultAdapter(this.resultList, this);
            this.linearLayoutManager = new LinearLayoutManager(this);
            this.rvResult.setLayoutManager(this.linearLayoutManager);
            this.rvResult.setAdapter(this.resultAdapter);
        }
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        initView();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.ResultActivity
 * JD-Core Version:    0.6.0
 */