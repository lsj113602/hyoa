/**
 * jQuery日期插件
 * 匿名函数
 */
(function($){
	/** 为jQuery添加对象的方法 */
	$.prototype.test1 = function(){
		this.html("dddd");
	}
	/** 为jQuery批量添加对象的方法 */
	$.fn.extend({
		/**
		 * 显示当前系统运行时间
		 */
		runtime : function(){
			// 2016年08月16日 星期二 15:07:37
			/** 创建日期对象 */
			var d = new Date();
			/** 定义数组封装数据 */
			var arr = new Array();
			/** 获取年 */
			arr.push(d.getFullYear() + "年");
			/** 获取月 0-11 */
			arr.push($.calc(d.getMonth() + 1) + "月");
			/** 获取日 */
			arr.push($.calc(d.getDate()) + "日");
			/** 获取星期几 0-6 (星期日-星期六) */
			var week = $.weeks[d.getDay()];
			arr.push("&nbsp;" + week + "&nbsp;");
			/** 获取小时 */
			arr.push($.calc(d.getHours()) + ":");
			/** 获取分钟 */
			arr.push($.calc(d.getMinutes()) + ":");
			/** 获取秒 */
			arr.push($.calc(d.getSeconds()));
			
			this.html(arr.join(""));
			var t = this;
			/** 开启定时器 */
			setTimeout(function(){
				// this --> window
				t.runtime();
			}, 1000);
		},
		/**
		 * 倒计时的方法
		 * text : 显示的文本
		 * seconds : 秒数
		 */
		countDown : function(text, seconds){
			if (seconds > 1){
				/** 自减 */
				seconds--;
				/** 替换掉text中的占位符{0} */
				var res = text.replace("{0}", seconds);
				/** 为按钮添加value属性值 */
				this.val(res);
				var obj = this;
				/** 开启延迟的定时器 */
				setTimeout(function(){
					obj.countDown(text, seconds);
				}, 1000);
			}else{
				$(this).attr("disabled", false).val("重新获取验证码");
			}
		}
	
	});
	
	/** 为jQuery添加静态的方法 */
	$.extend({
		weeks : ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
		calc : function(str){
			return str > 9 ? str : "0" + str;
		}
	})
})(jQuery);