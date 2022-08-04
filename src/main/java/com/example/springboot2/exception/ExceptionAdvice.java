package com.example.springboot2.exception;

import com.example.springboot2.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> badRequestException(BadRequestException e) {
        return new ResponseEntity<>(  ResponseDTO.setFail(-100,e.getMessage()) , HttpStatus.BAD_REQUEST);
    }







    // 버리는 것
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
