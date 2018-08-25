package com.sunseen.spcontrolsystem.model;

import java.io.Serializable;

public class FailureWarningModel implements Serializable {
    private String failure_name;
    private String failure_id;
    private String failure_describe;
    private String failure_time;

    private boolean is_result = false;
    private boolean result = false;

    public FailureWarningModel(String failure_name,String failure_id,String failure_describe,
                               String failure_time,boolean is_result,boolean result)
    {
        this.failure_name = failure_name;
        this.failure_describe = failure_describe;
        this.failure_id = failure_id;
        this.failure_time = failure_time;
        this.is_result = is_result;
        this.result = result;
    }

    public String getFailure_name() {
        return failure_name;
    }

    public String getFailure_id() {
        return failure_id;
    }

    public String getFailure_describe() {
        return failure_describe;
    }

    public String getFailure_time() {
        return failure_time;
    }

    public boolean isIs_result() {
        return is_result;
    }

    public void setIs_result(boolean is_result) {
        this.is_result = is_result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
