package com.knowdata.framework.core.model.dto;

import com.alibaba.fastjson2.JSON;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XResponseTest {
    /**
     * 测试常见的 pageResponse 嵌套到 XResponse 的数据结构
     */
    @Test
    public void pageResponseEmbeddingXResponseTest(){
        User user = new User(1L, "jack");
        User user2 = new User(2L, "mike");

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        PageResponse<User> pageResponse = new PageResponse( 1, users.size(), users, users.size() );
        XResponse<PageResponse<User>> xResponse = XResponse.success( pageResponse );
        //log.info("xResponse:{}", JSON.toJSONString(xResponse));//xResponse:{"data":{"currentPage":1,"currentSize":2,"pageSize":2,"records":[{"id":1,"name":"jack"},{"id":2,"name":"mike"}],"totalCount":2,"totalPages":1},"status":true}
        log.info("xResponse:{}", xResponse);//xResponse:XResponse{status=true, data=PageResponse{totalCount=2, totalPages=1, pageSize=2, currentPage=1, records=2, currentSize=2, extData=null}, errorCode='null', errorMessage='null'}
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private Long id;
    private String name;
}
