package com.wennuan.olddriver.ui.main.discover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.SaveCallback;
import com.wennuan.olddriver.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDiscoverActivity extends AppCompatActivity {

    @BindView(R.id.tv_add_discover_cancel)
    TextView tvAddDiscoverCancel;
    @BindView(R.id.tv_add_discover_send)
    TextView tvAddDiscoverSend;
    @BindView(R.id.et_add_discover)
    EditText etAddDiscover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discover);
        ButterKnife.bind(this);

        tvAddDiscoverCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAddDiscoverSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etAddDiscover.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(AddDiscoverActivity.this, "内容为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                AVStatus status = new AVStatus();
                status.setMessage(message);
                AVStatus.sendStatusToFollowersInBackgroud(status, new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null)
                            finish();
                    }
                });
            }
        });
    }
}
