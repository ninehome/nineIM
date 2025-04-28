package com.tiocloud.chat.feature.search.user.fragment.mvp;

import com.lzy.okgo.model.Response;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.model.request.UserSearchReq;
import com.watayouxiang.httpclient.model.response.UserSearchResp;
import com.watayouxiang.httpclient.model.BaseResp;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class SearchUserFragmentModel extends SearchUserFragmentContract.Model {

    @Override
    public void searchUser(String keyWord, int pageNumber, final DataProxy<UserSearchResp> proxy) {
        final UserSearchReq req = new UserSearchReq(String.valueOf(pageNumber), keyWord);
        TioHttpClient.get(this, req, new TaoCallback<BaseResp<UserSearchResp>>() {
            @Override
            public void onSuccess(Response<BaseResp<UserSearchResp>> response) {
                UserSearchResp data = response.body().getData();
                if (data != null) {
                    proxy.onSuccess(data);
                } else {
                    proxy.onFailure(response.body().getMsg());
                }
            }

            @Override
            public void onError(Response<BaseResp<UserSearchResp>> response) {
                super.onError(response);
                proxy.onFailure(response.getException().getMessage());
            }
        });
    }
}
