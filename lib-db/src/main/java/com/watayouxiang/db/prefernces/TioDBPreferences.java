package com.watayouxiang.db.prefernces;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   : 数据库 配置文件
 * </pre>
 */
public class TioDBPreferences extends PreferencesUtils {
    private static final String KEY_CURR_USER_PHONE = "curr_user_phone";
    private static final String KEY_CURR_USER_EMAIL = "curr_user_email";
    private static final String KEY_CURR_EX_DATA = "curr_exdata";
    private static final String KEY_CURR_USER_ID = "curr_user_id";
    private static final String KEY_CURR_USER_VOICE = "curr_user_voice";
    private static final String KEY_CURR_USER_SHAKE = "curr_user_shake";
    private static final String KEY_CURR_USER_NO_DISTURB = "curr_user_no_disturb";

    private static final String KEY_CURR_USER_MONEY = "curr_user_money";

    private static final String KEY_CURR_WALLET_FLAG = "curr_user_open_wallet";

    // ====================================================================================
    // currUserId
    // ====================================================================================
    public static void saveCurrNoDisturb(String glag) {
        saveString(KEY_CURR_USER_NO_DISTURB, glag);
    }


    public static String getCurrNoDisturb() {
        return getString(KEY_CURR_USER_NO_DISTURB, "");
    }

    public static void saveCurrVoice(String uid) {
        saveString(KEY_CURR_USER_VOICE, uid);
    }


    public static String getCurrVoice() {
        return getString(KEY_CURR_USER_VOICE, "1");
    }


    public static void saveCurrShake(String uid) {
        saveString(KEY_CURR_USER_SHAKE, uid);
    }


    public static String getCurrShake() {
        return getString(KEY_CURR_USER_SHAKE, "1");
    }

    public static void saveCurrUid(long uid) {
        saveLong(KEY_CURR_USER_ID, uid);
    }


    public static long getCurrUid() {
        return getLong(KEY_CURR_USER_ID, -1);
    }

    public static void savePhone(String phone) {
        saveString(KEY_CURR_USER_PHONE, phone);
    }


    public static String getPhone() {
        return getString(KEY_CURR_USER_PHONE, "");
    }

    public static void saveEmail(String email) {
        saveString(KEY_CURR_USER_EMAIL, email);
    }

    public static String getEmail() {
        return getString(KEY_CURR_USER_EMAIL, "");
    }

    public static void saveExData(String email) {
        saveString(KEY_CURR_EX_DATA, email);
    }

    public static String getExData() {
        return getString(KEY_CURR_EX_DATA, "");
    }

    public static void saveCurrMoney(int money){
        saveInt(KEY_CURR_USER_MONEY, money);
    }

    public static void saveCurrMoney(String money){
        saveInt(KEY_CURR_USER_MONEY, money == null ? 0 : Integer.parseInt(money));
    }

    public static int getCurrMoney(){
        return getInt(KEY_CURR_USER_MONEY, 0);
    }

    public static void setOpenWalletFlag(boolean openWalletFlag){
        saveBoolean(KEY_CURR_WALLET_FLAG+getCurrUid(), openWalletFlag);
    }

    public static boolean hasOpenWallet(){
        return getBoolean(KEY_CURR_WALLET_FLAG+getCurrUid(), false);
    }
}
