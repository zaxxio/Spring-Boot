package com.avaand.app.lifecycle;

import com.avaand.app.cache.model.Tracker;
import lombok.extern.java.Log;
import org.aspectj.lang.reflect.InitializerSignature;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sound.midi.Track;

@Log
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LifeCycle implements InitializingBean, DisposableBean{

    @PostConstruct
    public void onLifeCycleCreate(){
        log.info("LifeCycle Created.");
    }

    @PreDestroy
    public void onLifeCycleDestroyed(){
        log.info("LifeCycle Destroyed.");
    }


    @Lookup
    public Tracker getTracker(){
        return new Tracker();
    }

    @Override
    public void destroy() throws Exception {
        // on destroy
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // on create
    }

}