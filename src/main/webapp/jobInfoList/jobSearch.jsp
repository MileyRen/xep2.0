<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- bootstrap date插件 -->
<script type="text/javascript" src="styleRen/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="styleRen/bootstrap/locals/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<script type="text/javascript" src="styleRen/jsdate/laydate.js"></script>
</head>
<body>
	<form action="jobsList.action" id="search" class="form-horizontal" role="form" method="post">
		<div class="form-group">
			Status:
			<select required name="stateSearch" id="state">
			 	<option value="" selected>--Select--</option>
				<s:iterator value="#session.stateList" var="str">
				 <option value="${str}">${str} </option>
				</s:iterator>
			</select> 
			
			Create Time:
			<input class="inline laydate-icon" id="start" style="width: 100px; margin-right: 10px;" name="fTime" value="${fTime}">
			--
			<input class="inline laydate-icon" id="end" style="width: 100px;" name="tTime" value="${tTime}">
			&nbsp; SortBy:
			<select name="sortByTime">
				<option value=" order by bgTime " selected>Begin Time</option>
				<option value=" order by edTime ">End Time</option>
			</select>
			<select name="sortDA">
				<option value=" desc " selected>desc</option>
				<option value=" asc ">asc</option>
			</select>
			<a class="btn btn-primary btn-xs" onclick="javascript:$('form#search').submit()"> <span class="glyphicon glyphicon-search"></span> SEARCH
			</a>
		</div>
	</form>
	<script type="text/javascript">
		!function() {
			laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
			laydate({
				elem : '#demo'
			});//绑定元素
		}();

		//日期范围限制
		var start = {
			elem : '#start',
			format : 'YYYY-MM-DD',
			min : '1900-01-01', //
			max : laydate.now(), //设定最大日期为当前日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};

		var end = {
			elem : '#end',
			format : 'YYYY-MM-DD',
			min : '1900-01-01',
			max : laydate.now(),
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，充值开始日的最大日期
			}
		};
		laydate(start);
		laydate(end);

		//自定义日期格式
		laydate({
			elem : '#test1',
			format : 'YYYY年MM月DD日',
			festival : true, //显示节日
			choose : function(datas) { //选择日期完毕的回调
				alert('得到：' + datas);
			}
		});

		//日期范围限定在昨天到明天
		laydate({
			elem : '#hello3',
			min : laydate.now(-1), //-1代表昨天，-2代表前天，以此类推
			max : laydate.now(+1)
		//+1代表明天，+2代表后天，以此类推
		});
	</script>
</body>