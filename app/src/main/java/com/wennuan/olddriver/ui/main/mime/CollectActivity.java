package com.wennuan.olddriver.ui.main.mime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;
    @BindView(R.id.activity_collect)
    LinearLayout activityCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);

        rvCollect.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        List<CollectEntity> datas=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new CollectEntity());
        }
        CollectAdapter adapter=new CollectAdapter(datas);
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.view_search_bar,null,false));
        rvCollect.setAdapter(adapter);
    }


    public void back(View view) {
        finish();
    }

    private class CollectEntity
    {

    }

    private class CollectAdapter extends BaseQuickAdapter<CollectEntity,BaseViewHolder>
    {

        public CollectAdapter(List<CollectEntity> data) {
            super(R.layout.item_collect,data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CollectEntity item) {

        }
    }
}
