package com.knowdata.framework.core.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author johnnyzen
 * @version v2.0
 * @project know-data-parent
 * @create-date 2022/9/6 13:15
 * @description 分页响应对象
 * @sample
 *      totalRecords=12 / pageSize=5 then:
 *              totalPages=(totalRecords/pageSize) + 1 = 3
 *          if pageIndex(startIndex=1)=3 then:
 *              currentSize = pageIndex>=totalPages ? ( totalRecords-pageSize*(pageSize-1) ) : pageSize = records = 2
 *              (fromIndex, endIndex] = (10, 12]
 *                  fromIndex = pageSize*(pageIndex-1) = 5*2 = 10
 *                  endIndex = fromIndex + currentSize = 10 + 2 = 12
 * @reference-doc
 *  如何在Java中进行JSON序列化时忽略字段？ - nhooo.com - https://www.nhooo.com/note/qa012v.html
 *  使用Jackson和Spring Boot的条件JsonProperty - 一点教程 - http://www.yiidian.com/questions/256690
 */

@Data
@ApiModel(value = "分页响应对象", description = "分页响应 Bean")
public class PageResponse<T> extends BasePage {
    @ApiModelProperty(
            value = "总记录数 (含义: 本次请求共有 totalRecords 条记录 )",
            example = "143",
            hidden = false
    )
    @JsonAlias({"totalCount", "totalRecords" }) // 反序列化
    private Integer totalCount;

    @ApiModelProperty(
            value = "总页面数 (含义: 本次请求共有 totalPages 页 )",
            example = "10",
            hidden = false
    )
    private Integer totalPages;

    @ApiModelProperty(
            value = "响应内容 (含义: 本页响应的数据集)",
            hidden = false
    )
    // T: 一般为集合类的泛型，例如: Map<String, Object> / List<Map<String, Object>> / List<Object>
    private List<T> records;

    @ApiModelProperty(
            value = "非分页内的响应数据",
            hidden = false
    )
    private Map<String, Object> extData;

    @ApiModelProperty(
            value = "当前页面记录数 (含义: 本页面响应了 currentSize 条记录)",
            example = "3",
            hidden = false
    )
    private Integer currentSize;

    public PageResponse(){

    }
    public PageResponse(Integer currentPage, Integer pageSize, List<T> records, Integer totalCount) {
        this(currentPage, pageSize, records, totalCount, null);
    }

    public PageResponse(BasePage page,  List<T> records, Integer totalCount){
        this(page.getCurrentPage(), page.getPageSize(), records, totalCount, null);
    }

    public PageResponse(Integer currentPage, Integer pageSize, List<T> records, Integer totalCount, Map<String, Object> extData) {
        super(currentPage, pageSize);
        this.totalCount = totalCount;
        this.records = records;

        this.extData = extData;

        setTotalPages(super.getPageSize(), this.totalCount);
        initCurrentSize();
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    private int getTotalPages(Integer totalRecords, Integer size) {
        return this.totalPages;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    private void setTotalPages(Integer pageSize, Integer totalCount) {
        this.totalPages = calculateTotalPages(pageSize, totalCount);
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
        initCurrentSize();
    }

    private void initCurrentSize() {
        if(this.records != null){
            this.currentSize = this.records.size();
        } else {
            this.currentSize = 0;
        }
    }

    /**
     * 对外提供工具方法,计算总共页面数
     * @return
     */
    public static int calculateTotalPages(int pageSize, int totalRecords){
        pageSize = pageSize < 1 ? 1 : pageSize;
        int flag = totalRecords % pageSize == 0 ? 0 : 1;
        // 向上取整
        return (totalRecords / pageSize) + flag;
    }

    /**
     * 对外提供工具方法,计算当前页面理论的实际记录数
     * @note 根据 totalRecords、pageIndex、pageSize 计算出的、理论的当前页面大小: 可能为0(异常值)
     * currentSize = pageIndex>=totalPages ? ( totalRecords-pageSize*(pageSize-1) ) : pageSize = records = 2
     * @param pageIndex (startIndex=1)
     */
    public static int calculateCurrentSize(int pageIndex, int pageSize, int totalRecords){
        int totalPages = calculateTotalPages(pageSize, totalRecords);
        int currentSize = pageIndex>=totalPages ? ( totalRecords-pageSize*(pageIndex-1) ) : pageSize;
        currentSize = currentSize < 0? 0:currentSize;
        return currentSize;
    }

    /**
     * @function (伪)分页处理
     *  := 不通过 数据库分页
     * @param totalRecordsList
     * @param pageIndex (startIndex=1)
     * @param pageSize
     * @note
     *  List.subList(fromIndex, endIndex) := [fromIndex, endIndex) , but startIndex=0
     */
    public static <T> PageResponse<T> fakePagingHandle(Integer pageIndex, Integer pageSize, List<T> totalRecordsList){
        if(pageIndex==null || pageSize==null){
            pageIndex = MIN_CURRENT_PAGE;
            pageSize = MAX_PAGE_SIZE;
        }
        int totalRecords = totalRecordsList.size();
        //根据 totalRecords、pageIndex、pageSize 计算出的、理论的当前页面大小: 可能为0(异常值)
        int currentSize = PageResponse.calculateCurrentSize(pageIndex, pageSize, totalRecords);

        List<T> records = null;
        if(currentSize==0){//0为异常值 => 请求的 pageSize 、 pageIndex 超出正常范围
            records = new ArrayList<>();
        } else {
            //[fromIndex, endIndex)
            int fromIndex = (pageIndex-1)*pageSize; // note: subList.fromIndex.startIndex=0
            int endIndex = fromIndex+currentSize;
            records = totalRecordsList.subList(fromIndex, endIndex);
        }
        PageResponse pageResponse = new PageResponse(pageIndex, pageSize, records, totalRecords);
        return pageResponse;
    }

    /**
     * 分页处理
     * @param isReuqestNeedPaging
     * @param isReuqestSQLSupportAutoPaging
     * @param isParseMethodChangeOriginResponsePageCurrentSize
     * @param pageRequest
     * @param oldPageResponse
     * @param newRecords
     * @return
     */
    public static <T> PageResponse pagingHandle(
            boolean isReuqestNeedPaging,
            boolean isReuqestSQLSupportAutoPaging,
            boolean isParseMethodChangeOriginResponsePageCurrentSize,
            PageRequest pageRequest,
            PageResponse oldPageResponse,
            List<Map<String, T>> newRecords
    ){
        if((pageRequest == null) || (pageRequest.getPageSize()==null && pageRequest.getCurrentPage()==null)){// 避免空指针异常
            pageRequest = new PageRequest<>();
            pageRequest.setCurrentPage(MIN_CURRENT_PAGE);
            pageRequest.setPageSize(MAX_PAGE_SIZE);
        }
        if(isReuqestNeedPaging == false){ // step1 request 不要求分页
            if(isParseMethodChangeOriginResponsePageCurrentSize == false){
                return new PageResponse(oldPageResponse.getCurrentPage(), oldPageResponse.getPageSize(), newRecords, oldPageResponse.getTotalCount());
            } else {
                //伪分页
                return PageResponse.fakePagingHandle(1, newRecords.size(), newRecords);
            }
        } else { // request 要求分页
            if(isReuqestSQLSupportAutoPaging==true){ // step2 原业务 SQL 支持自动分页
                if(isParseMethodChangeOriginResponsePageCurrentSize == false){// step3 本 process 的 parse 方法 未 改变 原 page 的一级元素数量
                    return new PageResponse(pageRequest.getCurrentPage(), pageRequest.getPageSize(), newRecords, oldPageResponse.getTotalCount());
                } else {
                    //伪分页
                    return PageResponse.fakePagingHandle(pageRequest.getCurrentPage(), pageRequest.getPageSize(), newRecords);
                }
            } else {// 原业务 SQL 不支持自动分页 => 只能伪分页
                //伪分页
                return PageResponse.fakePagingHandle(pageRequest.getCurrentPage(), pageRequest.getPageSize(), newRecords);
            }
        }
    }

    public Map<String, Object> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, Object> extData) {
        this.extData = extData;
    }


    /**
     * toString
     * @param isDisplayRecords
     *  isDisplayRecords = false - 不显示 records 的具体内容(仅显示 条数)
     * @return
     */
    public String toString(boolean isDisplayRecords){
        return "PageResponse{" +
                "totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", pageSize=" + getPageSize() +
                ", currentPage=" + getCurrentPage() +
                ", records=" + (isDisplayRecords==false?(new Integer(records.size())).toString() : records.toString() ) +
                ", currentSize=" + currentSize +
                ", extData=" + extData +
                '}';
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
