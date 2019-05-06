package ysn.com.customtextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ysn.com.textview.MagicalTextView;

public class MagicalActivity extends AppCompatActivity implements View.OnClickListener, MagicalTextView.OnDetailsClickListener {

    private MagicalTextView magicalTextView1;
    private MagicalTextView magicalTextView2;
    private MagicalTextView magicalTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magical);

        String text1 = getResources().getString(R.string.text1);
        String text2 = getResources().getString(R.string.text2);
        String text3 = getResources().getString(R.string.text3);

        magicalTextView1 = findViewById(R.id.magical_activity_view1);
        magicalTextView1.setText(text1, 2)
                .setOnDetailsClickListener(this)
                .setOnClickListener(this);

        magicalTextView2 = findViewById(R.id.magical_activity_view2);
        magicalTextView2.setText(text2, 2)
                .setOnDetailsClickListener(this)
                .setOnClickListener(this);

        magicalTextView3 = findViewById(R.id.magical_activity_view3);
        magicalTextView3.setText(text3, 2)
                .setOnDetailsClickListener(this)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "点击了文本", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetailsClick(MagicalTextView magicalTextView) {
        switch (magicalTextView.getId()) {
            case R.id.magical_activity_view2:
                if (magicalTextView2.getTag() == null) {
                    magicalTextView2.setDetailsText("详细分析")
                            .setDetailsImage(getResources().getDrawable(R.drawable.ic_arrow_blue))
                            .setTag(false);
                } else {
                    Toast.makeText(this, "点击了详情", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "点击了详情", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
