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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bc.pmpheep.ueditor.define.ActionMap;

/**
 * 配置管理器<br>
 * 通过指定的配置文件来获取实例
 * 
 * @author hancong03@baidu.com
 * @author 阿信sxq--2016年9月18日
 * 
 */
public final class ConfigManager {

    private final String        rootPath;
    /**
     * 配置文件的绝对路径
     * 
     * @author 阿信sxq--2016年9月18日
     */
    private final String        configFileName;
    private JSONObject          jsonConfig       = null;
    // 涂鸦上传filename定义
    private final static String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private final static String REMOTE_FILE_NAME = "remote";

    /**
     * 构建配置文件管理的实例，通过指定的配置文件绝对路径
     * 
     * @author 阿信sxq--2016年9月18日
     * 
     * @param rootPath 应用根路径
     * @param configFileName 配置文件绝对路径
     * @throws FileNotFoundException 找不到配置文件
     * @throws IOException 文件读取出错
     */
    private ConfigManager(String rootPath, String configFileName) throws FileNotFoundException,
    IOException {

        rootPath = rootPath.replace("\\", "/");

        this.rootPath = rootPath;
        this.configFileName = configFileName;

        this.initEnv();

    }

    /**
     * 通过指定的配置文件绝对路径获取配置文件管理实例
     * 
     * @author 阿信sxq--2016年9月18日
     * 
     * @param rootPath 根路径
     * @param configFileName 配置文件绝对路径
     * @return 配置文件管理的实例
     */
    public static ConfigManager getInstance(String rootPath, String configFileName) {

        try {
            return new ConfigManager(rootPath, configFileName);
        } catch (Exception e) {
            return null;
        }

    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {

        return this.jsonConfig;

    }

    public Map<String, Object> getConfig(int type) {

        Map<String, Object> conf = new HashMap<String, Object>();
        String savePath = null;

        switch (type) {

        case ActionMap.UPLOAD_FILE:
            conf.put("isBase64", "false");
            conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
            conf.put("allowFiles", this.getArray("fileAllowFiles"));
            conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
            savePath = this.jsonConfig.getString("filePathFormat");
            break;

        case ActionMap.UPLOAD_IMAGE:
            conf.put("isBase64", "false");
            conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
            conf.put("allowFiles", this.getArray("imageAllowFiles"));
            conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
            savePath = this.jsonConfig.getString("imagePathFormat");
            break;

        case ActionMap.UPLOAD_VIDEO:
            conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
            conf.put("allowFiles", this.getArray("videoAllowFiles"));
            conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
            savePath = this.jsonConfig.getString("videoPathFormat");
            break;

        case ActionMap.UPLOAD_SCRAWL:
            conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
            conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
            conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
            conf.put("isBase64", "true");
            savePath = this.jsonConfig.getString("scrawlPathFormat");
            break;

        case ActionMap.CATCH_IMAGE:
            conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
            conf.put("filter", this.getArray("catcherLocalDomain"));
            conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
            conf.put("allowFiles", this.getArray("catcherAllowFiles"));
            conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
            savePath = this.jsonConfig.getString("catcherPathFormat");
            break;

        case ActionMap.LIST_IMAGE:
            conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
            conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
            conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
            break;

        case ActionMap.LIST_FILE:
            conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
            conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
            conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
            break;

        }

        conf.put("savePath", savePath);
        conf.put("rootPath", this.rootPath);

        return conf;

    }

    /**
     * 读取指定的配置文件来初始化
     * 
     * @author 阿信sxq--2016年9月18日
     * 
     * @throws FileNotFoundException 找不到指定的配置文件
     * @throws IOException 文件读取出错
     */
    private void initEnv() throws FileNotFoundException, IOException {

        String configContent = this.readFile(configFileName);

        try {
            JSONObject jsonConfig = new JSONObject(configContent);
            this.jsonConfig = jsonConfig;
        } catch (Exception e) {
            this.jsonConfig = null;
        }

    }

    private String[] getArray(String key) {

        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];

        for (int i = 0, len = jsonArray.length(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;

    }

    private String readFile(String path) throws IOException {

        StringBuilder builder = new StringBuilder();

        try {

            InputStreamReader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
            BufferedReader bfReader = new BufferedReader(reader);

            String tmpContent = null;

            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }

            bfReader.close();

        } catch (UnsupportedEncodingException e) {
            // 忽略
        }

        return this.filter(builder.toString());

    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {

        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");

    }

}
