package com.rt.taopicker.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.QueryPickAreaListRespEntity;
import com.rt.taopicker.main.box.activity.LogisticsBoxActivity;
import com.rt.taopicker.main.crossing.activity.CrossingActivity;
import com.rt.taopicker.main.home.adpater.PickAreaAdapter;
import com.rt.taopicker.main.home.contract.IEmployeeLoginContract;
import com.rt.taopicker.main.home.presenter.EmployeeLoginPresenter;
import com.rt.taopicker.main.packing.activity.PackingSuspenderActivity;
import com.rt.taopicker.main.packing.activity.PackingWaitingActivity;
import com.rt.taopicker.main.picker.activity.PickerWaitActivity;
import com.rt.taopicker.main.temporary.activity.TemporaryActivity;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.KbUtil;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.UserInfoHelper;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.NoticeDialogWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import butterknife.BindView;

/**
 * 员工登录
 * Created by yaoguangyao on 2018/1/25.
 */

public class EmployeeLoginActivity extends BaseActivity<IEmployeeLoginContract.IPresenter> implements IEmployeeLoginContract.IView, AdapterView.OnItemClickListener, View.OnClickListener, TextView.OnEditorActionListener {

    private static final String EXTRA_MENU_CODE = "menuCode";
    private static final String EXTRA_MENU_NAME = "menuName";

    @BindView(R.id.dl_container)
    protected DrawerLayout mContainerDl;

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdg;

    @BindView(R.id.tv_account)
    protected TextView mAccountTv;

    @BindView(R.id.tv_store)
    protected TextView mStoreTv;

    @BindView(R.id.edt_employee_no)
    protected EditText mEmployeeNoTv;

    @BindView(R.id.tv_pick_area)
    protected TextView mPickAreaTv;

    @BindView(R.id.tv_cancel)
    protected TextView mCancelTv;

    @BindView(R.id.ll_pick_area)
    protected LinearLayout mPickAreaLl;

    @BindView(R.id.lv_pick_area)
    protected ListView mPickAreaLv;
    private PickAreaAdapter mPickAreaAdapter;

    @BindView(R.id.ll_ok)
    protected LinearLayout mOkLl;

    @BindView(R.id.ll_open)
    protected LinearLayout mOpenLl;

    @Override
    public int getRootViewResId() {
        return R.layout.home_employee_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String menuCode = bundle.getString(EXTRA_MENU_CODE);
            String menuName = bundle.getString(EXTRA_MENU_NAME);
            mPresenter.init(menuCode, menuName);
        }

        HeadTitleVo headTitleVo = new HeadTitleVo(
                "",
                false,
                Constant.HeadTitleFuncType.HOME
        );
        mHeadTitleWdg.init(headTitleVo);

        mPickAreaAdapter = new PickAreaAdapter(this);
        mPickAreaLv.setAdapter(mPickAreaAdapter);
        mPickAreaLv.setOnItemClickListener(this);

        mCancelTv.setOnClickListener(this);
        mOkLl.setOnClickListener(this);
        mOpenLl.setOnClickListener(this);
    }

    @Override
    public IEmployeeLoginContract.IPresenter getPresenter() {
        return new EmployeeLoginPresenter();
    }

    @Override
    public void showTitle(String menuName) {
        mHeadTitleWdg.setTitle(menuName);
    }

    @Override
    public void showPickAreas(QueryPickAreaListRespEntity body) {
        mPickAreaAdapter.setDatas(body.getPickAreaList());
    }

    @Override
    public void showPickArea() {
        mPickAreaLl.setVisibility(View.VISIBLE);
        mContainerDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //拣货菜单不监听回车
        mEmployeeNoTv.setImeOptions(EditorInfo.IME_ACTION_NONE);
        mEmployeeNoTv.setOnEditorActionListener(null);

    }

    @Override
    public void hidePickArea() {
        mPickAreaLl.setVisibility(View.GONE);
        mContainerDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //监听键盘回车
        mEmployeeNoTv.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mEmployeeNoTv.setOnEditorActionListener(this);
    }

    @Override
    public void showAreaName(String pickAreaName) {
        mPickAreaTv.setText(pickAreaName);
    }

    @Override
    public String getEmployeeNo() {
        return mEmployeeNoTv.getText().toString();
    }

    @Override
    public void toMenu(String menuCode, EmployeeLoginRespEntity body) {
        Intent intent = null;
        if (StringUtil.isNotBlank(menuCode)) {
            switch (menuCode) {
                case Constant.MenuCode.PICKING:
                    gotoPicking();
                    break;
                case Constant.MenuCode.PACKAGE:
                    intent = PackingSuspenderActivity.newIntent(this);
                    startActivity(intent);
                    break;
                case Constant.MenuCode.CHANNEL:
                    intent = CrossingActivity.newIntent(this);
                    startActivity(intent);
                    break;
                case Constant.MenuCode.LOGISTICSBOX:
                    intent = LogisticsBoxActivity.newIntent(this);
                    startActivity(intent);
                    break;
                case Constant.MenuCode.TEMPORARY_STORAGE:
                    intent = TemporaryActivity.newIntent(this);
                    startActivity(intent);
                    break;
                case Constant.MenuCode.NO_SUSPENDER_PACKAGE:
                    intent = PackingWaitingActivity.newIntent(this);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void gotoPicking() {
        EmployeeLoginRespEntity employeeLoginRespEntity = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeLoginRespEntity.getCrossPickOrder() != null && employeeLoginRespEntity.getCrossPickOrder()) {
            DialogHelper.getInstance().showDialog(employeeLoginRespEntity.getMessage(), new NoticeDialogWidget.DialogListener() {
                @Override
                public void onClick() {
                    mPresenter.checkPickArea(employeeLoginRespEntity.getPickAreaNo(), employeeLoginRespEntity.getPickAreaName());
                    mPresenter.submit();
                }
            });
        } else {
            Intent intent = PickerWaitActivity.newIntent(this);
            startActivity(intent);
        }
    }

    @Override
    public void showUser(String realname, String storeName) {
        mAccountTv.setText(realname);
        mStoreTv.setText(storeName);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String areaCode = (String) view.getTag(R.id.tag_area_code);
        String areaName = (String) view.getTag(R.id.tag_area_name);
        mPresenter.checkPickArea(areaCode, areaName);
        mContainerDl.closeDrawers();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                mContainerDl.closeDrawers();
                break;
            case R.id.ll_ok:
                mPresenter.submit();
                break;
            case R.id.ll_open:
                mContainerDl.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    public static Intent newIntent(Context context, String menuCode, String menuName) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MENU_CODE, menuCode);
        bundle.putString(EXTRA_MENU_NAME, menuName);

        Intent intent = new Intent(context, EmployeeLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KbUtil.isShouldHideInput(v, ev)) {
                KbUtil.hideKb(this);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        //当actionId == XX_SEND 或者 XX_DONE时都触发
        //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
        //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
        if (actionId == EditorInfo.IME_ACTION_SEND
                || actionId == EditorInfo.IME_ACTION_DONE
                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
            mPresenter.submit();
            textView.setText("");
            KbUtil.hideKb(this);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //禁止返回
    }
}
