package com.example.article;

import java.util.List;
/**
 * 接口类，抽象方法用来获取过滤后的数据
 * @author 邹奇
 *
 */
public interface FilterListener {
    void getFilterData(List<String> list);// 获取过滤后的数据
}
