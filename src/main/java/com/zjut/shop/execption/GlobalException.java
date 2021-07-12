package com.zjut.shop.execption;

import com.zjut.shop.enums.ResultStatus;
import com.zjut.shop.response.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(ShopRuntimeException.class)
    public ResponseEntity<?> shopRuntimeExceptionHandler(HttpServletRequest request, ShopRuntimeException ex) {
        log.info("处理:{}的ShopRuntimeException异常", request.getRequestURI(), ex);
        return new ResponseEntity<>(BaseResult.handlerFailure(ex.getMessage(), ex.getCode()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> shopRuntimeExceptionHandler(HttpServletRequest request, Exception ex) {
        log.info("处理:{}的Exception异常", request.getRequestURI(), ex);
        return new ResponseEntity<>(BaseResult.handlerFailure(ResultStatus.INTERNAL_EXEC.getMsg(), ResultStatus.INTERNAL_EXEC.getCode()), HttpStatus.OK);
    }
}
