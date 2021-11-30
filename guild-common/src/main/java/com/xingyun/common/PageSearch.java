package com.xingyun.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import lombok.Data;

/**
 * @author xingyun
 * @date 2021/3/17
 */
@Data
public class PageSearch {

    private Integer pageNo = 1;

    private Integer pageSize = 15;

    /**
     * 构建分页参数
     * @return
     */
    public  IPage buildPage(){
        return PageDto.of(this.pageNo, this.pageSize);
    }

}
