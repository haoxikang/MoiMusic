package com.example.moimusic.utils;

import java.util.HashMap;

/**
 * Created by qqq34 on 2016/1/13.
 */
public class ErrorList {
    HashMap<Integer,String> hashMap = new HashMap<Integer, String>();

    public ErrorList() {
        hashMap.put(9001,"Application Id为空，请初始化。");
        hashMap.put(9002,"解析返回数据出错");
        hashMap.put(9003,"上传文件出错");
        hashMap.put(9004,"文件上传失败");
        hashMap.put(9005,"批量操作只支持最多50条");
        hashMap.put(9006,"objectId为空");
        hashMap.put(9007,"文件大小超过10M");
        hashMap.put(9008,"上传文件不存在");
        hashMap.put(9009,"没有缓存数据");
        hashMap.put(9010,"网络超时");
        hashMap.put(9011,"BmobUser类不支持批量操作");
        hashMap.put(9012,"上下文为空");
        hashMap.put(9013,"BmobObject（数据表名称）格式不正确");
        hashMap.put(9014,"第三方账号授权失败");
        hashMap.put(9015,"其他错误均返回此code");
        hashMap.put(9016,"无网络连接，请检查您的手机网络");
        hashMap.put(9017,"与第三方登录有关的错误，具体请看对应的错误描述");
        hashMap.put(9018,"参数不能为空");
        hashMap.put(9019,"格式不正确：手机号码、邮箱地址、验证码");
        hashMap.put(101,"账号或者密码错误");
    }
    public String getErrorMsg(int code){
        if (hashMap.get(code)!=null){
            return hashMap.get(code);
        }else return "未知错误";

    }
}
