package com.example.word.utils;

import cn.hutool.core.lang.Validator;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordUtils {

    private Map<String,Object> finalMap = new HashMap<>();
    private List<String> multiRowTableName = new ArrayList<>();

    /**
     * 添加基础数据
     * @param key 数据库字段名
     * @param value 数据库对应值
     */
    public void addBasic(String key,String value){
        finalMap.put(key,value);
    }

    /**
     * 添加表格数据
     * @param key 表格主名
     * @param value 表格数据集合
     * @param relationship 数据库字段名与模板列名的对应关系
     */
    public void addMultiRowTable(String key, List<Map<String,String>> value,Map<String,String> relationship){
        List<Map<String,String>> result = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            Map<String,String> temp = new HashMap<>();
            for (String s : value.get(i).keySet()) {
                temp.put(relationship.get(s),value.get(i).get(s));
            }
            result.add(temp);
        }
        multiRowTableName.add(key);
        finalMap.put(key,result);
    }

    /**
     * 列表渲染
     * @param key 列表标签名（每一行的标签默认为item）
     * @param value 列表数据集
     */
    public void addListData(String key,List<String> value){
        List<Map> result = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            Map item = new HashMap();
            item.put("item",value.get(i)+"\n");
            result.add(item);
        }
        finalMap.put(key,result);
    }

    /**
     * 上传本地url
     * @param key 图片模板名
     * @param pictureUrl 图片路径
     * @param width 宽
     * @param height 高
     */
    public void addPictureByLocal(String key, String pictureUrl, Integer width, Integer height){
        if (Validator.isNotEmpty(pictureUrl)){
            PictureRenderData picture = Pictures.ofLocal(pictureUrl).size(width,height).create();
            finalMap.put(key,picture);
        }
    }

    /**
     * 添加勾选
     * @param key 模板名
     * @param value 是否勾选
     */
    public void addCheckBox(String key,Boolean value){
        finalMap.put(key,new TextRenderData(value?"R":"*",new Style("Wingdings 2",14)));
    }

    /**
     * 根据模板word生成word文件
     * @param originalFilePath 模板word文件路径
     * @param generateFilePath 生成文件路径
     */
    public void generateWord(String originalFilePath,String generateFilePath){
        File wordTemplate = new File(originalFilePath);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure build = null;
        for (int i = 0; i < multiRowTableName.size(); i++) {
            build = Configure.builder().bind(policy, multiRowTableName.get(i)).build();
        }
        // 进行编译
        XWPFTemplate render;
        if (build == null){
            render = XWPFTemplate.compile(wordTemplate.getAbsolutePath()).render(finalMap);
        }else {
             render = XWPFTemplate.compile(wordTemplate.getAbsolutePath(), build).render(finalMap);
        }
        File word = new File(generateFilePath);
        try {
            // 生成word文档
            render.writeToFile(word.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
