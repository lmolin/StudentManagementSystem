package org.perscholas.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(FileException.class)
    public ModelAndView handleException(FileException fileException, RedirectAttributes redirect) {
        var mav = new ModelAndView();
        mav.addObject("message", fileException.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
