package ysn.com.customtextview.page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ysn.com.customtextview.R;
import ysn.com.textview.LabelEllipsisTextView;

/**
 * @Author yangsanning
 * @ClassName LabelEllipsisActivity
 * @Description 一句话概括作用
 * @Date 2019/4/26
 * @History 2019/4/26 author: description:
 */
public class LabelEllipsisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_ellipsis);

        LabelEllipsisTextView labelEllipsisTextView = findViewById(R.id.label_ellipsis_activity_view);
        labelEllipsisTextView.setText("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
    }
}
