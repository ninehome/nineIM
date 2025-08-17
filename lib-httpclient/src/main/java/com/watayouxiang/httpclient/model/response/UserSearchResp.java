package com.watayouxiang.httpclient.model.response;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-20
 * desc :
 */
public class UserSearchResp {

    /**
     * firstPage : true
     * lastPage : false
     * list : [{"nick":"zhfqwscwn","country":"中国","province":"山东省","city":"济南市","roles":[2],"id":32878,"avatar":"/user/base/avatar/20200115/2/1432461217333788409995264.png"},{"nick":"qfxhqw","country":"中国","province":"重庆","city":"重庆市","roles":[2],"id":32863,"avatar":"https://static.oschina.net/uploads/user/582/1165164_50.jpg?t=1471346739000","osc_url":"https://my.oschina.net/jinhe"},{"nick":"edwinyan","country":"中国","province":"江苏省","city":"苏州市","roles":[2],"id":32852,"avatar":"/user/base/avatar/20200115/5/1432561217333827702235136.png"},{"nick":"cpfwmsx","country":"中国","province":"上海","city":"上海市","roles":[2],"id":32839,"avatar":"/user/base/avatar/20200115/8/1433011217333849365815296.png"},{"nick":"lbjoxw","country":"中国","province":"湖北省","city":"武汉市","roles":[2],"id":32833,"avatar":"https://static.oschina.net/uploads/user/1520/3041611_50.jpeg?t=1506306830000","osc_url":"https://my.oschina.net/u/3041611"},{"nick":"changwang","country":"中国","province":"北京","city":"北京市","roles":[2],"id":32824,"avatar":"/user/base/avatar/20200115/8/1433011217333849365815296.png"},{"nick":"147258jw","country":"中国","province":"江苏省","city":"常州市","roles":[2],"id":32823,"avatar":"/user/base/avatar/20200115/3/1433091217333883616501760.png"},{"nick":"Jasonwolf","country":"中国","province":"河南省","city":"郑州市","roles":[2],"id":32819,"avatar":"/user/base/avatar/20200115/2/1432561217333828725645312.png"},{"nick":"weinoai","country":"中国","province":"重庆","city":"重庆市","roles":[2],"id":32804,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"ldwallen","country":"中国","province":"福建省","city":"福州市","roles":[2],"id":32801,"avatar":"/user/base/avatar/20200115/4/1432461217333788229640192.png"},{"nick":"fewkiss","country":"中国","province":"江苏省","city":"盐城市","roles":[2],"id":32787,"avatar":"https://static.oschina.net/uploads/user/87/175530_50.jpg","osc_url":"https://my.oschina.net/u/175530"},{"nick":"OVA_Won","country":"中国","province":"山东省","city":"淄博市","roles":[2],"id":32776,"avatar":"/user/base/avatar/20200115/2/1432561217333828302020608.png"},{"nick":"wata测试","country":"中国","province":"浙江省","city":"杭州市","roles":[2],"id":32774,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"GWH_SKY","country":"中国","province":"江西省","city":"南昌市","roles":[2],"id":32769,"avatar":"https://www.oschina.net/img/portrait.gif","osc_url":"https://my.oschina.net/u/3821578"},{"nick":"wuxiaogu","country":"中国","province":"安徽省","city":"安庆市","roles":[2],"id":32765,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"WenGame","country":"中国","province":"四川省","city":"泸州市","roles":[2],"id":32741,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"wodeln","country":"中国","province":"上海","city":"上海市","roles":[2],"id":32734,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"williamyoung","country":"中国","province":"香港","city":"香港","roles":[2],"id":32710,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"wgq4812","country":"中国","province":"黑龙江省","city":"哈尔滨市","roles":[2],"id":32709,"avatar":"/user/base/avatar/20200115/7/1432571217333833301630976.png"},{"nick":"jesallow","country":"中国","province":"北京","city":"北京市","roles":[2],"id":32705,"avatar":"/user/base/avatar/20200115/2/1432561217333828725645312.png"}]
     * pageNumber : 1
     * pageSize : 20
     * totalPage : 43
     * totalRow : 855
     */

    public boolean firstPage;
    public boolean lastPage;
    public int pageNumber;
    public int pageSize;
    public int totalPage;
    public int totalRow;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * nick : zhfqwscwn
         * country : 中国
         * province : 山东省
         * city : 济南市
         * roles : [2]
         * id : 32878
         * avatar : /user/base/avatar/20200115/2/1432461217333788409995264.png
         * osc_url : https://my.oschina.net/jinhe
         */

        public String nick;
        public String country;
        public String province;
        public String city;
        public int id;
        public String avatar;
        public String osc_url;
        public List<Integer> roles;
    }
}
