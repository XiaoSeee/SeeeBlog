package com.yiketang.android.presenter.contract;

import com.yiketang.android.base.BasePresenter;
import com.yiketang.android.base.BaseView;

/**
 * Created by Xiao.Se on 2016/5/22.
 * ..
 */
public interface PasswordContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void setTimeIndicator(boolean active);

        void phoneNumError();

        void codeNumError();

        void passwordShortError();

        void passwordDiffError();

        void resetFail();

        void createFail();


    }

    interface Presenter extends BasePresenter {

        void getCode(String phone);

        void resetPassword(String phone, String code, String newPassword, String againPassword);

        void createUser(String phone, String code, String newPassword, String againPassword);
    }
}
