package com.stl.iminds.commons.security.controller;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stl.core.base.utils.Response;
import com.stl.iminds.commons.security.utils.CommonConstant;

@RestController
public class NotificationErrorContoller implements ErrorController {

    private static final String ERROR_MAPPING = "/error";

    @RequestMapping(value = ERROR_MAPPING)
    public ResponseEntity<String> error(HttpServletRequest httpServletRequest) {
    	
    	Response response1 = new Response(CommonConstant.INVALID_REQUEST_ERROR_CODE,(String)httpServletRequest.getAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE),null);
    	return new ResponseEntity(response1,HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_MAPPING;
    }
}