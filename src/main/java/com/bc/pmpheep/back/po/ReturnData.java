package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.List;

public class ReturnData implements Serializable {
    private String status;
    private List<SsoReturnData> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SsoReturnData> getMessage() {
        return message;
    }

    public void setMessage(List<SsoReturnData> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", message=" + message +
                '}';
    }
}
