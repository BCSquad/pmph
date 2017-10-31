/**
 * <pre>
 * Copyright (c) 2016, 2016 阿信sxq(songxinqiang@vip.qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 */
/*
 * 作者：宋信强(阿信sxq, songxinqiang@vip.qq.com, http://my.oschina.net/songxinqiang )
 * <p>
 * 众里寻她千百度, 蓦然回首, 那人却在灯火阑珊处.
 * </p>
 */
package com.bc.pmpheep.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bc.pmpheep.ueditor.define.ActionMap;
import com.bc.pmpheep.ueditor.define.AppInfo;
import com.bc.pmpheep.ueditor.define.BaseState;
import com.bc.pmpheep.ueditor.define.State;
import com.bc.pmpheep.ueditor.hunter.FileManager;
import com.bc.pmpheep.ueditor.hunter.ImageHunter;
import com.bc.pmpheep.ueditor.upload.Uploader;

/**
 * 
 * @author 阿信sxq--2016年9月18日
 * 
 */
public class ActionEnter {

    private HttpServletRequest request       = null;

    private String             rootPath      = null;

    private String             actionType    = null;

    private ConfigManager      configManager = null;

    /**
     * 入口，配置是通过指定的配置文件来加载的，指定的配置文件应该是文件的绝对路径
     * 
     * @author 阿信sxq--2016年9月18日
     * 
     * @param request 用户请求
     * @param rootPath 应用根目录
     * @param configFileName 配置文件绝对路径
     */
    public ActionEnter(HttpServletRequest request, String rootPath, String configFileName) {

        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter("action");
        this.configManager = ConfigManager.getInstance(this.rootPath, configFileName);
    }

    public String exec() {

        String callbackName = this.request.getParameter("callback");

        if (callbackName != null) {

            if (!validCallbackName(callbackName)) {
                return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
            }

            return callbackName + "(" + this.invoke() + ");";

        } else {
            return this.invoke();
        }

    }

    public String invoke() {

        if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }

        if (this.configManager == null || !this.configManager.valid()) {
            return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
        }

        State state = null;

        int actionCode = ActionMap.getType(this.actionType);

        Map<String, Object> conf = null;

        switch (actionCode) {

        case ActionMap.CONFIG:
            return this.configManager.getAllConfig().toString();

        case ActionMap.UPLOAD_IMAGE:
        case ActionMap.UPLOAD_SCRAWL:
        case ActionMap.UPLOAD_VIDEO:
        case ActionMap.UPLOAD_FILE:
            conf = this.configManager.getConfig(actionCode);
            state = new Uploader(request, conf).doExec();
            break;

        case ActionMap.CATCH_IMAGE:
            conf = configManager.getConfig(actionCode);
            String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
            state = new ImageHunter(conf).capture(list);
            break;

        case ActionMap.LIST_IMAGE:
        case ActionMap.LIST_FILE:
            conf = configManager.getConfig(actionCode);
            int start = this.getStartIndex();
            state = new FileManager(conf).listFile(start);
            break;

        }

        return state.toJSONString();

    }

    public int getStartIndex() {

        String start = this.request.getParameter("start");

        try {
            return Integer.parseInt(start);
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * callback参数验证
     */
    public boolean validCallbackName(String name) {

        if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
            return true;
        }

        return false;

    }

}
