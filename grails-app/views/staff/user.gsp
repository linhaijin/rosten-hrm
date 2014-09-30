<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>人员信息</title>
    <style type="text/css">
    	body{
			overflow:auto;
		}
		.rosten .rostenTitleGrid .dijitTitlePaneContentInner{
			padding:2px 1px 1px 1px;
			height:200px;
		}
    </style>
	<script type="text/javascript">
	require(["dojo/parser",
		 		"dojo/_base/kernel",
		 		"dijit/registry",
		 		"dijit/form/Button",
		     	"rosten/widget/ActionBar",
		     	"dijit/layout/TabContainer",
				"dijit/layout/ContentPane",
		     	"dijit/form/TextBox",
		     	"dijit/form/RadioButton",
		     	"dijit/form/ValidationTextBox",
		     	"dijit/form/SimpleTextarea",
		     	"dijit/form/Button",
		     	"dijit/Dialog",
				"dojox/grid/DataGrid",
		     	"dijit/form/RadioButton",
		     	"dijit/form/FilteringSelect",
		     	"dijit/form/ComboBox",
		     	"rosten/app/SystemApplication",
		     	"rosten/app/StaffApplication"],
			function(parser,kernel,registry,ActionBar){
				kernel.addOnLoad(function(){
					rosten.init({webpath:"${request.getContextPath()}",dojogridcss : true});
					rosten.cssinit();
				});
			user_add_check = function(){
				<g:if test='${departId}'>
					if(check_common("username","账号不正确！",true)==false) return false;
					if(check_common("password","密码不正确！",true)==false) return false;
					if(check_common("passwordcheck","确认密码不正确！",true)==false) return false;
	
					var password = registry.byId("password");
					var passwordcheck = registry.byId("passwordcheck");
					if(password.attr("value")!=passwordcheck.attr("value")){
						rosten.alert("密码不一致！").queryDlgClose = function(){
							password.attr("value","");
							passwordcheck.attr("value","");
							password.focus();
						};
						return false;
					}
				</g:if>

				//个人概况检查
				if(check_common("chinaName","姓名不正确！",true)==false) return false;
				if(check_common("userTypeName","用户类型不正确！",true)==false) return false;
				if(check_common("allowdepartsName","所属部门不正确！")==false) return false;
				if(check_common("idCard","身份证号不正确！",true)==false) return false;

				//通讯方式
				
				return true;
			};
			user_add = function(){
				if(user_add_check()==false) return;
				var content = {};
				<g:if test='${!userType.equals("super")  }'>
					content.companyId = "${company?.id}";
					//content.userNameFront = registry.byId("userNameFront").attr("value");
				</g:if>
				rosten.readSync(rosten.webPath + "/staff/userSave",content,function(data){
					if(data.result=="true"){
						rosten.alert("保存成功！").queryDlgClose= function(){
							page_quit();	
						};
					}else if(data.result=="repeat"){
						rosten.alert("账号冲突，保存失败!");
					}else{
						rosten.alert("保存失败!");
					}
				},null,"rosten_form");
			};
			page_quit = function(){
				if(window.opener.rosten.kernel.navigationEntity=="newStaffAdd"){
					window.opener.rosten.kernel.refreshGrid();
				}else{
					window.opener.dom_rostenGrid.refresh();
				}
		        window.close();
			};
			check_common = function(fieldStr,alertStr,isFocus){
				var _dom = registry.byId(fieldStr);
				if(_dom && !_dom.isValid()){
					if(isFocus){
						rosten.alert(alertStr).queryDlgClose = function(){
							_dom.focus();
						};
					}else{
						rosten.alert(alertStr);
					}
					return false;
				}
				return true;
			};
	});
    </script>
</head>
<body>
	<div class="rosten_action">
		<div data-dojo-type="rosten/widget/ActionBar" id="rosten_actionBar" 
			data-dojo-props='actionBarSrc:"${createLink(controller:'staffAction',action:'staffForm',params:[userId:loginUser?.id])}"'></div>
	</div>
	<form class="rosten_form" id="rosten_form" onsubmit="return false;" style="text-align:left;">
	<div>
	<g:if test='${departId}'>
		<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${user?.id }"' />
	</g:if>
        <input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
	</div>
	<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
        <div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
			
			
			<g:if test='${departId}'>
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"账号信息 <span style=\"color:red;margin-left:5px\">(必填信息)</span>",toggleable:false,moreText:"",height:"100px",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>账号：</div></td>
					    <td width="250">
					    	<input id="userNameFront" data-dojo-type="dijit/form/ValidationTextBox" 
                               	data-dojo-props='name:"userNameFront",style:{width:"50px"},
             						value:"${company?.shortName}-",disabled:true
                            '/>
					    	
				    		<input id="username" data-dojo-type="dijit/form/ValidationTextBox" 
                               	data-dojo-props='name:"username",${fieldAcl.isReadOnly("username")},
                               		trim:true,required:true,
                               		promptMessage:"请正确输入账号...",
                               		style:{width:"125px"},
                               		<g:if test="${username && !"".equals(username)}">disabled:true,</g:if>
             							value:"${username}"
                               '/>
					    	
					    </td>
					    <td width="120"><div align="right">具有角色：</div></td>
					    <td width="250">
					    	<input id="allowrolesName" data-dojo-type="dijit/form/ValidationTextBox"
                					data-dojo-props='trim:true,readOnly:true,
                						value:"${allowrolesName }"
                				'/>
                 				
                 					<g:hiddenField name="allowrolesId" value="${allowrolesId }" />
									<button data-dojo-type="dijit.form.Button" 
										data-dojo-props = 'onClick:function(){selectRole("${createLink(controller:'system',action:'roleSelect',params:[companyId:company?.id])}")}'
									>选择</button>
                 				
			           </td>
					</tr>
						<tr>
						    <td><div align="right"><span style="color:red">*&nbsp;</span>密码：</div></td>
						    <td>
						    	<input id="password" data-dojo-type="dijit/form/ValidationTextBox" 
	                               	data-dojo-props='name:"password",
	                               		type:"password",
	                               		trim:true,
	                               		required:true,
	                               		promptMessage:"请正确输入密码...",
	             						value:"${user?.password}"
	                           	'/>
				            </td>
						    <td><div align="right"><span style="color:red">*&nbsp;</span>密码确认：</div></td>
						    <td>
						    	<input id="passwordcheck" data-dojo-type="dijit/form/ValidationTextBox" 
                                	data-dojo-props='name:"passwordcheck",
                                		type:"password",
                                		trim:true,
                                		required:true,
                                		promptMessage:"请正确输入密码...",
              							value:"${user?.password}"
                                '/>
				            </td>    
						</tr>
					
					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>CSS样式表：</div></td>
					    <td>
					    	<select id="cssStyle" data-dojo-type="dijit/form/FilteringSelect"
                           		data-dojo-props='name:"cssStyle",
                           			autoComplete:false,${fieldAcl.isReadOnly("cssStyle")},
            						value:"${(user!=null && user.cssStyle!=null)?user.cssStyle:"normal" }"
                            '>
	                            <option value="normal">标准样式</option>
								<option value="cfyl">春风杨柳</option>
								<option value="hbls">环保绿色</option>
								<option value="jqsy">金秋十月</option>	
								<option value="jsnz">金色农庄</option>
								<option value="lhqh">蓝灰情怀</option>
								<option value="rose">浪漫玫瑰</option>
								<option value="shys">深红夜思</option>
                              
                           	</select>
			            </td>
					    <td></td>
					    <td></td>    
					</tr>
					
				</table>
			</div>
			</g:if>
			
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"个人概况  <span style=\"color:red;margin-left:5px\">(必填信息)</span>",toggleable:false,moreText:"",height:"260px",marginBottom:"2px",
				href:"${createLink(controller:'staff',action:'getPersonInfor',id:personInfor?.id,params:[departId:departId])}"
			'>
			</div>
			
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"通讯方式",toggleable:false,moreText:"",height:"150px",marginBottom:"2px",
				href:"${createLink(controller:'staff',action:'getContactInfor',id:personInfor?.id)}"'>
			</div>

			<div data-dojo-type="rosten/widget/TitlePane" 
				data-dojo-props='"class":"rostenTitleGrid",title:"家庭成员",toggleable:true,_moreClick:staff_addFamily,moreText:"<span style=\"color:#108ac6\">增加成员</span>",marginBottom:"2px"'>
				<div data-dojo-type="rosten/widget/RostenGrid" id="staffFamilyGrid" data-dojo-id="staffFamilyGrid"
					data-dojo-props='showPageControl:false,url:"${createLink(controller:'staff',action:'getFamily',id:personInfor?.id)}"'></div>
			</div>
			
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"学历学位",toggleable:false,moreText:"",height:"100px",marginBottom:"2px",
				href:"${createLink(controller:'staff',action:'getDegree',id:user?.id)}"'>
			</div>
			
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"工作经历",toggleable:false,moreText:"",height:"260px",marginBottom:"2px",
				href:"${createLink(controller:'staff',action:'getWorkResume',id:user?.id)}"'>
			</div>
			
        </div>
        
        <g:if test='${"rz".equals(type)}'>
			<div data-dojo-type="dijit/layout/ContentPane" title="面试结果" data-dojo-props=''>
		
			</div>
		</g:if>
		<g:else>
			<div data-dojo-type="dijit/layout/ContentPane" title="合同信息" data-dojo-props=''>
		
			</div>
			<div data-dojo-type="dijit/layout/ContentPane" title="劳资福利" data-dojo-props=''>
			
			</div>
		</g:else>
	</div>
	
	</form>
</body>
</html>