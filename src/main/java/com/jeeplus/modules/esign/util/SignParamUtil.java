package com.jeeplus.modules.esign.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.modules.esign.bean.signflow.*;
import com.jeeplus.modules.esign.exception.DefineException;

import java.util.List;

/**
 * @description 签署服务相关参数组装工具类
 * @since JDK1.7
 */
public class SignParamUtil {

	/**
	 * 不允许外部创建实例
	 */
	private SignParamUtil() {
	}
	// ------------------------------公有方法start--------------------------------------------

	/**
	 * @description 签署流程创建 参数设置
	 *
	 * @return
	 * @date 2019年7月17日 下午3:05:14
	 * @author 宫清
	 */
	public static String createSignFlowParam(SignFlowStart signFlowStart) {
//		return JSON.toJSONString(createSignFlowStart());
		return JSON.toJSONString(signFlowStart);
	}


	/**
	 * @description 流程文档添加 参数设置
	 *
	 *
	 *              说明：
	 *              <p>
	 *              可添加多个流程文档，这里只添加一个作为示例
	 *
	 *              待填充参数：
	 *              <p>
	 *              fileId:文档id【必填】
	 *              <p>
	 *              encryption:是否加密，0-不加密，1-加密，默认加密【可空】
	 *              <p>
	 *              fileName：文档名称，默认文件名称
	 *              <p>
	 *              filePassword:文档密码，如果encryption为1，文档密码不能为空，默认没有密码【可空】
	 *
	 * @param files 多个fileId拼接，英文"," 隔开
	 * @throws DefineException
	 * @return
	 */
	public static String addFlowDocParam(List<FlowAddFile> files) throws DefineException {
		if (null == files || files.isEmpty()) {
			throw new DefineException("文档ID为空");
		}
		JSONObject outterjson = new JSONObject();
		JSONArray jarr = new JSONArray();

//		String[] arr = fileIds.split(",");
		for (FlowAddFile file : files) {
			JSONObject innerjson = new JSONObject();
			if(null == file.getFileId() || "".equals(file.getFileId().trim())){
				throw new DefineException("文档ID为空");
			}
			innerjson.put("fileId", file.getFileId());
			innerjson.put("encryption", file.getEncryption());
			innerjson.put("fileName", file.getFileName());
			innerjson.put("filePassword", file.getFilePassword());
			jarr.add(innerjson);
		}
		outterjson.put("docs", jarr);

		return outterjson.toString();
	}


	/**
	 * @description 添加签署方手动盖章签署区 参数设置
	 * @return
	 * @param signfieldList              列表
	 * @date 2019年7月18日 下午3:40:20
	 * @throws DefineException
	 */
	public static String addSignerHandSignAreaParam(List<Signfield> signfieldList) throws DefineException {
		JSONArray jarr = JSONArray.parseArray(JSON.toJSONString(signfieldList));
		JSONObject json = new JSONObject();
		json.put("signfields", jarr);
		return json.toString();
	}


	// ------------------------------私有方法start--------------------------------------------

	/**
	 * @description 组装签署流程创建的对象（参数只做参考）
	 *
	 *              涉及对象：cn.tign.hz.domain.signflow包下
	 *              <p>
	 *              {@link ConfigInfo} 任务配置信息
	 *              <p>
	 *              {@link SignFlowStart} 签署流程参数信息
	 * @return
	 */
	private static SignFlowStart createSignFlowStart() {
		ConfigInfo cfgInfo = new ConfigInfo(null, "1,2", null, null);
		SignFlowStart sfs = new SignFlowStart(null, "测试签署流程开启", null, null, null, null, null, cfgInfo);
		return sfs;
	}

	/**
	 * @description 构建用户手动签署签署区列表数据
	 *
	 *              设计对象：cn.tign.hz.domain.signarea 包下
	 *              <p>
	 *              {@link PosBean} 签署位置信息
	 *              <p>
	 *              {@link Signfield} 签署区数据信息
	 *
	 *
	 * @param fileIds              文件file id列表
	 * @param authorizedAccountIds 签约主体id列表
	 * @param signerAccountIds     签署人账号ID列表，与fileIds、authorizedAccountIds 一一对应
	 * @return
	 */
	private static List<Signfield> createSignerHandSignfields(List<String> fileIds, List<String> signerAccountIds,
															  List<String> authorizedAccountIds) throws DefineException {
		if (CollectionUtils.isEmpty(fileIds) || CollectionUtils.isEmpty(signerAccountIds)) {
			throw new DefineException(" 授权主体账号签约主体账号、签署人个人账号以及文件ID不能为空");
		}
		if ((CollectionUtils.isNotEmpty(authorizedAccountIds) && fileIds.size() != authorizedAccountIds.size())
				|| fileIds.size() != signerAccountIds.size()) {
			throw new DefineException(" 授权主体账号签约主体账号、签署人个人账号以及文件ID个数不一致");
		}

		// 这里举一个签署区的例子
		String fileId1 = fileIds.get(0);
		String signerAccountId1 = signerAccountIds.get(0);
		String authorizedAccountId1 = CollectionUtils.isNotEmpty(authorizedAccountIds) ? authorizedAccountIds.get(0) : null;

		List<Signfield> list = Lists.newArrayList();
		PosBean posBean1 = new PosBean("1", 500f, 100f, null, null);
		Signfield signfield1 = new Signfield(fileId1, signerAccountId1, null, authorizedAccountId1, null, 1, posBean1,
				null, null, null);

		list.add(signfield1);
		return list;
	}
}
