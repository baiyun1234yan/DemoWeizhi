package bai.bai.bai.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import bai.bai.bai.demo.R;
import bai.bai.bai.demo.view.KeyBoardLinearLayout;

public class Test extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_view_test);
        KeyBoardLinearLayout keyboard = findViewById(R.id.keyboard);
        keyboard.setKeyBoartTouchListener(new KeyBoardLinearLayout.KeyBoartTouchListener() {
            @Override
            public void onNumberTouch(String inputNumber) {

            }

            @Override
            public void onDelTouch() {

            }

            @Override
            public void onOkTouch() {

            }
        });
    }
}
