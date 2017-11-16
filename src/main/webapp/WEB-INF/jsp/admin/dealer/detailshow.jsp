<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="unit"> 
			<strong>经销商详情</strong>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>经销商全称: </label>
			${geli:display(entity,'name')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>经销商简称: </label>
			${geli:display(entity,'shortName')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>登录名称: </label>
			${geli:display(entity,'loginName')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>ID: </label>
			${geli:display(entity,'id')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>所在地: </label>
			${geli:refer(entity,'provinceId').name}省
			${geli:refer(entity,'cityId').name}市
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>地址: </label>
			${geli:display(entity,'address')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>e-mail: </label>
			${geli:display(entity,'email')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>销售热线: </label>
			${geli:display(entity,'phone')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>代理服务: </label>
			${geli:display(entity,'agentService')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>排序值: </label>
			${geli:display(entity,'seq')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>显示状态: </label>
			${entity.hidden==0?'显示':'隐藏'}
		</div>
		<div class="divider"></div>   
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
