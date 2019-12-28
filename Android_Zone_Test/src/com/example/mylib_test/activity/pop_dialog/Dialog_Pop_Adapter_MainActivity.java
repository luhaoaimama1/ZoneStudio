package com.example.mylib_test.activity.pop_dialog;

import com.example.mylib_test.activity.pop_dialog.dialog.ZDialog;
import com.zone.lib.base.FullScrreenDialog;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;

import view.DialogCustemZone;

import com.zone.view.FlowLayout;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.pop_dialog.pop.Pop_Bottom;
import com.example.mylib_test.activity.pop_dialog.pop.Pop_Photo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class Dialog_Pop_Adapter_MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_pop_dialog_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop:
//			PopCus pc=new PopCus(this, R.layout.poptest);
//			pc.showPop();
                Pop_Photo pop = new Pop_Photo(this, R.id.flowLayoutZone1, R.id.pop);
                pop.show();
                break;
            case R.id.tag:
                FlowLayout fz = (FlowLayout) findViewById(R.id.flowLayoutZone1);
                Button bt2 = (Button) fz.findViewWithTag("blue");
                bt2.setBackgroundColor(Color.BLUE);
                break;
            case R.id.bt_floatView:
                // TODO Auto-generated method stub
                Intent intent = new Intent(Dialog_Pop_Adapter_MainActivity.this, FxService.class);
                //启动FxService
                startService(intent);
                finish();
                break;
            case R.id.bt_floatViewClose:
                //uninstallApp("com.phicomm.hu");
                Intent intent2 = new Intent(Dialog_Pop_Adapter_MainActivity.this, FxService.class);
                //终止FxService
                stopService(intent2);
                break;
            case R.id.pop_bottom:
//			PopCus pc=new PopCus(this, R.layout.poptest);
//			pc.showPop();
                Pop_Bottom pop_bottom = new Pop_Bottom(Dialog_Pop_Adapter_MainActivity.this, R.id.pop_bottom);
                pop_bottom.show();
                break;
            case R.id.dialog:
                //dialog自定义测试
                DialogCustemZone dcz = new DialogCustemZone(this) {

                    @Override
                    public void notSure() {
                        toToast("这个is not OK!");
                    }

                    @Override
                    public void isSure() {
                        toToast("这个is OK,流逼！");
                    }

                    @Override
                    public void addSetProperty(Builder db) {
                        db.setIcon(R.drawable.ic_launcher);
                    }

                };
                break;
            case R.id.dialogFullScreen:
                ZDialog zDialog = new ZDialog(this);
                zDialog.show();
                break;

            case R.id.bt_floatNotification:
                startService(new Intent(this,NoticationService.class));
                break;
            case R.id.textGaoLiang:
                //点击文字高粱等效果
                Button bt = (Button) findViewById(R.id.textGaoLiang);
                Spannable span = new SpannableString("I am what are you doing，fucking!");
                span.setSpan(new AbsoluteSizeSpan(20), 1, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//字体大小
                span.setSpan(new ForegroundColorSpan(Color.RED), 5, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//前景色
                span.setSpan(new BackgroundColorSpan(Color.YELLOW), 11, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//背景色
                bt.setText(span);
//			bt.setText(Html.fromHtml(  "<font color=#E61A6B>红色代码</font> "+
//			"<I><font color=#1111EE>蓝色斜体代码</font></I>"+"<u><i><font color=#1111EE>蓝色斜体加粗体下划线代码</font></i></u>"));
                break;

            default:
                break;
        }
    }

    public void toToast(String str) {
        ToastUtils.showShort(this, str);
    }
}
