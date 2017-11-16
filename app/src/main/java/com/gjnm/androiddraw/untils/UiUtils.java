
package com.gjnm.androiddraw.untils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.gjnm.androiddraw.application.MyApplication;

import java.lang.reflect.Field;

public class UiUtils {
    private static float sDensity = 0f;
    private static int sDensityDpi = 0;
    private static int sScreenWidth = 0;
    private static int sScreenHeight = 0;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setLayerType(View v, int layerType, Paint paint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            v.setLayerType(layerType, paint);
        }
    }

    public static void destroyView(View view) {
        if (view == null)
            return;

        // 设置 mParent 为 null 时需先清除焦点
        view.clearFocus();

        // set view.mContext = null, mParent = null
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            Class<?> clsView;
            clsView = cl.loadClass("android.view.View");

            Field fieldParent = clsView.getDeclaredField("mParent");
            fieldParent.setAccessible(true);
            fieldParent.set(view, null);
        } catch (ClassNotFoundException e) {
        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (NullPointerException e) {
        }

        try {
            view.setVisibility(View.GONE);
            view.destroyDrawingCache();
            setLayerType(view, View.LAYER_TYPE_NONE, null);

            if (view.getBackground() != null) {
                view.setBackgroundDrawable(null);
            }

            view.setAnimation(null);
            view.setOnFocusChangeListener(null);
            view.setOnKeyListener(null);
            view.setOnLongClickListener(null);
            view.setOnTouchListener(null);
            if (!(view instanceof AdapterView))
                view.setOnClickListener(null);

            if ((view instanceof ViewGroup) && !(view instanceof AdapterView)) {
                ViewGroup vg = (ViewGroup) view;
                int count = vg.getChildCount();
                for (int i = 0; i < count; i++) {
                    View v = vg.getChildAt(i);
                    if (v != null)
                        destroyView(v);
                }
                vg.removeAllViews();
            } else if (view instanceof AdapterView) {
                AdapterView<?> av = (AdapterView<?>) view;
                av.setAdapter(null);
                av.setAnimation(null);
                av.setOnItemClickListener(null);
                av.setOnItemSelectedListener(null);
                av.setOnItemLongClickListener(null);
            }
        } catch (Exception e) {
            // silently discard all errors
        }
    }

    public static int dipToPx(int dip) {
        return (int) (dip * getDensity() + 0.5f);
    }

    public static float getDensity() {
        if (sDensity == 0f) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return sDensity;
    }

    public static int getDensityDpi() {
        if (sDensityDpi == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getMetrics(dm);
            sDensityDpi = dm.densityDpi;
        }
        return sDensityDpi;
    }

    public static int getScreenWidthPixels() {
        if (sScreenWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenWidth = dm.widthPixels;
        }
        return sScreenWidth;
    }

    public static int getScreenHeightPixels() {
        if (sScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            sScreenHeight = dm.heightPixels;
        }
        return sScreenHeight;
    }

    private static Rect sRectStatusBar = new Rect();

    public static int getStatusBarHeight(Activity act) {
        if (act != null) {
            act.getWindow().getDecorView().getWindowVisibleDisplayFrame(sRectStatusBar);
            return (sRectStatusBar.top > 0) ? sRectStatusBar.top : 0;
        } else {
            return 0;
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void destroyActivityViews(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            View win = activity.findViewById(android.R.id.content);
            UiUtils.destroyView(win);
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    decorView.setBackground(null);
                } else {
                    decorView.setBackgroundDrawable(null);
                }
            }
        } catch (Throwable t) {

        }
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        Class<?> immClass = imm.getClass();
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                Field field = immClass.getDeclaredField(param);
                field.setAccessible(true);
                Object viewObject = field.get(imm);
                if (viewObject instanceof View) {
                    View view = (View) viewObject;
                    if (view.getContext() == destContext) {
                        // 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(imm, null);

                    } else {

                        // InputMethodManager 引用的对象已经变更，需要继续往下进行
                        break;
                    }
                }
            } catch (Throwable t) {

            }
        }
    }

    private static int DENSITY = 0;
    public static int dip2px(Context context, float dpValue) {
        if (context == null || dpValue <= 0) {
            return 0;
        }

        if(DENSITY == 0){
            DENSITY = (int)context.getResources().getDisplayMetrics().density;
        }
        return (int) (dpValue * DENSITY + 0.5f);
    }
}
