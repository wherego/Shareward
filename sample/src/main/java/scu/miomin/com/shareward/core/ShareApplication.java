package scu.miomin.com.shareward.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.scu.miomin.sharewardlib.constants.APPAction;
import com.scu.miomin.sharewardlib.core.AppController;
import com.scu.miomin.sharewardlib.core.AppStatusTracker;
import com.scu.miomin.sharewardlib.http.okhttp.OkHttpStack;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.okhttp.OkHttpClient;

import scu.miomin.com.shareward.constants.APPString;
import scu.miomin.com.shareward.splash.SplashActivity;


/**
 * Created by 莫绪旻 and Stay on 2/2/16.
 */
public class ShareApplication extends Application {

    private static ShareApplication sInstance;

    // Volley request queue
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Fresco.initialize(this);
        LeakCanary.install(this);
        registerAppController();
        Logger.init(APPString.TAG).methodCount(2);
    }

    private void registerAppController() {
        AppStatusTracker.getInstance(this).registerAppController(new AppController() {
            /**
             * 如果App被kill掉了，应该回到welcomeActivity（singtask），重新进入APP正常的启动流程
             */
            @Override
            public void protectApp(Context context) {
                Intent intent = new Intent(context, SplashActivity.class);
                intent.putExtra(APPAction.KEY_HOME_ACTION, APPAction.ACTION_RESTART_APP);
                startActivity(intent);
            }
        });
    }

    public static ShareApplication getInstance() {
        return sInstance;
    }

    /**
     * Returns a Volley request queue for creating network requests
     *
     * @return {@link com.android.volley.RequestQueue}
     */
    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    /**
     * Adds a request to the Volley request queue
     *
     * @param request to be added to the Volley requests queue
     */
    private static void addRequest(@NonNull final Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }

    /**
     * Adds a request to the Volley request queue with a given tag
     *
     * @param request is the request to be added
     * @param tag     tag identifying the request
     */
    public static void addRequest(@NonNull final Request<?> request, @NonNull final String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    /**
     * Cancels all the request in the Volley queue for a given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public static void cancelAllRequests(@NonNull final String tag) {
        if (getInstance().getVolleyRequestQueue() != null) {
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }
}