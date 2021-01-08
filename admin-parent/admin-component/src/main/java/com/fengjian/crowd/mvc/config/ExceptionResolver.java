package com.fengjian.crowd.mvc.config;

import com.fengjian.crowd.constant.Constant;
import com.fengjian.crowd.exception.LoginAcctAlreadyInUseException;
import com.fengjian.crowd.exception.LoginAcctAlreadyInUseExceptionForUpdate;
import com.fengjian.crowd.exception.LoginFailedException;
import com.fengjian.crowd.util.CrowdUtil;
import com.fengjian.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(value = LoginAcctAlreadyInUseExceptionForUpdate.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseExceptionForUpdate exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-edit";
        return commonResolveException(viewName, exception, request, response);
    }
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResolveException(viewName, exception, request, response);
    }

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolveException(viewName, exception, request, response);
    }

//    @ExceptionHandler(value = NullPointerException.class)
//    public ModelAndView resolveNullPointException(
//            NullPointerException exception,
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        String viewName = Constant.ERROR_MODEL_AND_VIEW;
//        return commonResolveException(viewName, exception, request, response);
//    }

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException(
            ArithmeticException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String viewName = Constant.ERROR_MODEL_AND_VIEW;
        return commonResolveException(viewName, exception, request, response);
    }

    private ModelAndView commonResolveException(
            String viewName,
            Exception exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        if (CrowdUtil.judgeRequestType(request)) {
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            String json = new Gson().toJson(resultEntity);
            response.getWriter().write(json);
            return null;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }


}
