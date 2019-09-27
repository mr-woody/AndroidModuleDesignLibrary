package com.okay.component.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ApplicationManager {
    //是否已经排序
    private static boolean isSort = false;
    private final static List<IApplicationLife> applicationDelegates = new ArrayList();

    /**
     * 通过插件加载 IApplicationLife 类
     */
    private static void register(IApplicationLife obj) {
        if (obj!=null) {
            applicationDelegates.add(obj);
        }
    }

    public static List<IApplicationLife>  getApplicationDelegates() {
        if(!isSort && applicationDelegates!=null){
            isSort = true;
            Collections.sort(applicationDelegates, new AppLifeCycleComparator());
        }
        return applicationDelegates;
    }

}
