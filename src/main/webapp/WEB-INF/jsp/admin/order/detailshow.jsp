<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include	file="/WEB-INF/jspf/import.jspf"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="unit"> 
			<strong>线索量详情</strong>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>线索ID: </label>
			${geli:display(entity,'id')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>创建时间: </label>
			${geli:display(entity,'createTime')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>车系: </label>
			${geli:refer(entity,'serialGroupId').name}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>车型: </label>
			${geli:refer(entity,'modelId').name}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>姓名: </label>
			${geli:display(entity,'name')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>手机号: </label>
			${geli:display(entity,'mobile')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>用户市: </label>
			${geli:refer(entity,'cityId').name}
		</div>  
		<div class="divider"></div>
		<div class="unit"> 
			<label>性别: </label>
			<c:if test="${entity.gender == 0 }">女士</c:if>
			<c:if test="${entity.gender == 1 }">先生</c:if>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>购车方式: </label>
			<c:if test="${entity.payType == 0 }">全款购车</c:if>
			<c:if test="${entity.payType == 1 }">全款置换</c:if>
			<c:if test="${entity.payType == 2 }">贷款购车</c:if>
			<c:if test="${entity.payType == 3 }">贷款置换</c:if>
		</div>   
		<div class="divider"></div>
		<div class="unit"> 
			<label>期望价格： </label>
			${geli:display(entity,'expectPrice')}(万)
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>经销商简称： </label>
			${geli:refer(entity,'dealerId').name}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>平台: </label>
			<c:if test="${entity.origin == 0 }">未知</c:if>
			<c:if test="${entity.origin == 1 }">PC</c:if>
			<c:if test="${entity.origin == 2 }">wap</c:if>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>来源: </label>
			${geli:display(entity,'refer')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>重复线索: </label>
			<c:if test="${entity.repeat >1}">是</c:if>
			<c:if test="${entity.repeat ==1}">否</c:if>
		</div>
		
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
