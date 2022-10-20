package com.kingmeter.charging.serverold.service;

public interface BusinessApiMappingPrefix {
    String queryLoginPermission = "/loginPermission/site/";
    String malfunctionDataUpload = "/malfunction";
    String bikeInDock = "/bikeInDock";
    String offLine = "/offline/site/";
    String swipeCardPermission = "/swipeCard/permission/";
    String swipeCardConfirm = "/swipeCard/confirm";
    String allDockInfoUpload = "/allDockInfo";//心跳
    String allDockMonitorData = "/allDockMonitorData";
}
