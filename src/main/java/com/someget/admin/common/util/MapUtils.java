package com.someget.admin.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * map相关的工具类
 * @author oreoft
 * @date 2021-05-26 10:05
 */
public class MapUtils {

    /**
     * 这个可以方法可以根据里面具体的key来去重
     * 因为Stream的distinct去重不能根据key来去重, 只能比较元素整体是不是相同
     * @param mapping 转换
     * @param <T> 传入一个func, 获取要去重的key
     * @return 返回一个断言,表示这个是不是重复
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> mapping) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object -> seen.putIfAbsent(mapping.apply(object), Boolean.TRUE) == null;
    }
}
