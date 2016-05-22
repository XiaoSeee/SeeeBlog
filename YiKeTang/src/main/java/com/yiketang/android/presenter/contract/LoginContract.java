package com.yiketang.android.presenter.contract;

import com.yiketang.android.base.BasePresenter;
import com.yiketang.android.base.BaseView;

/**
 * Created by Xiao.Se on 2016/5/21.
 * ..
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void loginSuccess();

        void emptyPhoneError();

        void emptyPasswordError();

        void loginFail();

        void showBanner(String imgUrl);


    }

    interface Presenter extends BasePresenter {

        void loginPhone(String phone, String password);
    }
}
