package com.bc.pmpheep.back.po;


import java.io.Serializable;

public class Datas implements Serializable {

    private String id;
    private String operation;
    private String type;
    private String uuid;
    private String oldName;
    private String newName;
    private String oldParentUuid;
    private String oldParentPath;
    private String newParentUuid;
    private String newParentPath;
    private UtsNode utsNode;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getOperation() {
        return operation;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
    public String getOldName() {
        return oldName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    public String getNewName() {
        return newName;
    }

    public void setOldParentUuid(String oldParentUuid) {
        this.oldParentUuid = oldParentUuid;
    }
    public String getOldParentUuid() {
        return oldParentUuid;
    }

    public void setOldParentPath(String oldParentPath) {
        this.oldParentPath = oldParentPath;
    }
    public String getOldParentPath() {
        return oldParentPath;
    }

    public void setNewParentUuid(String newParentUuid) {
        this.newParentUuid = newParentUuid;
    }
    public String getNewParentUuid() {
        return newParentUuid;
    }

    public void setNewParentPath(String newParentPath) {
        this.newParentPath = newParentPath;
    }
    public String getNewParentPath() {
        return newParentPath;
    }

    public void setUtsNode(UtsNode utsNode) {
        this.utsNode = utsNode;
    }
    public UtsNode getUtsNode() {
        return utsNode;
    }
}