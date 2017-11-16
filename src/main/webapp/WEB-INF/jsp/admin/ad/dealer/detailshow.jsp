<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="unit"> 
			<strong>经销商广告详情</strong>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>店铺活动ID: </label>
			${geli:display(entity,'id')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>活动标题: </label>
			${geli:display(entity,'name')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>活动类型: </label>
			${geli:display(entity,'smallType')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>经销商ID: </label>
			${geli:display(entity,'dealerId')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>经销商全称: </label>
			${geli:refer(entity,'dealerId').name}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>经销商简称: </label>
			${geli:refer(entity,'dealerId').shortName}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>关联车系: </label>
			<div style="padding-left:130px;">
				<ul>
					<c:forEach var="sg" items="${serialGroups }">
						<li style="clear:right;float: left;width: 150px;height:25px;">
							${sg.name }
						</li>
					</c:forEach>
				</ul>
			</div>
			
		</div>
		<div class="divider"></div>
		<div class="unit" > 
			<label>起始时间: </label>
			<fmt:formatDate value='${entity.beginTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>
			-
			<fmt:formatDate value='${entity.endTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>是否删除: </label>
			${geli:display(entity,'deleted')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>是否隐藏: </label>
			${geli:display(entity,'hidden')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>活动级别: </label>
			${geli:display(entity,'activityLevel')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>是否推荐: </label>
			${geli:display(entity,'isRecommend')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>推荐时间: </label>
			${geli:display(entity,'recommendTime')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>推荐人: </label>
			${geli:refer(entity,'recommendUserId').name}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>创建时间: </label>
			${geli:display(entity,'createTime')}
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">确认</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
