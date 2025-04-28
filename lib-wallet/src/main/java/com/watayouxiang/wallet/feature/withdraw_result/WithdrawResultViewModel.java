package com.watayouxiang.wallet.feature.withdraw_result;

import androidx.lifecycle.ViewModel;

import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.request.PayWithholdQueryReq;
import com.watayouxiang.httpclient.model.response.PayWithholdQueryResp;
import com.watayouxiang.httpclient.prefernces.HttpCache;
import com.watayouxiang.wallet.tools.MoneyUtils;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/27
 *     desc   :
 * </pre>
 */
public class WithdrawResultViewModel extends ViewModel {

    private WithdrawResult withdrawResult;

    public void getWithholdQuery(String serialNumber, WithdrawResultActivity activity) {
        PayWithholdQueryReq withholdQueryReq = new PayWithholdQueryReq(serialNumber);
        withholdQueryReq.setCancelTag(this);
        withholdQueryReq.get(new TioCallback<PayWithholdQueryResp>() {
            @Override
            public void onTioSuccess(PayWithholdQueryResp resp) {
                processWithholdQuery(resp, activity);
            }

            @Override
            public void onTioError(String msg) {
                TioToast.showShort(msg);
            }
        });
    }

    private void processWithholdQuery(PayWithholdQueryResp resp, WithdrawResultActivity activity) {
        String amount = MoneyUtils.fen2yuan(resp.getArrivalAmount());
        String fee = MoneyUtils.fen2yuan(new BigDecimal(resp.getAmount()).subtract(new BigDecimal(resp.getArrivalAmount())));
        String bankIcon = HttpCache.getResUrl(resp.getBankicon());
        String bankInfo = String.format(Locale.getDefault(), "%s(%s)", resp.getBankname(), getBankCardNo(resp.getBankcardnumber()));

        this.withdrawResult = new WithdrawResult(amount, bankInfo, bankIcon, fee);

        WithdrawFragment fragment = new WithdrawFragment();
        activity.replaceFragment(fragment);
    }

    private String getBankCardNo(String bankcardnumber) {
        if (bankcardnumber != null && bankcardnumber.length() >= 4) {
            return bankcardnumber.substring(bankcardnumber.length() - 4);
        }
        return "";
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        TioHttpClient.cancel(this);
    }

    public WithdrawResult getWithdrawResult() {
        return withdrawResult;
    }
}
