package com.vsay.demo.mvp.api;

import com.vsay.demo.mvp.models.Event;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by vsaya on 2/11/17.
 */
public interface EventApi {
    @GET("vsay01/Android-Model-View-Presenter/master/feed.json")
    Observable<List<Event>>
    getFeeds();
}