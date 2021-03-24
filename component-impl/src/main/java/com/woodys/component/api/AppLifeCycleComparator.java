package com.woodys.component.api;

import java.util.Comparator;

public class AppLifeCycleComparator implements Comparator<IApplicationLife> {
    @Override
    public int compare(IApplicationLife o1, IApplicationLife o2) {
        int p1 = o1.getPriority();
        int p2 = o2.getPriority();
        return p2 - p1;
    }
}
