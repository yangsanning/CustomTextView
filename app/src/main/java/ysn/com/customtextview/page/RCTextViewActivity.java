package ysn.com.customtextview.page;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import ysn.com.customtextview.R;
import ysn.com.textview.RCTextView;

/**
 * @Author yangsanning
 * @ClassName RCTextViewActivity
 * @Description 一句话概括作用
 * @Date 2019/6/23
 * @History 2019/6/23 author: description:
 */
public class RCTextViewActivity extends AppCompatActivity {

    private RCTextView rcTextView;

    private SimpleSeekBarChangeListener seekBarChangeListener = new SimpleSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.rc_text_view_activity_stroke_width:
                    rcTextView.setStrokeWidth(progress, progress, progress);
                    break;
                case R.id.rc_text_view_activity_round_corner_top_left:
                    rcTextView.setRoundCornerTopLeft(progress);
                    break;
                case R.id.rc_text_view_activity_round_corner_top_right:
                    rcTextView.setRoundCornerTopRight(progress);
                    break;
                case R.id.rc_text_view_activity_round_corner_bottom_left:
                    rcTextView.setRoundCornerBottomLeft(progress);
                    break;
                case R.id.rc_text_view_activity_round_corner_bottom_right:
                    rcTextView.setRoundCornerBottomRight(progress);
                    break;
                default:
                    break;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Drawable iconNormal = AppCompatResources.getDrawable(RCTextViewActivity.this, R.mipmap.ic_launcher);
            rcTextView.setIconNormal(iconNormal).setIconWidth(100).setIconHeight(100);
            switch (buttonView.getId()) {
                case R.id.rc_text_view_activity_icon_left:
                    rcTextView.setIconDirection(RCTextView.ICON_DIRECTION_LEFT);
                    break;
                case R.id.rc_text_view_activity_icon_top:
                    rcTextView.setIconDirection(RCTextView.ICON_DIRECTION_TOP);
                    break;
                case R.id.rc_text_view_activity_icon_right:
                    rcTextView.setIconDirection(RCTextView.ICON_DIRECTION_RIGHT);
                    break;
                case R.id.rc_text_view_activity_icon_bottom:
                    rcTextView.setIconDirection(RCTextView.ICON_DIRECTION_BOTTOM);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc_text_view);
        rcTextView = findViewById(R.id.rc_text_view_activity_view);
        // 注意: 设置点击状态时要设置点击事件才能显示效果
        rcTextView.setOnClickListener(v -> {
        });

        setOnSeekBarChangeListener(R.id.rc_text_view_activity_stroke_width);
        setOnSeekBarChangeListener(R.id.rc_text_view_activity_round_corner_top_left);
        setOnSeekBarChangeListener(R.id.rc_text_view_activity_round_corner_top_right);
        setOnSeekBarChangeListener(R.id.rc_text_view_activity_round_corner_bottom_left);
        setOnSeekBarChangeListener(R.id.rc_text_view_activity_round_corner_bottom_right);

        ((CheckBox) findViewById(R.id.rc_text_view_activity_icon_left)).setOnCheckedChangeListener(checkedChangeListener);
        ((CheckBox) findViewById(R.id.rc_text_view_activity_icon_top)).setOnCheckedChangeListener(checkedChangeListener);
        ((CheckBox) findViewById(R.id.rc_text_view_activity_icon_right)).setOnCheckedChangeListener(checkedChangeListener);
        ((CheckBox) findViewById(R.id.rc_text_view_activity_icon_bottom)).setOnCheckedChangeListener(checkedChangeListener);
    }

    private void setOnSeekBarChangeListener(int resId) {
        ((SeekBar) findViewById(resId)).setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public static abstract class SimpleSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
