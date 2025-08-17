package com.watayouxiang.androidutils.mvp;

import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.imclient.TioIMClient;

/**
 * author : TaoWang
 * date : 2020-02-12
 * desc :
 */
public abstract class BasePresenter<Model extends BaseModel, View extends BaseView> {
    private final Model model;
    private final View view;
    private final boolean registerEvent;

    public BasePresenter(Model model, View view, boolean registerEvent) {
        this.model = model;
        this.view = view;
        this.registerEvent = registerEvent;
        if (registerEvent) {
            TioIMClient.getInstance().getEventEngine().register(this);
        }
    }

    public BasePresenter(Model model, boolean registerEvent) {
        this(model, null, registerEvent);
    }

    public BasePresenter(Model model, View view) {
        this(model, view, false);
    }

    public BasePresenter(Model model) {
        this(model, null, false);
    }

    public View getView() {
        return view;
    }

    public Model getModel() {
        return model;
    }

    public void detachView() {
        model.detachModel();
        TioHttpClient.cancel(this);
        if (registerEvent) {
            TioIMClient.getInstance().getEventEngine().unregister(this);
        }
    }
}
