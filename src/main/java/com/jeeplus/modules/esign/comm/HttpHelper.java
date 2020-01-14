package com.jeeplus.modules.esign.comm;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.jeeplus.modules.esign.constant.CacheKeyConstant;
import com.jeeplus.modules.esign.constant.ConfigConstant;
import com.jeeplus.modules.esign.enums.RequestType;
import com.jeeplus.modules.esign.exception.DefineException;

import java.util.Map;

/**
 * @description Http 请求 辅助类
 * @author 飞飞
 * @date 2019年7月19日 下午2:21:14
 * @since JDK1.7
 */
public class HttpHelper {

	/**
	 * 不允许外部创建实例
	 */
	private HttpHelper() {

	}

	// ------------------------------公有方法start--------------------------------------------

	/**
	 * @description 发送常规HTTP 请求
	 * 
	 * @param reqType 请求方式
	 * @param url 请求路径
	 * @param paramStr 请求参数
	 * @return
	 * @throws DefineException
	 * @date 2019年7月19日 下午2:27:59
	 * @author 飞飞
	 */
	public static JSONObject doCommHttp(RequestType reqType, String url, String paramStr) throws DefineException {
		return HttpCfgHelper.sendHttp(reqType, url, buildCommHeader(), paramStr);
	}

	/**
	 * @description 发送文件流上传 HTTP 请求
	 *
	 * @param reqType 请求方式
	 * @param url 请求路径
	 * @param param 请求参数
	 * @param contentMd5 文件contentMd5
	 * @param contentType 文件MIME类型
	 * @return
	 * @throws DefineException
	 * @author 飞飞
	 * @date 2019年7月20日 下午8:21:28
	 */
	public static JSONObject doUploadHttp(RequestType reqType, String url, byte[] param, String contentMd5,
                                          String contentType) throws DefineException {
		return HttpCfgHelper.sendHttp(reqType, url, buildUploadHeader(contentMd5, contentType), param);
	}

	// ------------------------------公有方法end----------------------------------------------

	// ------------------------------私有方法start--------------------------------------------

	/**
	 * @description 创建常规请求头
	 * @param token
	 * @return
	 * @date 2019年7月19日 下午2:36:51
	 * @author 飞飞
	 */
	private static Map<String, String> buildCommHeader() {
		Map<String, String> header = Maps.newHashMap();
		header.put("X-Tsign-Open-App-Id", ConfigConstant.PROJECT_ID);
		header.put("X-Tsign-Open-Token", String.valueOf(LocalCacheHelper.get(CacheKeyConstant.TOKEN)));
		header.put("Content-Type", "application/json");
		return header;
	}

	/**
	 * @description 创建文件流上传 请求头
	 *
	 * @param contentMd5
	 * @param contentType
	 * @return
	 * @author 飞飞
	 * @date 2019年7月20日 下午8:13:15
	 */
	private static Map<String, String> buildUploadHeader(String contentMd5, String contentType) {
		Map<String, String> header = Maps.newHashMap();
		header.put("Content-MD5", contentMd5);
		header.put("Content-Type", contentType);
		return header;
	}

	// ------------------------------私有方法end----------------------------------------------
}
