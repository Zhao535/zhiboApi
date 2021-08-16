package cn.maidaotech.smartapi.common.context;

import cn.maidaotech.smartapi.common.resources.LocaleBundles;

public class Contexts {
    public static void set(Context context){
        SessionThreadLocal.getInstance().set(context);
    }

    public static Context get() {
        return SessionThreadLocal.getInstance().get();
    }

    public static String ensureLocale() {
        Context context = get();
        if (context == null) {
            return LocaleBundles.getDefaultLocale();
        }
        return context.getLocale();
    }

}
