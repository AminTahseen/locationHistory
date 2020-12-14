package com.masterandroid.backgroundservice;

public class Api {
    private static final String ROOT_URL = "https://locationhistoryapi.000webhostapp.com/Api.php?apicall=";
    public static final String URL_CREATE_LIST = ROOT_URL + "createHistory";
    public static final String URL_READ_LIST = ROOT_URL + "getHistory&userId=";
    public static final String URL_UPDATE_LIST = ROOT_URL + "updateHistoryStatus";
    public static final String URL_DELETE_LIST = ROOT_URL + "deleteList&userId=";
}
