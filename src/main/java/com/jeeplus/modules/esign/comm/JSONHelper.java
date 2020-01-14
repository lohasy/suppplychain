package com.jeeplus.modules.esign.comm;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.esign.exception.DefineException;

/**
 * @description JSON 处理辅助类
 * @author 飞飞
 * @date 2019年7月20日 下午5:35:31
 * @since JDK1.7
 */
public class JSONHelper {

	private JSONHelper() {
	}

	/**
	 * @description 格式化json data数据
	 *
	 * @param json
	 * @return
	 * @throws DefineException
	 * @author 飞飞
	 * @date 2019年7月20日 下午5:34:38
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castDataJson(JSONObject json, Class<T> clz) throws DefineException {
		Object obj = json.get("data");
		int code = json.getIntValue("code");
		if (code != 0 && obj == null) {
			throw new DefineException(json.getString("message"));
		}
		return (T)obj;
	}
}
