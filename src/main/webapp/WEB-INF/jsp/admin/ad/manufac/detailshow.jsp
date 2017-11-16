<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="unit"> 
			<strong>广告详情</strong>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>ID: </label>
			${geli:display(entity,'id')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>广告标题: </label>
			${geli:display(entity,'name')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>广告类型: </label>
			${geli:display(entity,'type')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>广告位置: </label>
			${geli:display(entity,'position')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>广告URL: </label>
			${geli:display(entity,'url')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>平台: </label>
			${geli:display(entity,'orgin')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>文章宣传/导读图: </label>
			<jsp:include page="/uploadImage"/>
			<div id="imageTest">
			    <div>
				    <div class="imgPlaceholder" id="showImageBlock" style="padding-left:130px;">
				        <ul id="div_pic">
				        	<li>
				        		<c:if test="${not empty entity && not empty entity.pic }">
				        			<img width="160" height="120" src="${geli:display(entity, 'pic')}">
				        		</c:if>
				        	</li>
				        </ul>
				    </div>    
			    </div>
			</div>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>关联城市: </label>
			<div style="padding-left:130px;">
				<ul>
					<c:if test="${fn:length(citys) <= 10 }">
						<c:forEach var="c" items="${citys }">
							<li style="clear:right;float: left;width: 150px;height:25px;">
								${c.name }
							</li>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(citys) > 10 }">
						覆盖${fn:length(citys)}个城市
					</c:if>
				</ul>
			</div>
			
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
		<div class="unit"> 
			<label>排序值: </label>
			${geli:display(entity,'seq')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>审核状态: </label>
			${geli:display(entity,'auditStatus')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>审核时间: </label>
			${geli:display(entity,'auditTime')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>审核人: </label>
			${geli:refer(entity,'auditUserId').name}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>广告状态: </label>
			${geli:display(entity,'status')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>开始时间: </label>
			${geli:display(entity,'beginTime')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>结束时间: </label>
			${geli:display(entity,'endTime')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>创建人: </label>
			${geli:refer(entity,'createUserId').name}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>创建时间: </label>
			${geli:display(entity,'createTime')}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>最后修改人: </label>
			${geli:refer(entity,'updateUserId').name}
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>最后修改时间: </label>
			${geli:display(entity,'updateTime')}
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
