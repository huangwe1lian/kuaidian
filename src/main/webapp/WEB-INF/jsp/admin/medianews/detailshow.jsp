<%@page contentType="text/html; charset=UTF-8" session="false"%><%@include
	file="/WEB-INF/jspf/import.jspf"%><div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<div class="unit"> 
			<strong>媒体资讯详情</strong>
		</div>
		<div class="divider"></div>
		<div class="unit"> 
			<label>ID: </label>
			${geli:display(entity,'id')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>类型: </label>
			${geli:display(entity,'kind')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>文章标题: </label>
			${geli:display(entity,'name')}
		</div> 
		<div class="divider"></div>
		<div class="unit"> 
			<label>文章URL: </label>
			${geli:display(entity,'url')}
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
			<label>来源媒体: </label>
			${geli:display(entity,'media')}
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
			<label>是否推荐: </label>
			${geli:display(entity,'isRecommend')}
		</div>
		<div class="divider"></div>
		<c:if test="${entity.isRecommend == 1 }">
		<div class="unit" id="recommendTime" > 
			<label>推荐起始时间: </label>
			<fmt:formatDate value='${entity.beginTime}' pattern='yyyy-MM-dd'></fmt:formatDate>
			-
			<fmt:formatDate value='${entity.endTime}' pattern='yyyy-MM-dd'></fmt:formatDate>
		</div>
		<div class="divider"></div>
		</c:if>
		<div class="unit"> 
			<label>状态: </label>
			${geli:display(entity,'status')}
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">确定</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
