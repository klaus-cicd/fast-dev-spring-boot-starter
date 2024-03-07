package com.klaus.fd.web;

import com.klaus.fd.comm.TestEntity;
import com.klaus.fd.response.Result;
import com.klaus.fd.utils.JsonUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Silas
 */
@RestController
@RequestMapping("/api/json")
public class JsonController {


    @PostMapping
    public Result<TestEntity> testJsonA(@RequestBody TestEntity testEntity) {
        return Result.ok(JsonUtil.parseToObject(JsonUtil.toJsonStr(testEntity), TestEntity.class));
    }


}
