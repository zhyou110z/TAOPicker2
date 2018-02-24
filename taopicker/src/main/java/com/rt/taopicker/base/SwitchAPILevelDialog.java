package com.rt.taopicker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.util.ApiBaseUrlHelper;
import com.rt.taopicker.util.PreferencesUtil;
import com.rt.taopicker.util.RetrofitHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 切换环境
 * Created by yaoguangyao on 2017/12/28.
 */

public class SwitchAPILevelDialog extends DialogFragment implements View.OnClickListener {
    @BindView(R.id.tv_beta)
    protected TextView mBetaTv;

    @BindView(R.id.tv_preview)
    protected TextView mPreviewTv;

    @BindView(R.id.tv_online)
    protected TextView mOnlineTv;

    @BindView(R.id.tv_version)
    protected TextView mVersionTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.base_switch_api_env, container, false);
        ButterKnife.bind(this, view);
        mVersionTv.setText(getAppInfo());
        mBetaTv.setOnClickListener(this);
        mPreviewTv.setOnClickListener(this);
        mOnlineTv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_beta:
                switchApiLevel(ApiBaseUrlHelper.API_BETA);
                break;
            case R.id.tv_preview:
                switchApiLevel(ApiBaseUrlHelper.API_PREVIEW);
                break;
            case R.id.tv_online:
                switchApiLevel(ApiBaseUrlHelper.API_ONLINE);
                break;
        }
    }

    private void switchApiLevel(String apiLevel) {
        PreferencesUtil.writeString(PreferencesUtil.PREFERENCES_API, apiLevel);
        dismiss();
        ToastUtil.toast(apiLevel);
        //清空baseurl缓存
        RetrofitHelper.reset();
        //清空model 缓存
        SingletonHelper.reset();
    }

    /**
     * 显示切换环境dialog
     */
    public static void showDialog(FragmentActivity activity) {
        String version = BaseApplication.sContext.getString(R.string.version);
        if (version != null && version.equals("develop")) {
            SwitchAPILevelDialog editNameDialog = new SwitchAPILevelDialog();
            editNameDialog.show(activity.getSupportFragmentManager(), "EditNameDialog");
        }
    }

    private String getAppInfo() {
        try {
            String pkName = BaseApplication.sContext.getPackageName();
            String versionName = BaseApplication.sContext.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = BaseApplication.sContext.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "\nversionName:" + versionName + "\nversionCode:" + versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
