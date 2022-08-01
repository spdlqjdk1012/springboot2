package com.example.springboot2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({NullPointerException.class, ClassCastException.class})
    public String handle(Exception ex) {
        return "Exception Handle!!!";
    }

    // http://localhost:8080/login/loginProcess 호출 시 테스트 가능
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleError405(HttpServletRequest request, Exception e) {
        System.out.println("handleError405!!!!!!!!");
        ModelAndView mav = new ModelAndView("exceptHandle/error405");
        mav.addObject("exception", e);
        //mav.addObject("errorcode", "405");
        return mav;
    }
}
