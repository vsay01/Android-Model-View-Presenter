package com.vsay.demo.mvp.components;

import com.vsay.demo.mvp.activities.MainActivity;
import com.vsay.demo.mvp.modules.AppModule;
import com.vsay.demo.mvp.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vsaya on 2/11/17.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
}