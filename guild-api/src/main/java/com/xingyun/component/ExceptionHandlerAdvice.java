package com.xingyun.component;

import com.xingyun.common.BusinessException;
import com.xingyun.common.Result;
import com.xingyun.enums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 最爱吃小鱼
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private Logger logger = LoggerFactory.getLogger("baseLog");


    // 异常的拦截处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("URL:{}统一异常处理。", request.getRequestURI(), e);

        return Result.forFail(ResultCodeEnum.SYSTEM_BUSY);
    }

    // 异常的拦截处理
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result handleBusinessException(BusinessException ex) {
        String message = ex.getMessage();
        logger.error(message, ex);
        return Result.forFail(ex.getMessage());
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.forFail(e.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class})
    @ResponseBody
    public Result badRequest(ServletException ex) {
        return Result.forFail(ex.getMessage());
    }

    /**
     * 抓取method参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            for (ConstraintViolation constraintViolation : constraintViolations) {
                return Result.forFail(constraintViolation.getMessage());
            }
        }
        String message = ex.getMessage();
        logger.error(message, ex);
        return Result.forFail(ex.getMessage());
    }


    /**
     * 抓取 @Valid @RequestBody bean 异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        String message = ex.getMessage();
        logger.error(message, ex);
        return paramCheckException(ex, objectErrors);
    }

    /**
     * 抓取 @Valid @RequestBody bean 异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result handleBindException(BindException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        String message = ex.getMessage();
        logger.error(message, ex);
        return paramCheckException(ex, objectErrors);
    }


    private Result paramCheckException(Exception ex, List<ObjectError> objectErrors) {
        if (!CollectionUtils.isEmpty(objectErrors)) {
            FieldError fieldError;
            List<ErrBean> errMessages = new ArrayList<>();
            for (ObjectError objectError : objectErrors) {
                return Result.forFail(objectError.getDefaultMessage());
            }
        }
        String message = ex.getMessage();
        logger.error(message, ex);
        return Result.forFail(ex.getMessage());
    }

    /*  private DataEntityResponse paramFailed(List<ErrBean> errMessages) {
          DataEntityResponse DataEntityResponse = new DataEntityResponse(ResponseCodeEnum.PARAM_INVALID);
          DataEntityResponse.setData(errMessages);
          return DataEntityResponse;
      }*/
    private String getParamName(Path propertyPath) {
        String paramName = null;
        for (Path.Node s : propertyPath) {
            paramName = s.getName();
        }
        return paramName;
    }

    class ErrBean {
        private String param;
        private String errmsg;

        public ErrBean(String param, String errmsg) {
            this.param = param;
            this.errmsg = errmsg;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }
    }

}
