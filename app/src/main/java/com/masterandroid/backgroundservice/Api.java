package com.masterandroid.backgroundservice;

public class Api {
    private static final String ROOT_URL = "http://10.33.32.83/LocationHistory/v1/Api.php?apicall=";
    public static final String URL_CREATE_LIST = ROOT_URL + "createHistory";
    public static final String URL_READ_LIST = ROOT_URL + "getList";
    public static final String URL_UPDATE_LIST = ROOT_URL + "updateList";
    public static final String URL_DELETE_LIST = ROOT_URL + "deleteList&userId=";
}
