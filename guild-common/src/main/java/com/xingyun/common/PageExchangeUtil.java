package com.xingyun.common;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.bean.BeanUtil;

/**
 * 分页返回信息类型转化
 */
public class PageExchangeUtil {

    private PageExchangeUtil() {
    }

    /**
     * 转换 list
     *
     * @param sourceList
     *            源集合
     * @param destinationClass
     *            目标类型
     * @return 目标集合
     */
    public static <T, E> List<T> toBeanList(Collection<E> sourceList, Class<T> destinationClass) {
        if (sourceList == null || sourceList.isEmpty() || destinationClass == null) {
            return Collections.emptyList();
        }
        return sourceList.parallelStream().filter(Objects::nonNull)
            .map(source -> BeanUtil.toBean(source, destinationClass)).collect(Collectors.toList());
    }

    /**
     * 转化 PageResult 对象
     *
     * @param page
     *            分页对象
     * @param destinationClass
     *            目标类型
     * @return 目录分页对象
     */
    public static <T, E> PageResult<T> toPageResult(IPage<E> page, Class<T> destinationClass) {
        if (page == null || destinationClass == null) {
            return null;
        }
        PageResult<T> newPage = new PageResult();
        newPage.setTotal(page.getTotal());
        List<E> list = page.getRecords();
        if (list.isEmpty()) {
            return newPage;
        }
        List<T> destinationList = toBeanList(list, destinationClass);
        newPage.setRows(destinationList);
        return newPage;
    }

}
