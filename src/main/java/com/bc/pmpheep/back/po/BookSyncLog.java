package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;


@SuppressWarnings("serial")
@Alias("BookSyncLog")
public class BookSyncLog implements Serializable {
    private Long id;
    private Boolean increment;
    private String synchronizationType ;
    private Timestamp syncGmt;

    public BookSyncLog(Boolean increment, String synchronizationType, Timestamp syncGmt) {
        this.increment = increment;
        this.synchronizationType = synchronizationType;
        this.syncGmt = syncGmt;
    }

    public BookSyncLog(Long id, Boolean increment, String synchronizationType, Timestamp syncGmt) {
        this.id = id;
        this.increment = increment;
        this.synchronizationType = synchronizationType;
        this.syncGmt = syncGmt;
    }

    public BookSyncLog() {
    }

    public String getSynchronizationType() {
        return synchronizationType;
    }

    public void setSynchronizationType(String synchronizationType) {
        this.synchronizationType = synchronizationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIncrement() {
        return increment;
    }

    public void setIncrement(Boolean increment) {
        this.increment = increment;
    }



    public Timestamp getSyncGmt() {
        return syncGmt;
    }

    public void setSyncGmt(Timestamp syncGmt) {
        this.syncGmt = syncGmt;
    }



}
