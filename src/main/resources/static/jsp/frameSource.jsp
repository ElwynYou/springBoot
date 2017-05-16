<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/rs.tld" prefix="rs"%>
<%@ taglib uri="/WEB-INF/tld/fns.tld" prefix="fns"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="Shortcut Icon" href="<c:url value="/beijing/images/logo.ico"/>" type="image/x-icon"/>
<script defer="defer" src="<c:url value='/js/toggleColumn.js'/>"></script>
<script src="<c:url value="/js/my97/WdatePicker.js"/>"></script>
<script>
		/** 如果作为独立页面运行,则**/
		window.jQuery || (function(){
			
			document.write([
				'<script src="<c:url value="/js/betterClient.js"/>"><\/script>', /**浏览器差异修复.**/
				<%--'<script src="<c:url value="/jslib"/>"><\/script>', /!**动态加载的js库.**!--%>
				'<!--[if lte IE 9]>',	/**如果目标浏览器是ie8 及以下, 则使用以下js库兼容.**/
				'<script src="<c:url value="/js/json2.js"/>"><\/script>', /**工具函数**/
				'<script src="<c:url value="/js/html5shiv/html5.min.js"/>"><\/script>', 
				'<script src="<c:url value="/js/respond/respond.min.js"/>"><\/script>',
				'<![endif]-->',
				
                '<script src="<c:url value="/js/jquery-1.11.3.js"/>"><\/script>',	/**jQuery核心库, 支持ie8**/
                '<script src="<c:url value="/js/ajaxupload.js"/>"><\/script>',
                '<script src="<c:url value="/js/bootstrap/bootstrap-clockpicker.min.js"/>"><\/script>', /**时钟选择器**/
 				'<script src="<c:url value="/js/bootstrap/bootstrap.js"/>"><\/script>',	/**bootstrap核心库**/
				'<script src="<c:url value="/js/bootstrap/bootstrap-datepicker.js"/>"><\/script>', /**日期控件库**/
				'<script src="<c:url value="/js/bootstrap/bootstrap-datepicker.zh-CN.js"/>"><\/script>', /**日期控件语言库**/
				'<script src="<c:url value="/js/bootstrap/bootstrap-datetimepicker.min.js"/>"><\/script>', /**时间库**/
				'<script src="<c:url value="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"/>"><\/script>', /**时间语言库**/
				'<script src="<c:url value="/js/datatables/jquery.dataTables.js"/>"><\/script>',	/**dataTables插件支持**/
				'<script src="<c:url value="/js/datatables/dataTables.bootstrap.js"/>"><\/script>',	/**dataTables插件支持**/
				'<script src="<c:url value="/js/highcharts/highcharts.js"/>"><\/script>',	/**highcharts 图表插件支持**/
				'<script src="<c:url value="/js/typeahead.bundle.js"/>"><\/script>',	/**自动补全插件  http://twitter.github.io/typeahead.js/ https://github.com/twitter/typeahead.js/blob/master/doc/jquery_typeahead.md **/
				'<script src="<c:url value="/js/handlebars.js"/>"><\/script>',	/**模板引擎 用于快速构建模板  http://www.ghostchina.com/introducing-the-handlebars-js-templating-engine/**/
                '<script src="<c:url value="/js/jquery.customize.js"/>"><\/script>',	/**jQuery 自定义拓展.依赖jQuery, bootstrap**/
                '<script src="<c:url value="/js/clientSetting.js"/>"><\/script>',	/**客户端浏览器定制.依赖jQuery, bootstrap**/
				'<script src="<c:url value="/js/datatablesSupport.js"/>"><\/script>', /**dataTables设置**/
				
				'<script src="<c:url value="/js/layer/layer.js"/>"><\/script>',
				'<script src="<c:url value="/js/jquery.validate.js"/>"><\/script>', /**校验框架**/
				'<script src="<c:url value="/js/jquery.validate.extend.js"/>"><\/script>', /**校验框架这个应该在jquery.validate.js之后**/
				'<script src="<c:url value="/js/util.js"/>"><\/script>', /**工具函数**/
				
				'<script src="<c:url value="/js/artTemplate.js"/>"><\/script>', /**腾讯的模板js, 很小，使用方便，能将html模板与数据编译在一起**/
				'<script src="<c:url value="/js/jquery.fancybox.pack.js"/>"><\/script>', /**弹窗**/
				'<script src="<c:url value="/js/ajaxfileupload.js"/>"><\/script>', /**弹窗**/
				'<script src="<c:url value="/js/jqPaginator.min.js"/>"><\/script>',/**BootStrap分页插件**/
				'<script src="<c:url value="/js/city.js"/>"><\/script>',/**省市县三级联动插件(地址内容可以自己更新)**/

				'<link rel="stylesheet" href="<c:url value="/css/jquery.fancybox.css" />"/>', /**弹窗样式**/
				'<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-clockpicker.min.css"/>"/>',/**时钟选择**/
				'<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.min.css" />"/>',
				'<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-theme.min.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-datepicker3.min.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-datetimepicker.min.css"/>"/>',		
				'<link rel="stylesheet" href="<c:url value="/css/dataTables.bootstrap.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/css/bootstrap.customize.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/css/index.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/css/main.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/js/layer/skin/layer.css"/>"/>',
				'<link rel="stylesheet" href="<c:url value="/css/toggleColumn.css"/>"/>',
// 				'<script type="text/javascript" src="<c:url value="/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"/>"><\/script>',
// 				'<link rel="stylesheet" href="<c:url value="/js/jquery-ui-1.11.4.custom/jquery-ui.css"/>"/>'
				].join(''));
		})();

</script>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
