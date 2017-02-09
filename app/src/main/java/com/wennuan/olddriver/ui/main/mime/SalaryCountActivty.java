package com.wennuan.olddriver.ui.main.mime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wennuan.olddriver.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalaryCountActivty extends AppCompatActivity {

    @BindView(R.id.tv_chat_title)
    TextView tvChatTitle;
    @BindView(R.id.rv_salary)
    RecyclerView rvSalary;
    @BindView(R.id.activity_salary_count_activty)
    LinearLayout activitySalaryCountActivty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_count_activty);
        ButterKnife.bind(this);

        List<SalaryEntity> datas = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            Random random = new Random();
            String money = String.valueOf(random.nextInt(4500) + 500);
            String date = "2016年" + i + "月";
            datas.add(new SalaryEntity(money, date));
        }
        rvSalary.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SalaryAdapter adapter = new SalaryAdapter(datas);
        rvSalary.setAdapter(adapter);
    }


    public void back(View view) {
        finish();
    }

    private class SalaryEntity {
        public String money;
        public String date;

        public SalaryEntity(String money, String date) {
            this.money = money;
            this.date = date;
        }
    }

    private class SalaryAdapter extends BaseQuickAdapter<SalaryEntity, BaseViewHolder> {

        public SalaryAdapter(List<SalaryEntity> data) {
            super(R.layout.item_salary, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SalaryEntity item) {
            helper.setText(R.id.tv_salary_money, item.money);
            helper.setText(R.id.tv_salary_month, item.date);
        }
    }
}

