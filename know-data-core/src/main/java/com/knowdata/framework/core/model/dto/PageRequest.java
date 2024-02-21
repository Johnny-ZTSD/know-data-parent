package com.knowdata.framework.core.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author johnnyzen
 * @version v2.0
 * @project know-data-parent
 * @create-date 2022/9/6 16:00
 * @description 分页请求对象
 * @sample
 */

@ApiModel(value = "分页请求对象", description = "分页请求 Bean")
public class PageRequest<T> extends BasePage {

    @ApiModelProperty(
            value = "分页请求参数",
            example = "",
            hidden = false
    )
    private T params;

    public PageRequest(){

    }

    public PageRequest(Integer currentPage, Integer pageSize) {
        this(currentPage, pageSize, null);
    }

    public PageRequest(Integer currentPage, Integer pageSize, T params) {
        super(currentPage, pageSize);
        this.params = params;
    }

    /**
     * 是否分页
     * @return true(分页) / false(不分页)
     * @description 通常用于判断用户的 request 是否想要获得分页结果
     */
    public boolean isNeedPaging() {
        if(this.getCurrentPage() != null && (this.getPageSize() != null) ){
            if(this.getCurrentPage().equals(MIN_CURRENT_PAGE) && this.getPageSize().equals(MAX_PAGE_SIZE)){
                return false;
            }
            return true;
        }
        return false;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    @Override
    public Integer getPageSize(){
        return super.getPageSize();
    }

    @Override
    public void setPageSize(Integer pageSize){
        super.setPageSize(pageSize);
    }

    @Override
    public Integer getCurrentPage(){
        return super.getCurrentPage();
    }

    @Override
    public void setCurrentPage(Integer currentPage){
        super.setCurrentPage(currentPage);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageSize=" + super.getPageSize() +
                "currentPage=" + super.getCurrentPage() +
                "params=" + params +
                '}';
    }
}
