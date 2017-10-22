package cn.hnhy.hyoa.core.common.sms;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 操作短信的工具类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年4月23日 下午9:34:41
 */
public final class SmsUtils {
	
	/** 短信接口的请求地址 */
	private final static String SMS_REQUEST_URL = "http://gw.api.taobao.com/router/rest";
	
	/** ################# 需要根据情况填写下面的属性 #################### */
	/** 应用的key */
	private final static String APP_KEY = "23399504";
	/** 应用的签名 */
	private final static String APP_SECRET = "3fbe5c0fb6f6af88f21b7ecee2a0a47d";
	/** 短信签名 */
	private final static String SMS_FREE_SIGN_NAME = "五子连珠";
	/** 短信模板ID */
	private final static String SMS_TEMPLATE_CODE = "SMS_11480310";
	/** ################# 需要根据情况填写上面的属性 #################### */
	
	/**
	 * 验证码短信发送方法
	 * @param code 验证码
	 * @param phones 手机号码
	 * @return true : 发送成功  false: 发送失败
	 */
	public static boolean send(String code, String phones){
		try{
			/** 
			 * 创建淘宝客户端
			 * 第一个参数：请求URL (短信接口的请求地址)
			 * 第二个参数：应用的key
			 * 第三个参数：应用的签名
			 * */
			TaobaoClient client = new DefaultTaobaoClient(SMS_REQUEST_URL, APP_KEY, APP_SECRET);
			/** 构建短信发送请求对象 */
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			
			/**################# 封装请求参数 ############## */
			/**
			 * 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，
			 * 在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用 
			 * (可选)
			 */
			req.setExtend("it001");
			/**
			 * 短信类型，传入值请填写normal 
			 * (必须)
			 */
			req.setSmsType("normal");
			/**
			 * 短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名。
			 * 如“阿里大于”已在短信签名管理中通过审核，则可传入”阿里大于“（传参时去掉引号）作为短信签名。
			 * 短信效果示例：【阿里大于】欢迎使用阿里大于服务。
			 * (必须)
			 */
			req.setSmsFreeSignName(SMS_FREE_SIGN_NAME);
			
			/**
			 * 短信模板ID，传入的模板必须是在阿里大于“管理中心-短信模板管理”中的可用模板。
			 * 示例：SMS_585014 
			 * (必须)
			 */
			req.setSmsTemplateCode(SMS_TEMPLATE_CODE);
			/**
			 * 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开。
			 * 示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，
			 * 传参时需传入{"code":"1234","product":"alidayu"} 
			 *      验证码${number}，您正进行身份验证，打死不告诉别人！
			 * (可选)
			 */
			req.setSmsParamString("{\"number\":\""+ code +"\"}");
			/**
			 * 短信接收号码。支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。群发短信需传入多个号码，
			 * 以英文逗号分隔，一次调用最多传入200个号码。示例：18600000000,13911111111,13322222222 
			 * (必须)
			 */
			req.setRecNum(phones);
			/**################# 封装请求参数 ############## */
			
			/** 执行请求得到响应对象 */
			AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
			/**
			 * {"alibaba_aliqin_fc_sms_num_send_response":
			 * 			{"result":
			 * 				{"model":"102576995949^0","success":true},
			 * 				"request_id":"44nzz2ck96uq"
			 * 	        }
			 * }
			 */
			/** 获取响应数据 */
			String responseData = response.getBody();
			if (responseData.contains("success")){
				// JsonObject: {}
				// JsonArray: []
				/** 创建 gson对象 */
				Gson gson = new Gson();
				return gson.fromJson(responseData, JsonObject.class)
				           .getAsJsonObject("alibaba_aliqin_fc_sms_num_send_response")
				           .getAsJsonObject("result") // {"model":"102576995949^0","success":true}
				           .get("success") // JsonElement
				           .getAsBoolean();
			}
			
			return false;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
}