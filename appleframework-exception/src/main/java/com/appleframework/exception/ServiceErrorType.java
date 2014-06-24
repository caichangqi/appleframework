package com.appleframework.exception;

import java.util.EnumMap;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 徐少敏
 * @version 1.0
 */
public enum ServiceErrorType {
	
	SERVICE_UNAVAILABLE,
	BUSINESS_LOGIC_ERROR;

    private static EnumMap<ServiceErrorType, String> errorCodeMap = new EnumMap<ServiceErrorType, String>(ServiceErrorType.class);
    private static EnumMap<ServiceErrorType, String> errorMsgMap  = new EnumMap<ServiceErrorType, String>(ServiceErrorType.class);

    static {
    	
    	errorCodeMap.put(ServiceErrorType.SERVICE_UNAVAILABLE,  "0001");
    	errorCodeMap.put(ServiceErrorType.BUSINESS_LOGIC_ERROR, "0009");
    	        
        errorMsgMap.put(ServiceErrorType.SERVICE_UNAVAILABLE,  "服务不可用");
        errorMsgMap.put(ServiceErrorType.BUSINESS_LOGIC_ERROR, "业务逻辑出错");
        
    }
    
    public static String codeValue(ServiceErrorType serviceErrorType) {
        return errorCodeMap.get(serviceErrorType);
    }
    
    public static String msgValue(ServiceErrorType serviceErrorType) {
        return errorMsgMap.get(serviceErrorType);
    }
}

