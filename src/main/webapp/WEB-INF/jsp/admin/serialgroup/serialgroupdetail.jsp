<%@page contentType="text/html; charset=UTF-8" session="false"%>
<%@include	file="/WEB-INF/jspf/import.jspf"%>
<div class="pageContent">
	<form method="post"
		action="${ctx}/admin/serialgroup/${method}.do<c:if test="${method=='update'}">?id=${param.id}</c:if>"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>车系ID: </label><input class="textInput"
					${geli:not_oper('id') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'id')}" name="id" />
			</p>
			<p>
				<label>车系名称: </label><input class="textInput"
					${geli:not_oper('name') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'name')}" name="name" />
			</p>
			<p>
				<label>车系英文名称: </label><input class="textInput"
					${geli:not_oper('ename') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'ename')}" name="ename" />
			</p>
			<p>
				<label>车系封面图: </label><input class="textInput"
					${geli:not_oper('logo') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'logo')}" name="logo" />
			</p>
			<p>
				<label>在售状态: </label>
				<select class="required " name="status" >
					<option value="0" ${(0 == status) ? 'selected' : '' }>未上市</option>
					<option value="1" ${(1 == status) ? 'selected' : '' }>在售</option>
					<option value="2" ${(2 == status) ? 'selected' : '' }>停售</option>
				</select>
			</p>
			<p>
				<label>是否删除: </label>
				<select class="required " name="deleted" >
					<option value="0" ${(0 == deleted) ? 'selected' : '' }>否</option>
					<option value="1" ${(1 == deleted) ? 'selected' : '' }>是</option>
				</select>
			</p>
			<p>
				<label>创建时间: </label><input
					class=""
					readonly="readonly"
					dateFmt="yyyy-MM-dd HH:mm:ss"
					value="<fmt:formatDate value='${entity.createTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>"
					name="createTime" />
			</p>
			<p>
				<label>更新时间: </label><input
					class=""
					readonly="readonly"
					dateFmt="yyyy-MM-dd HH:mm:ss"
					value="<fmt:formatDate value='${entity.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'></fmt:formatDate>"
					name="updateTime" />
			</p>
			<p>
				<label>热门推荐车系图1: </label><input class="textInput"
					${geli:not_oper('logo1') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'logo1')}" name="logo1" />
			</p>
			<p>
				<label>热门推荐车系图2: </label><input class="textInput"
					${geli:not_oper('logo2') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'logo2')}" name="logo2" />
			</p>
			<p>
				<label>导航排序值: </label><input class="textInput"
					${geli:not_oper('navSeq') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'navSeq')}" name="navSeq" />
			</p>
			<p>
				<label>首页热门车系排序值: </label><input class="textInput"
					${geli:not_oper('hotSeq') ? 'readonly="readonly"' : ''}
					value="${geli:display(entity, 'hotSeq')}" name="hotSeq" />
			</p>
			<p>
				<label>是否隐藏: </label>
				<select class="required " name="hidden" >
					<option value="0" ${(0 == hidden) ? 'selected' : '' }>否</option>
					<option value="1" ${(1 == hidden) ? 'selected' : '' }>是</option>
				</select>
			</p>
			<p>
				<label>更新人ID: </label><input class=""
					readonly="readonly"
					value="${geli:display(entity, 'updateUserId')}" name="updateUserId" />
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
