package ysn.com.customtextview.page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ysn.com.customtextview.R;
import ysn.com.textview.SpaceTextView;

/**
 * @Author yangsanning
 * @ClassName LabelEllipsisActivity
 * @Description 一句话概括作用
 * @Date 2019/4/26
 * @History 2019/4/26 author: description:
 */
public class SpaceTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_text_view);

        SpaceTextView spaceTextView = findViewById(R.id.spaceTextView);
        spaceTextView.setText("我是调整字间距的");
    }
}
