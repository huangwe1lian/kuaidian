<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<div class="pageContent">
	<form method="post"
		action="${ctx}/admin/order/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>名单线索量ID: </label>
				<input class="textInput" ${geli:not_oper('id') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'id')}" name="id" />
			</p>
			<p>
				<label>姓名: </label>
				<input class="textInput" ${geli:not_oper('name') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'name')}" name="name" />
			</p>
			<p>
				<label>手机号: </label>
				<input class="textInput" ${geli:not_oper('mobile') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'mobile')}" name="mobile" />
			</p>
			<p>
				<label>性别: </label>
				<select class="required " name="gender" >
					<option value="0" ${(0 == gender) ? 'selected' : '' }>女士</option>
					<option value="1" ${(1 == gender) ? 'selected' : '' }>先生</option>
				</select>
			</p>
			<p>
				<label>经销商ID: </label>
				<input class="textInput" ${geli:not_oper('dealerId') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'dealerId')}" name="dealerId" />
			</p>
			<p>
				<label>城市ID: </label>
				<input class="textInput" ${geli:not_oper('cityId') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'cityId')}" name="cityId" />
			</p>
			<p>
				<label>车系ID: </label>
				<input class="textInput" ${geli:not_oper('serialGroupId') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'serialGroupId')}"
					name="serialGroupId" />
			</p>
			<p>
				<label>车型ID: </label>
				<input class="textInput" ${geli:not_oper('modelId') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'modelId')}" name="modelId" />
			</p>
			<p>
				<label>名单类型: </label>
				<select class="required " name="type" >
					<option value="0" ${(0 == type) ? 'selected' : '' }>未知</option>
					<option value="1" ${(1 == type) ? 'selected' : '' }>我要询价</option>
					<option value="2" ${(2 == type) ? 'selected' : '' }>置换购车</option>
				</select>
			</p>
			<p>
				<label>平台来源: </label>
				<select class="required " name="origin" >
					<option value="0" ${(0 == origin) ? 'selected' : '' }>未知</option>
					<option value="1" ${(1 == origin) ? 'selected' : '' }>PC</option>
					<option value="2" ${(2 == origin) ? 'selected' : '' }>wap</option>
				</select>
			</p>
			<p>
				<label>购车方式: </label>
				<select class="required " name="payType" >
					<option value="0" ${(0 == payType) ? 'selected' : '' }>全款购车</option>
					<option value="1" ${(1 == payType) ? 'selected' : '' }>全款置换</option>
					<option value="2" ${(2 == payType) ? 'selected' : '' }>贷款购车</option>
					<option value="3" ${(3 == payType) ? 'selected' : '' }>贷款置换</option>
				</select>
			</p>
			<p>
				<label>购车时间: </label>
				<input class="textInput" ${geli:not_oper('buyTime') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'buyTime')}" name="buyTime" />
			</p>
			<p>
				<label>网友IP地址: </label>
				<input class="textInput" ${geli:not_oper('ip') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'ip')}" name="ip" />
			</p>
			<p>
				<label>页面来源: </label>
				<input class="textInput" ${geli:not_oper('refer') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'refer')}" name="refer" />
			</p>
			<p>
				<label>名单报名授权代码: </label>
				<input class="textInput" ${geli:not_oper('orderCode') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'orderCode')}" name="orderCode" />
			</p>
			<p>
				<label>名单状态: </label>
				<select class="required " name="status" >
					<option value="0" ${(0 == status) ? 'selected' : '' }>未处理</option>
					<option value="1" ${(1 == status) ? 'selected' : '' }>已处理</option>
				</select>
			</p>
			<p>
				<label>商家备注: </label>
				<input class="textInput" ${geli:not_oper('dmemo') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'dmemo')}" name="dmemo" />
			</p>
			<p>
				<label>名单处理时间: </label>
				<input class="${geli:not_oper('dealTime') ? '' : 'date'} textInput"
					${geli:not_oper('dealTime') ? 'readonly="readonly"' : ''}
					dateFmt="yyyy-MM-dd HH:mm:ss"
					value="<fmt:formatDate value='${entity.dealTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>"
					name="dealTime" />
				<c:if test="${!geli:not_oper('dealTime')}">
					<a class="inputDateButton" href="javascript:;">select</a>
				</c:if>
			</p>
			<p>
				<label>创建时间: </label>
				<input class="${geli:not_oper('createTime') ? '' : 'date'} textInput"
					${geli:not_oper('createTime') ? 'readonly="readonly"' : ''}
					dateFmt="yyyy-MM-dd HH:mm:ss"
					value="<fmt:formatDate value='${entity.createTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>"
					name="createTime" />
				<c:if test="${!geli:not_oper('createTime')}">
					<a class="inputDateButton" href="javascript:;">select</a>
				</c:if>
			</p>
			<p>
				<label>更新时间: </label>
				<input class="${geli:not_oper('updateTime') ? '' : 'date'} textInput"
					${geli:not_oper('updateTime') ? 'readonly="readonly"' : ''}
					dateFmt="yyyy-MM-dd HH:mm:ss"
					value="<fmt:formatDate value='${entity.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>"
					name="updateTime" />
				<c:if test="${!geli:not_oper('updateTime')}">
					<a class="inputDateButton" href="javascript:;">select</a>
				</c:if>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
