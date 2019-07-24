package com.bc.pmpheep.back.po;

import java.io.Serializable;

public class SsoReturnData implements Serializable {
    private String id;
    private String code;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\"=\"" + id +"\"" +
                ", \"code\"=\"" + code + "\"" +
                ", \"message\"=\"" + message + "\"}" ;
    }
}
