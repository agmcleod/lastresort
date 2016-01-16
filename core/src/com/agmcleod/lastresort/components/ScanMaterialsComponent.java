package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by aaronmcleod on 2016-01-16.
 */
public class ScanMaterialsComponent implements Component {
    public boolean scanTriggerd;
    public boolean canTrigger = true;
    public boolean showCannotScanYet = false;
}
