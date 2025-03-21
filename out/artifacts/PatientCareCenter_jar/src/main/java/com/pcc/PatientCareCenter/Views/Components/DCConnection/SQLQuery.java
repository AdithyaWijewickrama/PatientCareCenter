package com.pcc.PatientCareCenter.Views.Components.DCConnection;

public class SQLQuery {
    private String queryString;
    private Object[] params;
    private QueryReturnType queryReturnType;

    public SQLQuery(String queryString,QueryReturnType queryReturnType, Object[] params) {
        this.queryString = queryString;
        this.params = params;
        this.queryReturnType=queryReturnType;
    }

    public SQLQuery(String queryString, QueryReturnType queryReturnType) {
        this.queryString = queryString;
        this.queryReturnType = queryReturnType;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public QueryReturnType getQueryReturnType() {
        return queryReturnType;
    }

    public void setQueryReturnType(QueryReturnType queryReturnType) {
        this.queryReturnType = queryReturnType;
    }
}
