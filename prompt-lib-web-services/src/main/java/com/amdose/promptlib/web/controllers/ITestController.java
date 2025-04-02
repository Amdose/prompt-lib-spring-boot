package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.web.constants.UriConstants;
import com.amdose.promptlib.web.payloads.TestRequest;
import com.amdose.promptlib.web.payloads.TestResponse;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Alaa Jawhar
 */
public interface ITestController {

    @PostMapping(UriConstants.SUCCESS_TEST)
    @Cacheable(value = "test", keyGenerator = "localizedGenerator")
    TestResponse successTest(@RequestBody @Valid TestRequest testRequest);

    @PostMapping(UriConstants.FAILURE_TEST)
    TestResponse failureTest(@RequestBody @Valid TestRequest testRequest);
}
