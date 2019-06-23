package ysn.com.customtextview.page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ysn.com.customtextview.R;

/**
 * @Author yangsanning
 * @ClassName RCTextViewActivity
 * @Description 一句话概括作用
 * @Date 2019/6/23
 * @History 2019/6/23 author: description:
 */
public class RCTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc_text_view);

        findViewById(R.id.rc_text_view_activity_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
