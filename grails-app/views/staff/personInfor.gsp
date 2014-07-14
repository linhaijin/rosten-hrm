<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>个人概况</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<style type="text/css">
</style>

<body>
	<table border="0" width="740" align="left">
		<tr>
		    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>姓名：</div></td>
		    <td width="250">
		    	<input id="chinaName" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"chinaName",trim:true,${fieldAcl.isReadOnly("chinaName")},
							value:"${personInforEntity?.user?.chinaName}"
	                '/>
		    </td>
		    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>所属部门：</div></td>
		    <td width="250">
		    	<input id="allowdepartsName" data-dojo-type="dijit/form/ValidationTextBox" 
	               	data-dojo-props='name:"allowdepartsName",${fieldAcl.isReadOnly("allowdepartsName")},
	               		trim:true,
	               		required:true,
						value:"${personInforEntity?.user?.getDepartName()}"
	          	'/>
	         	<g:hiddenField name="allowdepartsId" value="${personInforEntity?.user?.getDepartEntity()?.id }" />
				<button data-dojo-type="dijit.form.Button" data-dojo-props='onClick:function(){selectDepart("${createLink(controller:'system',action:'departTreeDataStore',params:[companyId:company?.id])}")}'>选择</button>
           </td>
		</tr>
		<tr>
		 <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>性别：</div></td>
		  <td width="250">
		    	<input id="sex" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"sex",trim:true,${fieldAcl.isReadOnly("sex")},
							value:"${personInforEntity?.sex}"
	                '/>
		    </td>
		    
		     <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>出生日期：</div></td>
		  <td width="250">
		    	<input id="birthday" data-dojo-type="dijit/form/DateTextBox" 
	                 	data-dojo-props='name:"birthday",trim:true,${fieldAcl.isReadOnly("birthday")},
							value:"${personInforEntity?.user?.getFormatteBirthday()}"
	                '/>
		    </td>
		</tr>
		<tr>
		 <td width="120"><div align="right">曾用名：</div></td>
		  <td width="250">
		    	<input id="usedName" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"usedName",trim:true,${fieldAcl.isReadOnly("usedName")},
							value:"${personInforEntity?.user?.usedName}"
	                '/>
		    </td>
		    
		     <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>身份证号：</div></td>
		  <td width="250">
		    	<input id="idCard" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"idCard",trim:true,${fieldAcl.isReadOnly("idCard")},
							value:"${personInforEntity?.idCard}"
	                '/>
		    </td>
		</tr>
		
		
		<tr>
		 <td width="120"><div align="right">国籍：</div></td>
		  <td width="250">
		    	<input id="city" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"city",trim:true,${fieldAcl.isReadOnly("city")},
							value:"${personInforEntity?.city}"
	                '/>
		    </td>
		    
		     <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>民族：</div></td>
		  <td width="250">
		    	<input id="nationality" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"nationality",trim:true,${fieldAcl.isReadOnly("nationality")},
							value:"${personInforEntity?.nationality}"
	                '/>
		    </td>
		</tr>
		
		<tr>
		 <td width="120"><div align="right">出生地：</div></td>
		  <td width="250">
		    	<input id="birthAddress" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"birthAddress",trim:true,${fieldAcl.isReadOnly("birthAddress")},
							value:"${personInforEntity?.birthAddress}"
	                '/>
		    </td>
		    
		     <td width="120"><div align="right">籍贯：</div></td>
		  <td width="250">
		    	<input id="nativeAddress" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"nativeAddress",trim:true,${fieldAcl.isReadOnly("nativeAddress")},
							value:"${personInforEntity?.nativeAddress}"
	                '/>
		    </td>
		</tr>
		
		<tr>
		 <td width="120"><div align="right">政治面貌：</div></td>
		  <td width="250">
		    	<input id="politicsStatus" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"politicsStatus",trim:true,${fieldAcl.isReadOnly("politicsStatus")},
							value:"${personInforEntity?.politicsStatus}"
	                '/>
		    </td>
		    
		     <td width="120"><div align="right">婚姻状况：</div></td>
		  <td width="250">
		    	<input id="marriage" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"marriage",trim:true,${fieldAcl.isReadOnly("marriage")},
							value:"${personInforEntity?.marriage}"
	                '/>
		    </td>
		</tr>
		
		<tr>
		 <td width="120"><div align="right">宗教信仰：</div></td>
		  <td width="250">
		    	<input id="religion" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"religion",trim:true,${fieldAcl.isReadOnly("religion")},
							value:"${personInforEntity?.religion}"
	                '/>
		    </td>
		    
		     <td width="120"><div align="right">血型：</div></td>
		  <td width="250">
		    	<input id="blood" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"blood",trim:true,${fieldAcl.isReadOnly("blood")},
							value:"${personInforEntity?.blood}"
	                '/>
		    </td>
		</tr>
		
		<tr>
		 <td width="120"><div align="right">健康状况：</div></td>
		  <td width="250" colspan="3">
		    	<input id="health" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='name:"health",trim:true,${fieldAcl.isReadOnly("health")},
							value:"${personInforEntity?.health}"
	                '/>
		    </td>

		</tr>
		
	</table>
</body>
</html>
