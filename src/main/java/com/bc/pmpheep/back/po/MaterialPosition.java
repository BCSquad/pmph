package com.bc.pmpheep.back.po;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 *
 */
@Alias("MaterialPosition")
public class MaterialPosition implements Serializable {
    private Long id;
    private Long materailId;
    private String positionCode ;


    public MaterialPosition(Long id, Long materailId, String positionCode) {
        this.id = id;
        this.materailId = materailId;
        this.positionCode = positionCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterailId() {
        return materailId;
    }

    public void setMaterailId(Long materailId) {
        this.materailId = materailId;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
}
