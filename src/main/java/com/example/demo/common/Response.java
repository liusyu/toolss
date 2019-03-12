package com.example.demo.common;

import com.example.demo.common.exception.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

public class Response<T>  {
	
	public static final String ERR_LOGIN = "10001";
	public static final String ERR_ACCESSDENIED = "10002";
	public static final String ERR_UNAUTHORIZED = "10003";
	public static final String ERR_RELOGIN = "10004";
	
	@SuppressWarnings("rawtypes")
	public final static Response SUCCEED = new Response<>();
	
	@SuppressWarnings("rawtypes")
	public final static Response LOGIN = new Response<>(ERR_LOGIN, "login");
	
	@SuppressWarnings("rawtypes")
	public final static Response ACCESSDENIED = new Response<>(ERR_ACCESSDENIED, "authentication failed");//403
	
	@SuppressWarnings("rawtypes")
	public final static Response UNAUTHORIZED = new Response<>(ERR_UNAUTHORIZED, "not authorized");//401

	private boolean success;
	
	private String errorCode;
	
	private String message;

	private long total;
	
	protected List<T> entities;
	
	protected T entity;
	
	public Response() {
		this(ResultCode.SUCCESS.getCode(), "success");
		this.success = true;
	}
	
	public Response(String message) {
		this(ResultCode.SUCCESS.getCode(), message);
		this.success = true;
	}
	
	public Response(String errorCode, String errorMessage) {
		this.setErrorCode(errorCode);
		this.setMessage(errorMessage);
	}
	
	public static Response createInfoError(String message) {
		return new Response(ResultCode.InfoException.getCode(), message);
	}
	
	public static Response createParamError() {
		return createParamError("");
	}
	
	public static Response createParamError(String message) {
		return new Response(ResultCode.ParamException.getCode(), StringUtils.isNotEmpty(message) ? message : ResultCode.ParamException.getMessage());
	}
	
	public static Response createParamError(BindingResult result) {
		return createParamError("", result);
	}
	
	public static Response createParamError(String message, BindingResult result) {
		Response response = new Response(ResultCode.ParamException.getCode(), StringUtils.isNotEmpty(message) ? message : ResultCode.ParamException.getMessage());
		List<ValidationError> errors = new ArrayList<ValidationError>();
        for (FieldError err : result.getFieldErrors()) {
            errors.add(new ValidationError(err.getObjectName(), err.getField(), err.getDefaultMessage()));
        }
        response.setEntities(errors);
        return response;
	}
	
	public static Response create4Exception(Exception ex) {
		if(ex instanceof BindException) {
			Response response = new Response<>(ResultCode.ParamException.getCode(), ResultCode.ParamException.getMessage());
			List<ValidationError> errors = new ArrayList<ValidationError>();
	        for (FieldError err : ((BindException)ex).getFieldErrors()) {
	            errors.add(new ValidationError(err.getObjectName(), err.getField(), err.getDefaultMessage()));
	        }
	        response.setEntities(errors);
	        return response;
		}
		
		if(ex instanceof MethodArgumentNotValidException) {
			Response response = new Response<>(ResultCode.ParamException.getCode(), ResultCode.ParamException.getMessage());
			List<ValidationError> errors = new ArrayList<ValidationError>();
	        for (FieldError err : ((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors()) {
	            errors.add(new ValidationError(err.getObjectName(), err.getField(), err.getDefaultMessage()));
	        }
	        response.setEntities(errors);
	        return response;
		}
		
		if(ex instanceof DBException) {
			Response response = new Response<>(ResultCode.DBException.getCode(), StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ResultCode.DBException.getMessage());
			response.setEntity(ex.getMessage());
	        return response;
		}
		
		if(ex instanceof SystemException) {
			Response response = new Response<>(ResultCode.SystemException.getCode(), StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ResultCode.SystemException.getMessage());
			response.setEntity(ex.getStackTrace()[0].toString());
	        return response;
		}
		
		if(ex instanceof BusinessException) {
			Response response = new Response<>(ResultCode.BusinessException.getCode(), StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ResultCode.BusinessException.getMessage());
			response.setEntity(ex.getStackTrace()[0].toString());
	        return response;
		}
		
		if(ex instanceof InfoException) {
			Response response = new Response<>(ResultCode.InfoException.getCode(), ex.getMessage());
			response.setEntity(ex.getStackTrace()[0].toString());
	        return response;
		}
		
		if(ex instanceof MaintainException) {
			Response response = new Response<>(ResultCode.MaintainException.getCode(), StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ResultCode.MaintainException.getMessage());
			response.setEntity(ex.getStackTrace()[0].toString());
	        return response;
		}
		
		Response response = new Response<>(ResultCode.UnknownException.getCode(), ResultCode.UnknownException.getMessage());
		response.setEntity(ex.getMessage() + "\n" + ex.getStackTrace()[0].toString());
        return response;
	}
	
	public Response(T entity) {
		this();
		setEntity(entity);
		setTotal(1);
	}
	
	public Response(List<T> entities) {
		this();
		setEntities(entities);
		setTotal(entities == null ? 0 : entities.size());
	}
	
	public Response(List<T> entities, long total) {
		this();
		setEntities(entities);
		setTotal(total);
	}

	public boolean isSuccess() {
		return success;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		this.success = StringUtils.equals(errorCode, ResultCode.SUCCESS.getCode());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public void addEntity(T entity) {
		if(entities == null) {
			entities = Lists.newArrayList();
		}
		entities.add(entity);
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

}
