package com.tiocloud.chat.yanxun.lable;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.tiocloud.chat.R;
import com.tiocloud.chat.yanxun.base.BaseLabelGridFragment;
import com.watayouxiang.wallet.yanxun.utisl.SkinUtils;
import com.tiocloud.chat.yanxun.base.UiUtils;
import com.tiocloud.jpush.utils.LogUtils;
import com.watayouxiang.db.prefernces.TioDBPreferences;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TaoCallback;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.LableDeleteReq;
import com.watayouxiang.httpclient.model.request.LablelListReq;
import com.watayouxiang.httpclient.model.response.LableListResp;
import com.watayouxiang.httpclient.model.response.MailListResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelFragment extends BaseLabelGridFragment<LabelFragment.LabelHolder> {
    private List<Label> mLabelList;
    private Map<String, String> map = new HashMap<>();

    @Override
    public void initDatas(int pager) {
        refreshLabelListFromService();
    }

    @Override
    public LabelHolder initHolder(ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_label_grid, parent, false);
        return new LabelHolder(v);
    }

    @Override
    public LabelHolder initOtherHolder(ViewGroup parent) {
        View v = mInflater.inflate(R.layout.item_other_label_grid, parent, false);
        return new LabelHolder(v);
    }

    @Override
    public void fillData(LabelHolder holder, int position) {
        final Label label = mLabelList.get(position);
        if (label != null) {
            List<MailListResp.Friend> userIds = label.getFriendList();
            if (userIds != null) {
                holder.tv_label.setText(label.getGroupname() + "(" + userIds.size() + ")");
            } else {
                holder.tv_label.setText(label.getGroupname() + "(0)");
            }
        }
        ViewCompat.setBackgroundTintList(holder.tv_label, ColorStateList.valueOf(SkinUtils.getSkin(requireActivity()).getAccentColor()));
        if (label == null) {
            ViewCompat.setBackgroundTintList(holder.iv_label, ColorStateList.valueOf(SkinUtils.getSkin(requireActivity()).getAccentColor()));
            holder.iv_label.setOnClickListener(v -> {
                Intent intent = new Intent(requireActivity(), CreateLabelActivity.class);
                intent.putExtra("isEditLabel", false);
                getActivity().startActivityForResult(intent, 0x01);
            });
        } else {
            holder.iv_delete_label.setVisibility(map.containsKey(label.getGroupId()) ? View.VISIBLE : View.GONE);
            holder.tv_label.setOnClickListener(v -> {
                onItemClick(v, position);
                holder.iv_delete_label.setVisibility(holder.iv_delete_label.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            });
            holder.tv_label.setOnLongClickListener(v -> {
                if (map.containsKey(label.getGroupId())) {
                    map.remove(label.getGroupId());
                } else {
                    map.put(label.getGroupId(), label.getGroupId());
                }
                update();
                return true;
            });
            holder.iv_delete_label.setOnClickListener(v -> {
                onIvClick(position);
                holder.iv_delete_label.setVisibility(holder.iv_delete_label.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
            });
        }
    }

    @Override
    public void fillOtherData(LabelHolder holder, int position) {
        ViewCompat.setBackgroundTintList(holder.iv_label, ColorStateList.valueOf(SkinUtils.getSkin(requireActivity()).getAccentColor()));
//        holder.iv_label.setBackgroundColor(SkinUtils.getSkin(getActivity()).getAccentColor());
//        holder.iv_label.setBackgroundColor(getResources().getColor(R.color.color1));
        holder.iv_label.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CreateLabelActivity.class);
            intent.putExtra("isEditLabel", false);
            startActivityForResult(intent, 0x01);
        });
    }

    public void onItemClick(View view, int position) {
        if (UiUtils.isNormalClick(view)) {// 防止过快点击
            Label label = mLabelList.get(position);
            if (label != null) {
                Intent intent = new Intent(requireActivity(), CreateLabelActivity.class);
                intent.putExtra("isEditLabel", true);
                intent.putExtra("labelId", label.getGroupId());
                intent.putExtra("oldLable", JSON.toJSONString(label));
                startActivityForResult(intent, 0x01);
            }
        }
    }

    public void onIvClick(int position) {
        final Label label = mLabelList.get(position);
        deleteLabel(label);
    }

    private void deleteLabel(final Label label) {
        LableDeleteReq mailListReq = new LableDeleteReq(label.getGroupId());
        mailListReq.setCancelTag(this);
        TioHttpClient.get(this, mailListReq, new TaoCallback<BaseResp<String>>() {
            @Override
            public void onSuccess(Response<BaseResp<String>> response) {
                LogUtils.d("zlb=22223==>"+response.body());
                if (response.body().isOk()){
                    ToastUtils.showShort(getString(R.string.del_success));
                    refreshLabelListFromService();
                }else {
                    ToastUtils.showShort(response.body().getMsg());
                }
            }
        });
//        TioHttpClient.get(mailListReq, new TioCallback<String>() {
//
//            @Override
//            public void onTioSuccess(String lableListResp) {
//                ToastUtils.showShort("删除成功");
//                refreshLabelListFromService();
//                LogUtils.d("zlb-lablelist===>"+JSON.toJSONString(lableListResp));
//            }
//
//            @Override
//            public void onTioError(String msg) {
//
//            }
//        });
//        Map<String, String> params = new HashMap<>();
//        params.put("access_token", coreManager.getSelfStatus().accessToken);
//        params.put("groupId", label.getGroupId());
//        DialogHelper.showDefaulteMessageProgressDialog(requireActivity());
//
//        HttpUtils.get().url(coreManager.getConfig().FRIENDGROUP_DELETE)
//                .params(params)
//                .build()
//                .execute(new BaseCallback<Label>(Label.class) {
//                    @Override
//                    public void onResponse(ObjectResult<Label> result) {
//                        DialogHelper.dismissProgressDialog();
//                        if (result.getResultCode() == 1) {
//                            LabelDao.getInstance().deleteLabel(coreManager.getSelf().getUserId(), label.getGroupId());
//                            loadData();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                        DialogHelper.dismissProgressDialog();
//                    }
//                });
    }

    // 从服务端下载标签
    private void refreshLabelListFromService() {
//        Map<String, String> params = new HashMap<>();
//        update(new ArrayList<>());
//        loadData();
//        LablelListReq mailListReq = new LablelListReq(TioDBPreferences.getCurrUid()+"");
//        mailListReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
//        mailListReq.setCancelTag(this);
        LablelListReq mailListReq = new LablelListReq(TioDBPreferences.getCurrUid()+"");
        mailListReq.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
        mailListReq.setCancelTag(this);
        TioHttpClient.get(mailListReq, new TioCallback<LableListResp>() {
            @Override
            public void onTioSuccess(LableListResp mailListResp) {
//                proxy.onSuccess(mailListResp.group);
                List<LableListResp.Lable> lables = mailListResp.groupList;
                mLabelList = new ArrayList<>();
                for (LableListResp.Lable lable : lables){
                    Label label2 = new Label();
                    label2.setUserId(lable.getUid()+"");
                    label2.setGroupId(lable.getId()+"");
                    label2.setGroupname(lable.getGroupname());
                    label2.setFriendList(lable.getFriendidlist());
                    mLabelList.add(label2);
                }
                update(mLabelList);
                LogUtils.d("zlb==>"+JSON.toJSONString(mailListResp));
            }

            @Override
            public void onTioError(String msg) {
                LogUtils.d("zlb==>"+msg);
//                proxy.onFailure(msg);
            }
        });

//        mailListReq.get(new TioCallback<LableListResp>() {
//            @Override
//            public void onTioSuccess(LableListResp lableListResp) {
//                LogUtils.d("zlb==>"+JSON.toJSONString(lableListResp));
//            }
//
//            @Override
//            public void onTioError(String msg) {
//                LogUtils.d("zlb-fail==>"+msg);
//            }
//        });
//        TioHttpClient.get(mailListReq, new TioCallback<BaseResp<LableListResp>>() {
//
//            @Override
//            public void onTioSuccess(BaseResp<LableListResp> lableListRespBaseResp) {
//
//            }
//
//            @Override
//            public void onTioError(String msg) {
//
//            }
//        });

//        params.put("access_token", coreManager.getSelfStatus().accessToken);
//
//        HttpUtils.get().url(coreManager.getConfig().FRIENDGROUP_LIST)
//                .params(params)
//                .build()
//                .execute(new ListCallback<Label>(Label.class) {
//                    @Override
//                    public void onResponse(ArrayResult<Label> result) {
//                        if (result.getResultCode() == 1 && result.getData() != null) {
//                            List<Label> labelList = result.getData();
//                            LabelDao.getInstance().refreshLabel(coreManager.getSelf().getUserId(), labelList);
//                            update(labelList);
//                            loadData();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e) {
//                    }
//                });
    }

    // 从数据库加载标签
    private void loadData() {
        update(mLabelList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x01) {
//            loadData();
            refreshLabelListFromService();
        }
    }

    class LabelHolder extends RecyclerView.ViewHolder {
        TextView tv_label;
        ImageView iv_label;
        ImageView iv_delete_label;

        LabelHolder(@NonNull View itemView) {
            super(itemView);
            iv_delete_label = itemView.findViewById(R.id.iv_delete_label);
            tv_label = itemView.findViewById(R.id.tv_label);
            iv_label = itemView.findViewById(R.id.iv_label);
        }
    }
}
