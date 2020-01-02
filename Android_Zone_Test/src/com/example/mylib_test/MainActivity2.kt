package com.example.mylib_test

import com.example.mylib_test.activity.db.entity.MenuEntity
import com.example.mylib_test.delegates.MenuEntityDeletates
import com.zone.adapter3.QuickRcvAdapter
import com.zone.adapter3.base.IAdapter
import com.zone.adapter3.loadmore.OnScrollRcvListener
import android.app.AlertDialog
import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.LogZSDK
import com.zone.lib.ZLogger
import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import kotlinx.android.synthetic.main.a_menu.*
import java.util.ArrayList

class MainActivity2 : BaseFeatureActivity() {

    private var positonId = -1
    private var alert: AlertDialog? = null
    private var adapter2: IAdapter<MenuEntity>? = null

    companion object {
        //还原最开始的log配置  如果某次配置一次特殊的 打印完后的记得还原配置
        @JvmStatic
        fun initLogger() {
            ZLogger.logLevelList.clear()
            ZLogger.mayLoggerList.clear()
            ZLogger.mayLoggerList.addAll(listOf<ZLogger>(LogApp, LogZSDK))
        }
    }

    override fun initDefaultConifg() {
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_menu)
    }

    override fun onStoragePermit() {
        super.onStoragePermit()
        FileManager.firstInit(this)
    }

    override fun onStorageDeniedMustPermit() {
        super.onStorageDeniedMustPermit()
        finish()
    }

    override fun initData() {
        permissionCheckStorageMustPermit()
        initLogger()
        createDialog()
        listView1.layoutManager = LinearLayoutManager(this)
        val colorArry = intArrayOf(Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN)
        adapter2 = QuickRcvAdapter<MenuEntity>(this, MainMenu.menu)
                .addViewHolder(MenuEntityDeletates(this, colorArry, MainMenu.menu))
                .addHeaderHolder(R.layout.header_simple)
                .addFooterHolder(R.layout.footer_simple)
                .relatedList(listView1)
                .setOnItemLongClickListener { viewGroup, view, i ->
                    positonId = i
                    alert!!.show()
                    false
                }
                .addOnScrollListener(object : OnScrollRcvListener() {

                    var refesh = true

                    override fun loadMore(recyclerView: RecyclerView) {
                        super.loadMore(recyclerView)
                        val mDatasa = ArrayList<MenuEntity>()
                        for (i in 0..4) {
                            mDatasa.add(MenuEntity("insert $i", null))
                        }
                        listView1!!.postDelayed({
                            if (refesh) {
                                adapter2!!.loadMoreComplete()
                                adapter2!!.data.addAll(mDatasa)
                                adapter2!!.notifyDataSetChanged()
                            } else {
                                adapter2!!.loadMoreFail()
                            }
                            refesh = !refesh
                        }, 1000)
                    }
                }) as IAdapter<MenuEntity>?


        //通过加载XML动画设置文件来创建一个Animation对象；
        //		Animation animation= AnimationUtils.loadAnimation(this, R.anim.scale_in);   //得到一个LayoutAnimationController对象；
        //		LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        //		controller.setOrder(LayoutAnimationController.ORDER_RANDOM);   //设置控件显示间隔时间；
        //		controller.setDelay(0.3F);   //为ListView设置LayoutAnimationController属性；
        //		listView1.setLayoutAnimation(controller);
        //		listView1.startLayoutAnimation();


        //		CustomLayoutAnimationController controller2 = new CustomLayoutAnimationController(animation);   //设置控件显示的顺序；
        //		controller2.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        //		controller2.setDelay(2F);   //为ListView设置LayoutAnimationController属性；
        //		listView1.setLayoutAnimation(controller2);
        //		listView1.startLayoutAnimation();
    }

    override fun setListener() {
    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to remove?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    if (positonId != -1) {
                        MainMenu.menu.removeAt(positonId)
                        positonId = -1
                        adapter2!!.notifyDataSetChanged()
                    }
                }
                .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        alert = builder.create()
    }
}
