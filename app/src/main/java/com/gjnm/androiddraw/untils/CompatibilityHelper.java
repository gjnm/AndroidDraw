
package com.gjnm.androiddraw.untils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class CompatibilityHelper {
    public static final boolean IS_LEPHONE;
    public static final boolean IS_MIUI;
    public static final boolean IS_MIUI_2;
    public static final boolean IS_MB525;
    public static final boolean IS_I9000;
    public static final boolean IS_Zoom2;
    public static final boolean IS_COOLPAD_N930;
    public static final boolean IS_M9;
    public static final boolean IS_MX;
    public static final boolean IS_XT800;
    public static final boolean IS_I9108;
    public static final boolean IS_A1_07;
    public static final boolean IS_I9100;
    public static final boolean IS_AT01;
    public static final boolean IS_SUNSUNG_NOTE;
    public static final boolean IS_HTC_G14;
    public static final boolean IS_LENOVO_K900;
    public static final boolean IS_ZTE_V9180;
    public static final boolean IS_VIVO_X3T;

    public static final boolean IS_MIUI_HM;

    static {
        if (!TextUtils.isEmpty(Build.DEVICE)) {
            IS_LEPHONE = Build.DEVICE.startsWith("lephone");
        } else {
            IS_LEPHONE = false;
        }

        boolean isMIUI = "MIUI".equalsIgnoreCase(Build.ID);
        if (!isMIUI && !TextUtils.isEmpty(Build.PRODUCT)) {
            isMIUI = Build.PRODUCT.contains("mione_plus");
        }
        IS_MIUI = isMIUI;

        IS_MIUI_2 = "MI 2".equals(Build.MODEL);
        IS_HTC_G14 = "HTC Z710e".equals(Build.MODEL);
        IS_MB525 = "umts_jordan".equals(Build.DEVICE);
        IS_I9000 = "GT-I9000".equals(Build.DEVICE);
        IS_Zoom2 = "zoom2".equals(Build.DEVICE);
        IS_COOLPAD_N930 = "CP9130".equals(Build.DEVICE);  // CoolPad N930
        IS_M9 = "m9".equals(Build.DEVICE);              // meizu m9
        IS_MX = "mx".equals(Build.DEVICE);
        IS_XT800 = "cg_tita2".equals(Build.DEVICE);     //   MOTO xt800+
        IS_I9108 = "GT-I9108".equals(Build.DEVICE);     // sumsung i9108
        IS_A1_07 = "A1_07".equals(Build.DEVICE);        // IdeaPad Tablet A107
        IS_I9100 = "GT-I9100".equals(Build.DEVICE);     // sumsung i9100
        IS_AT01 = "GINGERBREAD".equals(Build.ID);
        IS_SUNSUNG_NOTE = "GT-N7000".equals(Build.DEVICE);
        IS_LENOVO_K900 = "K900".equals(Build.DEVICE);
        IS_ZTE_V9180 = "V9180".equals(Build.DEVICE);
        IS_VIVO_X3T = "vivo X3t".equals(Build.MODEL);
        IS_MIUI_HM = "HM2013022".equals(Build.DEVICE);
    }

    private final static String[] mPhone = {
            "GT", "SM", "EK", "SGH", "SHV", "SCH", "SPH", "SC", "SPH"
    };

    public static boolean isLDPI(Context context) {
        Resources res = context.getResources();
        if (res != null) {
            DisplayMetrics met = res.getDisplayMetrics();
            if (met != null) {
                return met.densityDpi <= DisplayMetrics.DENSITY_LOW;
            }
        }
        return false;
    }

    public static boolean isLowerThanMDPI(Context context) {
        Resources res = context.getResources();
        if (res != null) {
            DisplayMetrics met = res.getDisplayMetrics();
            if (met != null) {
                return met.densityDpi <= DisplayMetrics.DENSITY_MEDIUM;
            }
        }
        return false;
    }

    public static boolean isLowerThanHDPI(Context context) {
        Resources res = context.getResources();
        if (res != null) {
            DisplayMetrics met = res.getDisplayMetrics();
            if (met != null) {
                return met.densityDpi <= DisplayMetrics.DENSITY_HIGH;
            }
        }
        return false;
    }

    public static boolean isLowerThanXHDPI(Context context) {
        Resources res = context.getResources();
        if (res != null) {
            DisplayMetrics met = res.getDisplayMetrics();
            if (met != null) {
                return met.densityDpi <= DisplayMetrics.DENSITY_XHIGH;
            }
        }
        return false;
    }

    public static boolean IsAndroid4_1() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean isHigherAndroid3_0() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean IsAndroid4() {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean isHigherAndroid5_0() {
        return Build.VERSION.SDK_INT > 20;
    }

    public static boolean isHigherEqAndroid5_1() {
        return Build.VERSION.SDK_INT > 21;
    }

    // 判断是否为三星手机
    public static boolean isSamsungPhone() {
        if (Build.MODEL.toString() != null) {
            for (String temp : mPhone) {
                if (Build.MODEL.toString().contains(temp)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 判断是否为魅族手机
    public static boolean isMeizu() {
        String brand = android.os.Build.BRAND;
        if (brand != null && brand.toUpperCase().equals("MEIZU")) {
            return true;
        }
        return IS_M9 || IS_MX;
    }

    // 判断facebook sdk是否可用
    public static boolean isFaceBookSdkEnabled() {
        if(isMeizu()) {  // 屏蔽魅族线上crash
            return false;
        }
        return Build.VERSION.SDK_INT >= 15;
    }
}
