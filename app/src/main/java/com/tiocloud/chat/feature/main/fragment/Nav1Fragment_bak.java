package com.tiocloud.chat.feature.main.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.main.base.MainTabFragment;
import com.tiocloud.chat.yanxun.base.OnItemClickListener;
import com.watayouxiang.httpclient.model.response.ConfigResp;

import java.util.ArrayList;
import java.util.List;


/**
 * 导航1
 */
public class Nav1Fragment_bak extends MainTabFragment implements View.OnClickListener {

    public static List<ConfigResp.Website> findItems;
//    public List<ConfigResp.Website> items = new ArrayList<>();
    private List<Fragment> fragments;
    private RecyclerView recy_tab;
    private MyAdapter mAdapter;
    private int selected = 0;
    public Nav1Fragment_bak() {
    }

//    @Override
//    protected int inflateLayoutId() {
//        return R.layout.fragment_venice;
//    }
//
//    @Override
//    protected void onActivityCreated(Bundle savedInstanceState, boolean createView) {
//        if (createView) {
//            initView();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void initView() {
//        items = findItems;
        mAdapter = new MyAdapter();
        recy_tab = (RecyclerView) findViewById(R.id.recy_tab);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        recy_tab.setLayoutManager(layoutManager);
        recy_tab.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                changeFragment(position);
            }
        });
        fragments = new ArrayList<>();
        for (int i = 0; i < findItems.size(); i++) {
            WebItemFragment itemFragment = new WebItemFragment(null);
            itemFragment.homeUrl = findItems.get(i).siteurl;
            itemFragment.title = findItems.get(i).sitename;
            fragments.add(itemFragment);
        }
        if (findItems.size() > 0) {
            changeFragment(0);
        }
    }


    private void changeFragment(int position) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        Fragment fragment = fragments.get(position);

//        if (!fragment.isAdded()) {// 未添加 add
//            transaction.add(R.id.main_content, fragment);
//        }else {
//            transaction.hide(fragments.get(position));
//        }
//        transaction.show(fragment);
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onInit() {
        initView();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private OnItemClickListener onItemClickListener;

        public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_web_title, parent, false);
            if (findItems == null || findItems.size() <= 1){
                view.setVisibility(View.GONE);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            ConfigResp.Website findItem = findItems.get(position);
            holder.name.setText(findItem.sitename);
//            ColorStateList tabColor = SkinUtils.getSkin(getActivity()).getMainTabColorState();
//            holder.name.setTextColor(tabColor);
            if(selected == position){
                holder.lin.setBackgroundResource(R.drawable.btn_web_item_title_select);
            }else {
                holder.lin.setBackgroundResource(R.drawable.btn_web_item_title);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        selected = position;
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return findItems == null ? 0 : findItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            LinearLayout lin;

            public ViewHolder(View itemView) {
                super(itemView);
                name =  itemView.findViewById(R.id.name);
                lin =  itemView.findViewById(R.id.lin);
            }
        }
    }

    @Override
    public void onPageShow(int count, boolean isInit) {
        super.onPageShow(count, isInit);
    }
}
