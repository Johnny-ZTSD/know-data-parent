package com.knowdata.framework.core.model.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.knowdata.framework.core.model.base.BaseObject;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author johnnyzen
 * @version v2.0
 * @project know-data-parent
 * @create-date 2022/9/6 13:14
 * @description 分页基础类
 */

public class BasePage extends BaseObject {
    public final static Integer MIN_CURRENT_PAGE = 1;
    public final static Integer MAX_PAGE_SIZE = Integer.MAX_VALUE;

    private static Integer DEFAULT_CURRENT_PAGE = MIN_CURRENT_PAGE;

    private static Integer DEFAULT_PAGE_SIZE = 15;

    @ApiModelProperty(
            value = "页码 (含义: 第 currentPage 页; 起始值:=默认值:=1)",
            example = "10",
            hidden = false
    )
    @JsonAlias({"currentPage", "index" }) // 反序列化(前端/string-->后端/bean)时，兼容多种的入参命名
    private Integer currentPage; // = DEFAULT_CURRENT_PAGE;

    @ApiModelProperty(
            value = "分页大小 (含义: 本页码最多 pageSize 条记录; 起始值 := 默认值:= 15)",
            example = "15",
            hidden = false
    )
    @JsonAlias({"pageSize", "size" })
    private Integer pageSize; // = DEFAULT_PAGE_SIZE;

    public BasePage() {

    }

    public BasePage(Integer currentPage, Integer pageSize) {
        setCurrentPage(currentPage);
        setPageSize(pageSize);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setCurrentPage(Integer currentPage) {
        if(currentPage == null){
            this.currentPage = null;
        } else {
            this.currentPage = currentPage<1?DEFAULT_CURRENT_PAGE:currentPage;
        }
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize == null){
            this.pageSize = null;
        } else {
            this.pageSize = pageSize<1?DEFAULT_PAGE_SIZE:pageSize;
        }
    }

    public static Page convertTo(BasePage basePage){
        return new Page<>(basePage.getCurrentPage(), basePage.getPageSize());
    }

}