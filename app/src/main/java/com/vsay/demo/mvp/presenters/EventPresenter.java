package com.vsay.demo.mvp.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vsay.demo.mvp.activities.MainActivity;
import com.vsay.demo.mvp.api.EventApi;
import com.vsay.demo.mvp.components.DaggerSharedPrerenceComponent;
import com.vsay.demo.mvp.models.Event;
import com.vsay.demo.mvp.modules.SharedPreferenceModule;
import com.vsay.demo.mvp.utils.Constants;
import com.vsay.demo.mvp.utils.LocalUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by datct0407 on 10/6/15.
 */
public class EventPresenter {
    private MainActivity mView;
    private Retrofit retrofit;
    private SharedPreferences mSharedPreferences;

    public EventPresenter(MainActivity view, Retrofit retrofit) {
        this.mView = view;
        this.retrofit = retrofit;
        // Dagger%COMPONENT_NAME%
        mSharedPreferences = DaggerSharedPrerenceComponent.builder()
                .sharedPreferenceModule(new SharedPreferenceModule(mView.getApplication()))
                .build().getSharedPreference();
    }

    public void loadPosts(final boolean isRefresh) {
        if(LocalUtils.isConnected(mView)){
            retrofit.create(EventApi.class).getFeeds().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Event>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(EventPresenter.class.getName(), e.toString());
                            mView.displayErrorSnackbar();
                        }

                        @Override
                        public void onNext(List<Event> data) {
                            saveFeedToSharePreference(data);
                            loadFeeds(isRefresh, data);
                        }
                    });
        }else{
            List<Event> events = getDataFromSharedPreferences();
            if(events == null){
                LocalUtils.buildNoNetworkDialog(mView).show();
                mView.displayErrorSnackbar();
                mView.hideProgress();
            }else{
                loadFeeds(isRefresh, events);
            }
        }
    }

    public void loadFeeds(boolean isRefresh, List<Event> data){
        mView.hideProgress();
        if (isRefresh) {
            mView.refreshPosts(data);
        } else {
            mView.addPosts(data);
        }
    }

    public void saveFeedToSharePreference(List<Event> data){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(data);

        editor.putString(Constants.FEED_KEY, json);
        editor.apply();
    }

    private List<Event> getDataFromSharedPreferences(){
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(Constants.FEED_KEY, null);
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        ArrayList<Event> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}