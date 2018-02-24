package com.rt.taopicker.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.Menu;
import com.rt.taopicker.main.home.adpater.MenuAdapter;
import com.rt.taopicker.main.home.contract.IHomeContract;
import com.rt.taopicker.main.home.presenter.HomePresenter;
import com.rt.taopicker.main.lack.activity.OutStockActivity;
import com.rt.taopicker.main.login.activity.LoginActivity;
import com.rt.taopicker.main.packing.activity.PackingBrushVehicleActivity;
import com.rt.taopicker.main.packing.activity.PackingSuspenderActivity;
import com.rt.taopicker.main.packing.activity.PackingWaitingActivity;
import com.rt.taopicker.main.printer.activity.PrinterTestActivity;
import com.rt.taopicker.main.status.activity.StatusViewActivity;
import com.rt.taopicker.main.temporary.activity.TemporaryActivity;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.util.List;

import butterknife.BindView;

/**
 * Created by yaoguangyao on 2017/12/18.
 */

public class HomeActivity extends BaseActivity<IHomeContract.IPresenter> implements IHomeContract.IView, AdapterView.OnItemClickListener {
    private static final int REQUEST_CODE_CHECK_COURSE_GROUP = 1;

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdg;

    @BindView(R.id.tv_account)
    protected TextView mAccountTv;


    @BindView(R.id.tv_store)
    protected TextView mStoreTv;

    @BindView(R.id.gv_menus)
    protected GridView mMenusGv;

    private MenuAdapter mMenuAdapter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public int getRootViewResId() {
        return R.layout.home_activity;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mMenuAdapter = new MenuAdapter(this);
        mMenusGv.setAdapter(mMenuAdapter);
        mMenusGv.setOnItemClickListener(this);

        HeadTitleVo headTitleVo = new HeadTitleVo(
                "飞牛优鲜",
                false,
                Constant.HeadTitleFuncType.SIGNOUT,
                Color.parseColor("#D7063B"),
                Color.parseColor("#ffffff")
        );
        mHeadTitleWdg.init(headTitleVo);
    }

    @Override
    public IHomeContract.IPresenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showPage(List<Menu> menus) {
        if (menus != null && menus.size() > 0) {
            for (Menu menu : menus) {
                if (menu != null && StringUtil.isNotBlank(menu.getMenuCode())) {
                    switch (menu.getMenuCode()) {
                        case Constant.MenuCode.PICKING:
                            menu.setMenuIcon(R.drawable.icon_pick);
                            break;
                        case Constant.MenuCode.PACKAGE:
                            menu.setMenuIcon(R.drawable.icon_package);
                            break;
                        case Constant.MenuCode.CHANNEL:
                            menu.setMenuIcon(R.drawable.icon_scan);
                            break;
                        case Constant.MenuCode.LOGISTICSBOX:
                            menu.setMenuIcon(R.drawable.icon_recycle);
                            break;
                        case Constant.MenuCode.LACK_LIST:
                            menu.setMenuIcon(R.drawable.icon_lack);
                            break;
                        case Constant.MenuCode.QUERY_STATUS:
                            menu.setMenuIcon(R.drawable.icon_search);
                            break;
                        case Constant.MenuCode.TEMPORARY_STORAGE:
                            menu.setMenuIcon(R.drawable.icon_storage);
                            break;
                        case Constant.MenuCode.NO_SUSPENDER_PACKAGE:
                            menu.setMenuIcon(R.drawable.icon_pack);
                            break;
                    }
                }
            }

            //------------------打印测试菜单begin-----------------
            Menu tempMenu = new Menu();
            tempMenu.setMenuCode(Constant.MenuCode.PRINTER_TEST);
            tempMenu.setMenuName("打印测试");
            menus.add(tempMenu);
            //------------------打印测试菜单end-----------------

            mMenuAdapter.setDatas(menus);
        }
    }

    @Override
    public void showUser(String realname, String storeName) {
        mAccountTv.setText(realname);
        mStoreTv.setText(storeName);
    }

    @Override
    public void onBackPressed() {
        //清空用户信息
        Intent intent = LoginActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String menuCode = (String) view.getTag(R.id.tag_menu_code);
        String menuName = (String) view.getTag(R.id.tag_menu_name);
        Intent intent= null;
        if (menuCode.equals(Constant.MenuCode.LACK_LIST)) { //缺货待盘点
            intent = OutStockActivity.newIntent(this);
        } else if (menuCode.equals(Constant.MenuCode.QUERY_STATUS)) { //状态查询
            intent = StatusViewActivity.newIntent(this);
        } else if(menuCode.equals(Constant.MenuCode.PRINTER_TEST)){
            intent = new Intent(this, PrinterTestActivity.class);
        } else { //其它需要登录
             intent = EmployeeLoginActivity.newIntent(this, menuCode, menuName);
        }

        startActivity(intent);
    }

    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.toast("再按一次返回键退出优鲜作业");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
