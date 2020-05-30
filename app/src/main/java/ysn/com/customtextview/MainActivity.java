package ysn.com.customtextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ysn.com.customtextview.page.LabelEllipsisActivity;
import ysn.com.customtextview.page.MagicalActivity;
import ysn.com.customtextview.page.RCTextViewActivity;
import ysn.com.customtextview.page.SpaceTextViewActivity;
import ysn.com.textview.SpaceTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_activity_label_ellipsis).setOnClickListener(this);
        findViewById(R.id.main_activity_magical).setOnClickListener(this);
        findViewById(R.id.main_activity_rc_text_view).setOnClickListener(this);
        findViewById(R.id.main_activity_sapce_text_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_activity_label_ellipsis:
                startActivity(new Intent(this, LabelEllipsisActivity.class));
                break;
            case R.id.main_activity_magical:
                startActivity(new Intent(this, MagicalActivity.class));
                break;
            case R.id.main_activity_rc_text_view:
                startActivity(new Intent(this, RCTextViewActivity.class));
                break;
            case R.id.main_activity_sapce_text_view:
                startActivity(new Intent(this, SpaceTextViewActivity.class));
                break;
            default:
                break;
        }
    }
}
