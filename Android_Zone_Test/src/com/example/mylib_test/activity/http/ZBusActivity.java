package com.example.mylib_test.activity.http;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.entity.Img;
import com.example.mylib_test.activity.http.zeventbus.Mode;
import com.example.mylib_test.activity.http.zeventbus.ThreadModeX;
import com.example.mylib_test.activity.http.zeventbus.ZEventBus;

/**
 * 因为有这个finalize  所以bu 用kotlin
 */
public class ZBusActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_ebus);

        findViewById(R.id.zeventBus3).setOnClickListener(this);
        findViewById(R.id.zeventBus).setOnClickListener(this);
        findViewById(R.id.zeventBus2).setOnClickListener(this);
    }

    //    /**
//     * 注意不能用内部类 会泄漏 activity 因为持有外部引用！
//     */
//    public  class Bean{
//        String name;
//
//        public Bean(String name) {
//            this.name = name;
//        }
//    }
    @ThreadModeX(threadMode = Mode.BACKGROUND)
    public void heihei(Bean bean) {
        ZEventBus.e("method:heihei---->" + bean.name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zeventBus:
                new ZEventBus(this).post(new Bean("what?"));
                return;
            case R.id.zeventBus2:
                new ZEventBus(this).post(new Img());
                return;
            case R.id.zeventBus3:
                zEventBus = new ZEventBus(ZBusActivity.this);
                mHandler.postDelayed(runable, 10000);
                finish();
                System.gc();
                return;
        }
    }

    public static ZEventBus zEventBus;
    public static MyRunnable runable = new MyRunnable();

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            ZBusActivity.zEventBus.post(new Bean("what?"));
        }
    }

    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        ZEventBus.e("finalize:---->ZBusActivity");
    }
}
