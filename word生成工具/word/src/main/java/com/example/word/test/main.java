package com.example.word.test;

import com.example.word.utils.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        WordUtils wordUtils = new WordUtils();
        wordUtils.addBasic("姓名","张三");
        HashMap<String, String> workItem = new HashMap<>();
        workItem.put("id", "1");
        workItem.put("company", "四川有限公司");
        workItem.put("time",  10 + "小时");
        workItem.put("salary", "800");
        workItem.put("name", "项目经理");
        workItem.put("office", "java工程师");
        HashMap<String, String> workItem1 = new HashMap<>();
        workItem1.put("id", "2");
        workItem1.put("company", "四川有限公司");
        workItem1.put("time",  10 + "小时");
        workItem1.put("salary", "800");
        workItem1.put("name", "项目经理");
        workItem1.put("office", "java工程师");
        List<Map<String,String>> target = new ArrayList<>();
        target.add(workItem);
        target.add(workItem1);
        Map<String,String> temp = new HashMap<>();
        temp.put("id","序号");
        temp.put("company","公司名");
        temp.put("time","工作时长");
        temp.put("salary","salary");
        temp.put("name","联系");
        temp.put("office","岗位");
        List<String> strings = new ArrayList<>();
        strings.add("nnnnnn");
        strings.add("kkkkkk");
        wordUtils.addListData("申明项列表",strings);
        wordUtils.addPictureByLocal("员工签字图片0","D:\\告警.png",40,30);
        wordUtils.addMultiRowTable("工作经历列表",target,temp);
        wordUtils.addCheckBox("test",false);
        wordUtils.generateWord("D:\\模板.docx","D:\\生成.docx");
    }
}
