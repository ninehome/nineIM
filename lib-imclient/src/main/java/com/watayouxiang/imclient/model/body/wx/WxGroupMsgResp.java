package com.watayouxiang.imclient.model.body.wx;

import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgAudio;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCall;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgCard;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFaceEmotion;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgFile;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgGroupApply;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgImage;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgLocation;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgRed;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgType;
import com.watayouxiang.imclient.model.body.wx.msg.InnerMsgVideo;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/12
 * desc :
 */
public class WxGroupMsgResp {
    /**
     * chatlinkid : -128
     * data : [{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"wata\" 被 \"叶44\" 移除了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349018","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":1,"sigleuid":23436,"sysmsgkey":"tokick","t":"2020-04-16 17:14:58","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 将群主转让给了 \"叶44\"","ct":1,"d":5,"f":23436,"g":"128","mid":"349017","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"ownerchange","t":"2020-04-16 17:14:32","tonicks":"叶44","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 邀请 \"叶44\" 加入了群聊","ct":1,"d":5,"f":23436,"g":"128","mid":"349016","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-16 17:14:11","tonicks":"叶44","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 将 \"叶44\" 移除了群聊","ct":1,"d":5,"f":23436,"g":"128","mid":"349015","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"operkick","t":"2020-04-16 17:12:21","tonicks":"叶44","whereflag":1,"whereuid":",22628,"},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"叶44\" 将群主转让给了 \"wata\"","ct":1,"d":5,"f":22628,"g":"128","mid":"349013","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"ownerchange","t":"2020-04-16 17:12:01","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"叶44\" 邀请 \"wata\" 加入了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349012","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-16 17:11:06","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"wata\" 被 \"叶44\" 移除了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349010","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":1,"sigleuid":23436,"sysmsgkey":"tokick","t":"2020-04-16 17:10:56","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"叶44\" 邀请 \"wata\" 加入了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349009","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-16 17:07:59","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"wata\" 被 \"叶44\" 移除了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349007","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":1,"sigleuid":23436,"sysmsgkey":"tokick","t":"2020-04-16 17:07:43","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"叶44\" 邀请 \"wata\" 加入了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349006","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-16 17:06:46","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"\"wata\" 被 \"叶44\" 移除了群聊","ct":1,"d":5,"f":22628,"g":"128","mid":"349004","nick":"叶44","opernick":"叶44","sendbysys":1,"sigleflag":1,"sigleuid":23436,"sysmsgkey":"tokick","t":"2020-04-16 17:06:19","tonicks":"wata","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_smuser/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 将群主转让给了 \"叶44\"","ct":1,"d":5,"f":23436,"g":"128","mid":"348985","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"ownerchange","t":"2020-04-16 16:53:24","tonicks":"叶44","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 邀请 \"微信支付官方助手、talent、官方机器人、文曲2020、徐飞\" 加入了群聊","ct":1,"d":5,"f":23436,"g":"128","mid":"348984","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-16 16:52:50","tonicks":"微信支付官方助手、talent、官方机器人、文曲2020、徐飞","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 邀请 \"叶44\" 加入了群聊","ct":1,"d":5,"f":23436,"g":"128","mid":"348983","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-16 16:51:22","tonicks":"叶44","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 将 \"叶44\" 移除了群聊","ct":1,"d":5,"f":23436,"g":"128","mid":"348980","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"operkick","t":"2020-04-16 16:50:53","tonicks":"叶44","whereflag":1,"whereuid":",22628,"},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"我们在这种","ct":1,"d":3,"f":22628,"g":"128","mid":"348978","nick":"叶44","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-16 16:50:46","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"对着的就是你","ct":1,"d":3,"f":22628,"g":"128","mid":"348977","nick":"叶44","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-16 16:50:44","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"用户将","ct":1,"d":3,"f":22628,"g":"128","mid":"348976","nick":"叶44","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-16 16:50:43","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"丁是丁师傅会考虑清楚我们在","ct":1,"d":3,"f":22628,"g":"128","mid":"348975","nick":"叶44","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-16 16:50:41","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"1","ct":1,"d":2,"f":23436,"g":"128","mid":"348874","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-15 17:51:53","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"{\"bizavatar\":\"/wx/group/avatar/22/9010/1119563/88097616/74541310984/39/001637/1250095626776092672_sm.jpg\",\"bizid\":\"240\",\"bizname\":\"王涛小伙伴们\",\"cardtype\":2,\"shareFromUid\":23436,\"shareToBizid\":\"128\"}","cardc":{"bizavatar":"/wx/group/avatar/22/9010/1119563/88097616/74541310984/39/001637/1250095626776092672_sm.jpg","bizid":"240","bizname":"王涛小伙伴们","cardtype":2,"shareFromUid":23436,"shareToBizid":"128"},"ct":9,"d":2,"f":23436,"g":"128","mid":"348856","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-15 16:50:48","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg","bc":"","c":"{\"bizavatar\":\"/user/avatar/50/8931/1119484/88097537/74541310905/91/170020/t-io-前白背蓝-正方.png_sm.jpg\",\"bizid\":\"23357\",\"bizname\":\"talent\",\"cardtype\":1,\"shareFromUid\":22628,\"shareToBizid\":\"128\"}","cardc":{"bizavatar":"/user/avatar/50/8931/1119484/88097537/74541310905/91/170020/t-io-前白背蓝-正方.png_sm.jpg","bizid":"23357","bizname":"talent","cardtype":1,"shareFromUid":22628,"shareToBizid":"128"},"ct":9,"d":1,"f":22628,"g":"128","mid":"347881","nick":"叶44","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-04-02 17:49:29","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"\"wata\" 邀请 \"王涛2、王涛1、王涛4、王涛3\" 加入了群聊","ct":1,"d":5,"f":23436,"g":"128","mid":"347780","nick":"wata","opernick":"wata","sendbysys":1,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"join","t":"2020-04-01 10:44:15","tonicks":"王涛2、王涛1、王涛4、王涛3","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"好(✪▽✪)","ct":1,"d":2,"f":23436,"g":"128","mid":"346796","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-06 16:58:37","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/71/8203/1118756/88096809/74541310177/68/002215/1235601530019782656_sm.jpeg","bc":"","c":"[坏笑]","ct":1,"d":3,"f":22629,"g":"128","mid":"346795","nick":"我是叶33","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-06 16:55:34","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/71/8203/1118756/88096809/74541310177/68/002215/1235601530019782656_sm.jpeg","bc":"","c":"出来嗨","ct":1,"d":3,"f":22629,"g":"128","mid":"346794","nick":"我是叶33","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-06 16:55:17","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/71/8203/1118756/88096809/74541310177/68/002215/1235601530019782656_sm.jpeg","bc":"","c":"真的嗨","ct":1,"d":3,"f":22629,"g":"128","mid":"346767","nick":"我是叶33","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:58:40","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"哈哈哈哈嗝(ಡ&omega;ಡ)hiahiahia","ct":1,"d":2,"f":23436,"g":"128","mid":"346766","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:17:07","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"哈哈哈","ct":1,"d":2,"f":23436,"g":"128","mid":"346765","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:17:03","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"好玩吗","ct":1,"d":2,"f":23436,"g":"128","mid":"346764","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:16:57","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"睡了没","ct":1,"d":2,"f":23436,"g":"128","mid":"346763","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:16:54","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"(&brvbar;3[▓▓]","ct":1,"d":2,"f":23436,"g":"128","mid":"346762","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:16:42","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"睡觉","ct":1,"d":2,"f":23436,"g":"128","mid":"346761","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:16:40","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"玩个球","ct":1,"d":2,"f":23436,"g":"128","mid":"346760","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-05 23:16:36","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"[心碎]","ct":1,"d":2,"f":23436,"g":"128","mid":"346718","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:29:37","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"我应该认识的，大佬","ct":1,"d":2,"f":23436,"g":"128","mid":"346717","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:29:15","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"换个头像就不认识了嘛[撇嘴]","ct":1,"d":3,"f":23440,"g":"128","mid":"346716","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:28:51","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"皮","ct":1,"d":2,"f":23436,"g":"128","mid":"346715","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:28:47","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"666","ct":1,"d":2,"f":23436,"g":"128","mid":"346714","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:28:40","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"[撇嘴]","ct":1,"d":3,"f":23440,"g":"128","mid":"346713","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:28:31","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"我是你同桌","ct":1,"d":3,"f":23440,"g":"128","mid":"346712","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:28:26","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"手动打的艾特","ct":1,"d":3,"f":23440,"g":"128","mid":"346711","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:28:00","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"你是谁","ct":1,"d":2,"f":23436,"g":"128","mid":"346710","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:27:39","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"没@","ct":1,"d":2,"f":23436,"g":"128","mid":"346709","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:27:26","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"@wata","ct":1,"d":3,"f":23440,"g":"128","mid":"346708","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:26:55","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"艾特wata","ct":1,"d":3,"f":23440,"g":"128","mid":"346707","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:26:36","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"你第二条消息发的什么","ct":1,"d":3,"f":23440,"g":"128","mid":"346706","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:26:27","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"办理中","ct":1,"d":2,"f":23436,"g":"128","mid":"346705","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:16:12","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"[点赞]","ct":1,"d":2,"f":23436,"g":"128","mid":"346704","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:15:06","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/22/9010/1119563/88097616/74541310984/110/091514/1196597769314377728_sm.jpg","bc":"","c":"66666","ct":1,"d":2,"f":23436,"g":"128","mid":"346703","nick":"wata","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-04 18:14:49","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"叶","ct":1,"d":3,"f":23440,"g":"128","mid":"346429","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-02 13:57:41","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"可以","ct":1,"d":3,"f":23440,"g":"128","mid":"346428","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-02 13:57:34","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"我","ct":1,"d":3,"f":23440,"g":"128","mid":"346427","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-02 13:57:28","tonicks":"","whereflag":2,"whereuid":","},{"avatar":"/user/avatar/26/9014/1119567/88097620/74541310988/32/191939/1236974927777767424_sm.jpeg","bc":"","c":"我","ct":1,"d":3,"f":23440,"g":"128","mid":"346426","nick":"叶孤城新昵称","opernick":"","sendbysys":2,"sigleflag":2,"sigleuid":-1,"sysmsgkey":"","t":"2020-03-02 13:57:26","tonicks":"","whereflag":2,"whereuid":","}]
     * lastPage : false
     */

    public String chatlinkid;
    public boolean lastPage;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * avatar : /user/avatar/70/8202/1118755/88096808/74541310176/118/170640/1235854300484345856_sm.jpeg
         * bc :
         * c : "wata" 被 "叶44" 移除了群聊
         * ct : 1
         * d : 5
         * f : 22628
         * g : 128
         * mid : 349018
         * nick : 叶44
         * opernick : 叶44
         * sendbysys : 1
         * sigleflag : 1
         * sigleuid : 23436
         * sysmsgkey : tokick
         * t : 2020-04-16 17:14:58
         * tonicks : wata
         * whereflag : 2
         * whereuid : ,
         * cardc : {"bizavatar":"/wx/group/avatar/22/9010/1119563/88097616/74541310984/39/001637/1250095626776092672_sm.jpg","bizid":"240","bizname":"王涛小伙伴们","cardtype":2,"shareFromUid":23436,"shareToBizid":"128"}
         */

        public String avatar;
        public String bc;
        public int d;
        public int f;
        public String g;
        public String mid;
        public String nick;
        public int sendbysys;
        /**
         * 1 独有的消息，2 双方的消息
         */
        public int sigleflag;
        /**
         * 独有消息归属人
         */
        public int sigleuid;
        public String sysmsgkey;
        public String t;
        /**
         * 被操作者nick
         */
        public String tonicks;
        /**
         * 操作者nick
         */
        public String opernick;
        /**
         * 消息过滤标志：1 过滤，2 正常
         */
        public int whereflag;
        /**
         * 需要过滤的用户uid列表：,12312,45353,54535,
         */
        public String whereuid;
        /**
         * 消息类型
         *
         * @see InnerMsgType
         */
        public int ct;

        public String operbizdata;
        /**
         * 消息内容
         */
        public String c;
        public InnerMsgFile fc;
        public InnerMsgVideo vc;
        public InnerMsgAudio ac;
        public InnerMsgImage ic;
        public InnerMsgCard cardc;
        public InnerMsgCall call;
        public InnerMsgRed red;
        public InnerMsgGroupApply apply;
        public InnerMsgLocation location;
        public InnerMsgFaceEmotion faceEmotion;
    }

    @Override
    public String toString() {
        int msgCount = 0;
        if (data != null) {
            msgCount = data.size();
        }
        return "WxGroupMsgResp{" +
                "chatlinkid='" + chatlinkid + '\'' +
                ", lastPage=" + lastPage +
                ", msgCount=" + msgCount +
                '}';
    }
}
