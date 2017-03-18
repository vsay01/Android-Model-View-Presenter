package com.vsay.demo.mvp.components;

import android.content.SharedPreferences;

import com.vsay.demo.mvp.modules.SharedPreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vsaya on 2/12/17.
 */

@Singleton
@Component(modules = {SharedPreferenceModule.class})
public interface SharedPrerenceComponent {
    SharedPreferences getSharedPreference();
}