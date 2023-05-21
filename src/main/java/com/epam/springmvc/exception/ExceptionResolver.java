package com.epam.springmvc.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;


public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            HttpStatus httpStatus;
            ModelAndView modelAndView;

            switch (ex.getClass().getSimpleName()) {
                case "NotFoundException":
                    httpStatus = HttpStatus.NOT_FOUND;
                    modelAndView = handleNotFoundException(ex);
                    break;
                case "NotSupportedException":
                    httpStatus = HttpStatus.NOT_IMPLEMENTED;
                    modelAndView = handleNotSupportedException(ex);
                    break;
                case "ValidationException":
                    httpStatus = HttpStatus.BAD_REQUEST;
                    modelAndView = handleValidationException(ex);
                    break;
                default:
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    modelAndView = handleDefaultException(ex);
                    break;
            }

            response.setStatus(httpStatus.value());
            return modelAndView;
        } catch (Exception handlerException) {
            handlerException.printStackTrace();
        }
        return null;
    }

    private ModelAndView handleNotFoundException(Exception ex) {
        return buildErrorModelAndView(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ModelAndView handleNotSupportedException(Exception ex) {
        return buildErrorModelAndView(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }

    private ModelAndView handleValidationException(Exception ex) {
        return buildErrorModelAndView(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ModelAndView handleDefaultException(Exception ex) {
        return buildErrorModelAndView(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ModelAndView buildErrorModelAndView(String message, HttpStatus httpStatus) {
        var modelAndView = new ModelAndView();
        modelAndView.setStatus(httpStatus);
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}