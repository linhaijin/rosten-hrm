/**
 * @author rosten
 */
define(["dojo/_base/connect",
        "dijit/registry",
        "rosten/util/general",
        "rosten/app/SystemApplication",
        "rosten/kernel/behavior"], function(connect,registry,General) {
	
	var general = new General();
	
	staff_search = function(){
		var content = {};
		
		var username = registry.byId("s_username");
		if(username.get("value")!=""){
			content.username = username.get("value");
		}
		
		var chinaName = registry.byId("s_chinaName");
		if(chinaName.get("value")!=""){
			content.chinaName = chinaName.get("value");
		}
		
		var departName = registry.byId("s_departName");
		if(departName.get("value")!=""){
			content.departName = departName.get("value");
		}
		
		var idCard = registry.byId("s_idCard");
		if(idCard.get("value")!=""){
			content.idCard = idCard.get("value");
		}
		
		var sex = registry.byId("s_sex");
		if(sex.get("value")!=""){
			content.sex = sex.get("value");
		}
		
		var politicsStatus = registry.byId("s_politicsStatus");
		if(politicsStatus.get("value")!=""){
			content.politicsStatus = politicsStatus.get("value");
		}
		
		var nativeAddress = registry.byId("s_nativeAddress");
		if(nativeAddress.get("value")!=""){
			content.nativeAddress = nativeAddress.get("value");
		}
		
		var city = registry.byId("s_city");
		if(city.get("value")!=""){
			content.city = city.get("value");
		}
		
		var status = registry.byId("s_status");
		if(status.get("value")!=""){
			content.status = status.get("value");
		}
		
		var workJob = registry.byId("s_workJob");
		if(workJob.get("value")!=""){
			content.workJob = workJob.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		
		case "userManage1":
			dom_rostenGrid.refresh(null,content);
			break;
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
		
		
	};
	
	staff_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		case "userManage1":
			registry.byId("s_username").set("value","");
			registry.byId("s_chinaName").set("value","");
			registry.byId("s_departName").set("value","");
			registry.byId("s_idCard").set("value","");
			registry.byId("s_sex").set("value","");
			registry.byId("s_politicsStatus").set("value","");
			registry.byId("s_nativeAddress").set("value","");
			registry.byId("s_city").set("value","");
			registry.byId("s_status").set("value","");
			registry.byId("s_workJob").set("value","");
			
			dom_rostenGrid.refresh();
			break;
			
		default:
			registry.byId("s_username").set("value","");
			registry.byId("s_chinaName").set("value","");
			registry.byId("s_departName").set("value","");
			registry.byId("s_idCard").set("value","");
			registry.byId("s_sex").set("value","");
			registry.byId("s_politicsStatus").set("value","");
			registry.byId("s_nativeAddress").set("value","");
			registry.byId("s_city").set("value","");
			registry.byId("s_status").set("value","");
			registry.byId("s_workJob").set("value","");
			rosten.kernel.refreshGrid();
			break;
		}	
	};
	
	
	//
	statsChange_search = function(){
		var content = {};
		
		var departName = registry.byId("s_departName");
		if(departName.get("value")!=""){
			content.departName = departName.get("value");
		}
		
		var chinaName = registry.byId("s_chinaName");
		if(chinaName.get("value")!=""){
			content.chinaName = chinaName.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
	};
	
	statsChange_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_departName").set("value","");
			registry.byId("s_chinaName").set("value","");
			break;
		}	
		
		rosten.kernel.refreshGrid();
	};
	
	//
	departChange_search = function(){
		var content = {};
		
		var inDepart = registry.byId("s_inDepart");
		if(inDepart.get("value")!=""){
			content.inDepart = inDepart.get("value");
		}
		
		var departName = registry.byId("s_departName");
		if(departName.get("value")!=""){
			content.departName = departName.get("value");
		}
		
		var chinaName = registry.byId("s_chinaName");
		if(chinaName.get("value")!=""){
			content.chinaName = chinaName.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
	};
	
	departChange_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_inDepart").set("value","");
			registry.byId("s_departName").set("value","");
			registry.byId("s_chinaName").set("value","");
			break;
		}	
		
		rosten.kernel.refreshGrid();
	};
	
	systemCode_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:systemCode_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    systemCode_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("systemCode", rosten.webPath + "/systemExtend/systemCodeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    add_systemCode = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("systemCode", rosten.webPath + "/systemExtend/systemCodeAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_systemCode = function() {
        change_systemCode();
    };
    change_systemCode = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("systemCode", rosten.webPath + "/systemExtend/systemCodeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_systemCode = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.readSync(rosten.webPath + "/systemExtend/systemCodeDelete", content,delete_callback);
        };
    };
	
	user_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:user_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    user_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("user", rosten.webPath + "/system/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
	
    personInfor_asignAccount = function(){
    	var unid = rosten._getGridUnid(dom_rostenGrid,"single");
        if (unid == "")
            return;
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.kernel.createRostenShowDialog(rosten.webPath + "/staff/asignAccount/"+ unid + "?companyId=" + companyId, {
            onLoadFunction : function() {

            }
        });
    };
    asignAccount_Submit = function(){
    	if(rosten.check_common("username","账号不正确！",true)==false) return false;
		if(rosten.check_common("password","密码不正确！",true)==false) return false;
		if(rosten.check_common("passwordcheck","确认密码不正确！",true)==false) return false;

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
		
		var content = {};
//		content.userNameFront = registry.byId("userNameFront").attr("value");
        rosten.readSync(rosten.webPath + "/staff/asignAccountSubmit", content, function(data) {
            if (data.result == "true") {
                rosten.kernel.hideRostenShowDialog();
                dom_rostenGrid.refresh();
                rosten.alert("成功!");
            }else if(data.result == "exist"){
            	rosten.alert("当前账号已存在!").queryDlgClose = function(){
            		registry.byId("username").set("value","");
            	};
            } else {
                rosten.alert("失败!");
            }
        },null,"rosten_form");
		
		
    };
    
	/*
	 *系统管理模块中的员工管理模块使用 
	 */
	personInfor_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:personInfor_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	personInfor_onMessageOpen = function(rowIndex){
		var rostenGrid = registry.byId("rosten_rostenGrid");
        var unid = _getGridItemValue(rostenGrid,rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var currentDepartId = rosten.variable.currentDeartId;
		rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId + "&currentDepartId=" + currentDepartId);
		rostenGrid.clearSelected();
	};
	
	//2014-12-07增加导出员工账号信息功能
	export_personInfor_account = function(){
		 var companyId = rosten.kernel.getUserInforByKey("companyid");
		 
		 var content = {};
			var query = "";
			var username = registry.byId("s_username");
			if(username.get("value")!=""){
				query += "&username="+username.get("value");
			}
			
			var chinaName = registry.byId("s_chinaName");
			if(chinaName.get("value")!=""){
				query += "&chinaName="+chinaName.get("value");
			}
			
			var departName = registry.byId("s_departName");
			if(departName.get("value")!=""){
				query += "&departName="+departName.get("value");
			}
			
			var idCard = registry.byId("s_idCard");
			if(idCard.get("value")!=""){
				query += "&idCard="+idCard.get("value");
			}
			
			var sex = registry.byId("s_sex");
			if(sex.get("value")!=""){
				query += "&sex="+sex.get("value");
			}
			
			var politicsStatus = registry.byId("s_politicsStatus");
			if(politicsStatus.get("value")!=""){
				query += "&politicsStatus="+politicsStatus.get("value");
			}
		 
		rosten.openNewWindow("exportAccount", rosten.webPath + "/staff/exportPersonAccount?companyId="+companyId+"&type="+ rosten.kernel.navigationEntity+query );
	};
	
	export_personInfor = function(){
		 var companyId = rosten.kernel.getUserInforByKey("companyid");
		 
		 var content = {};
			var query = "";
			var username = registry.byId("s_username");
			if(username.get("value")!=""){
				query += "&username="+username.get("value");
			}
			
			var chinaName = registry.byId("s_chinaName");
			if(chinaName.get("value")!=""){
				query += "&chinaName="+chinaName.get("value");
			}
			
			var departName = registry.byId("s_departName");
			if(departName.get("value")!=""){
				query += "&departName="+departName.get("value");
			}
			
			var idCard = registry.byId("s_idCard");
			if(idCard.get("value")!=""){
				query += "&idCard="+idCard.get("value");
			}
			
			var sex = registry.byId("s_sex");
			if(sex.get("value")!=""){
				query += "&sex="+sex.get("value");
			}
			
			var politicsStatus = registry.byId("s_politicsStatus");
			if(politicsStatus.get("value")!=""){
				query += "&politicsStatus="+politicsStatus.get("value");
			}
		 
		rosten.openNewWindow("export", rosten.webPath + "/staff/exportPerson?companyId="+companyId+"&type="+ rosten.kernel.navigationEntity+query );
	};
	
	import_personInfor = function(){
		 var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.kernel.createRostenShowDialog(rosten.webPath + "/staff/importStaff/"+ companyId, {
            onLoadFunction : function() {

            }
        });
	};
	//打印
	personInfor_print = function(){
		var unids = rosten.getGridUnid("multi");
		if (unids == ""){
			rosten.alert("请勾选要打印的数据！");
			return;
		}
		var content = {};
		content.id = unids;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPerson/"+unids);
	};
	personInfor_print_rzqd = function(){
		var unids = rosten.getGridUnid("multi");
		if (unids == ""){
			rosten.alert("请勾选要打印的数据！");
			return;
		}
		var content = {};
		content.id = unids;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPersonRzqd/"+unids);
	};
	personInfor_print_rztzs = function(){
		rosten.alert("尚未开通！");
		return;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPerson");
	};
	personInfor_rz = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userAdd?companyId=" + companyId + "&userid=" + userid + "&type=staffAdd&flowCode=staffAdd");
        
    };
    personInfor_zz = function() {
    	var unid = rosten.getGridItemValue1(rosten.kernel.getGrid(),"id");
        if (unid == "")
            return;
        var status = rosten.getGridItemValue1(rosten.kernel.getGrid(),"status");
        if(!(status=="试用"||status=="实习")){
        	rosten.alert("只有状态为<试用/实习>的员工才允许转正");
        	return;
        }
        
        var content = {};
        content.id = unid;
        rosten.read(rosten.webPath + "/staff/staffZZ", content, function(data,ioArgs){
        	if (data.result == "true" || data.result == true) {
                rosten.alert("成功!").queryDlgClose = function(){
                    rosten.kernel.refreshGrid();
                };
            } else {
                rosten.alert("失败!");
            }
        });
        
    };
	personInfor_dj = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userAdd?companyId=" + companyId + "&userid=" + userid);
    };
    personInfor_add = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var currentDepartId = rosten.variable.currentDeartId;
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userAdd?companyId=" + companyId + "&userid=" + userid + "&currentDepartId=" + currentDepartId);
    };
    read_personInfor = function() {
    	change_personInfor();
    };
    change_personInfor = function() {
        var unid = rosten._getGridUnid(dom_rostenGrid,"single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var currentDepartId = rosten.variable.currentDeartId;
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId + "&currentDepartId=" + currentDepartId);
        dom_rostenGrid.clearSelected();
    };
    delete_personInfor = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
        	var unids;
        	if(rosten.kernel.navigationEntity=="staffAdd" || rosten.kernel.navigationEntity=="staffRegi"){
        		unids = rosten.getGridUnid("multi");
        	}else{
        		unids = rosten._getGridUnid(dom_rostenGrid,"multi");
        	}
            
            if (unids == "") return;
            var content = {};
            content.id = unids;
            rosten.readSyncNoTime(rosten.webPath + "/staff/userDelete", content, function(data,ioArgs){
            	if(rosten.kernel.navigationEntity=="staffAdd" || rosten.kernel.navigationEntity=="staffRegi"){
            		delete_callback(data);
            	}else{
            		delete_callback(data,ioArgs,dom_rostenGrid);
            	}
            });
        };
    };
    personInfor_freshGrid = function(){
    	dom_rostenGrid.refresh();
    };
    personInfor_changePassword = function(){
    	var unid = rosten._getGridUnid(dom_rostenGrid,"single","userId");
        if (unid == ""){
        	rosten.alert("请先选择条目或当前不存在账号信息！");
        	return;
        }
    	rosten.kernel.createRostenShowDialog(rosten.webPath + "/system/passwordChangeShow1/"+ unid, {
            onLoadFunction : function() {

            }
        });
    };
	_getGridItemValue=function(rostenGrid,index,name){
    	var grid = rostenGrid.getGrid();
    	var item = grid.getItem(index);
    	var store = rostenGrid.getStore();
    	return store.getValue(item, name);
    };
    changePassword = function(){
    	var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
    	rosten.kernel.createRostenShowDialog(rosten.webPath + "/system/passwordChangeShow1/"+ unid, {
            onLoadFunction : function() {

            }
        });
    };
    changePasswordSubmit = function(){
        var newpassword = registry.byId("newpassword");
        if (!newpassword.isValid()) {
            rosten.alert("新密码不正确！").queryDlgClose = function() {
                newpassword.focus();
            };
            return;
        }
        var newpasswordcheck = registry.byId("newpasswordcheck");
        if (!newpasswordcheck.isValid()) {
            rosten.alert("确认密码不正确！").queryDlgClose = function() {
                newpasswordcheck.focus();
            };
            return;
        }
        if (newpassword.getValue() != newpasswordcheck.getValue()) {
            rosten.alert("新密码与确认密码不一致！").queryDlgClose = function() {
                newpassword.focus();
            };
            return;
        }
        var content = {};
        content.newpassword = newpassword.getValue();
        content.id = registry.byId("dealunid").getValue();

        rosten.read(rosten.webPath + "/system/passwordChangeSubmit1", content, function(data) {
            if (data.result == "true") {
                rosten.kernel.hideRostenShowDialog();
                //2014-7-15修改为按部门方式查询并修改
//                rosten.kernel.getGrid().clearSelected();
                dom_rostenGrid.clearSelected();
                rosten.alert("修改密码成功!");
            } else if (data.result == "error") {
                rosten.alert("当前密码错误,修改密码失败!");
            } else {
                rosten.alert("修改密码失败!");
            }
        });
    };
    import_user = function(){
    	var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.kernel.createRostenShowDialog(rosten.webPath + "/system/importUser/"+ companyId, {
            onLoadFunction : function() {
            }
        });
    };
    add_user = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("user", rosten.webPath + "/system/userAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_user = function() {
        change_user();
    };
    change_user = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("user", rosten.webPath + "/system/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_user = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/userDelete", content, delete_callback);
        };
    };
    add_smsGroup = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("smsgroup", rosten.webPath + "/system/smsgroupAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_smsGroup = change_smsGroup = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("smsgroup", rosten.webPath + "/system/smsgroupShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
    };
    delete_smsGroup = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/smsgroupDelete", content, delete_callback);
        };
    };
    add_service = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("service", rosten.webPath + "/system/serviceAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_service = function() {
        change_service();
    };
    change_service = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("service", rosten.webPath + "/system/serviceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_service = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/serviceDelete", content, delete_callback);
        };
    };
    open_service = function(){
    	var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        var content = {status:"是"};
        content.id = unids;
        rosten.read(rosten.webPath + "/system/serviceStatus", content, delete_callback);
    };
    close_service = function(){
    	var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        var content = {status:"否"};
        content.id = unids;
        rosten.read(rosten.webPath + "/system/serviceStatus", content, delete_callback);
    };
    formatResourceTab = function(value){
    	if(value && value!=""){
			var imgs = general.splitString(value,",");
			var _values="";
			for(var i = 0; i < imgs.length; i ++){
				if(_values==""){
					_values = "<img style=\"margin-left:4px\" src=\"" + rosten.webPath + "/" + imgs[i] + "\" />";
				}else{
					_values += "<img style=\"margin-left:4px\" src=\"" + rosten.webPath + "/" + imgs[i] + "\" />";
				}
			}
			return _values;
		}else{
			return "";
		}
    };
    resource_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:resource_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    resource_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("resource", rosten.webPath + "/system/resourceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    resource_setDefault = function(){
    	var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        var content = {isDefault:"true"};
        content.id = unids;
        rosten.read(rosten.webPath + "/system/resourceSetDefault", content, rosten.commonCallback);
    };
    resource_cancelDefault = function(){
    	var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        var content = {isDefault:"false"};
        content.id = unids;
        rosten.read(rosten.webPath + "/system/resourceSetDefault", content, rosten.commonCallback);
    };
    add_resource = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("resource", rosten.webPath + "/system/resourceAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_resource = function() {
        change_resource();
    };
    change_resource = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("resource", rosten.webPath + "/system/resourceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_resource = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/resourceDelete", content, delete_callback);
        };
    };
    permission_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:permission_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    permission_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("permission", rosten.webPath + "/system/permissionShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    add_permission = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("permission", rosten.webPath + "/system/permissionAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_permission = function() {
        change_permission();
    };
    change_permission = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("permission", rosten.webPath + "/system/permissionShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_permission = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/permissionDelete", content, delete_callback);
        };
    };
    role_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:role_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    role_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("role", rosten.webPath + "/system/roleShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    add_role = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("role", rosten.webPath + "/system/roleAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_role = function() {
        change_role();
    };
    change_role = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("model", rosten.webPath + "/system/roleShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_role = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten._read(rosten.webPath + "/system/roleDelete", content, delete_callback);
        };
    };
    group_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:group_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    group_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("group", rosten.webPath + "/system/groupShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    add_group = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("group", rosten.webPath + "/system/groupAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_group = function() {
        change_group();
    };
    change_group = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("model", rosten.webPath + "/system/groupShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_group = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/groupDelete", content, delete_callback);
        };
    };
    model_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:model_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    model_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("model", rosten.webPath + "/system/modelShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    model_deleteFlow = function(){
   	 var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        rosten.readNoTime(rosten.webPath + "/system/modelDeleteFlow1/" + unids, {}, function(data){
       	 if (data.result == "true" || data.result == true) {
                rosten.alert("成功!");
                rosten.kernel.refreshGrid();
            } else {
                rosten.alert("失败!");
            }
        });
        
   };
   model_addFlow = function(){
   	 var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var id = "sys_relationFlowDialog";
        var initValue = [];
        initValue.push(rosten.getGridSelectedValue("relationFlowName"));
        
        rosten.selectDialog("关联流程选择", id, rosten.webPath + "/modeler/flowSelect?companyId="+companyId, false, initValue);
        rosten[id].callback = function(data) {
       	 var content = {flowId:data[0].id,flowName:data[0].name};
            rosten.read(rosten.webPath + "/system/modelAddFlow1/" + unids, content, function(_data){
           	 if (_data.result == "true" || _data.result == true) {
                    rosten.alert("成功!");
                    rosten.kernel.refreshGrid();
                } else {
                    rosten.alert("失败!");
                }
            });
        };
        
   };
    add_model = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("model", rosten.webPath + "/system/modelAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_model = function() {
        change_model();
    };
    change_model = function() {
        //获取参数数据
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("model", rosten.webPath + "/system/modelShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_model = function() {
        //获取参数数据
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            rosten.read(rosten.webPath + "/system/modelDelete/" + unids, content, delete_callback);
        };
    };
    delete_sms = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/smsDelete", content, delete_callback);
        };
    };
    change_question = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        rosten.openNewWindow("question", rosten.webPath + "/system/questionShow/" + unid);
        rosten.kernel.getGrid().clearSelected();
    };
    read_question = function() {
        change_question();
    };
    delete_question = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/system/questionDelete", content, delete_callback);
        };
    };
    add_administrator = function() {
        var unid = rosten.kernel.getUserInforByKey("idnumber");
        rosten.openNewWindow("administrator", rosten.webPath + "/system/userAdd?userid=" + unid);
    };
    read_administrator = function() {
        change_administrator();
    };
    change_administrator = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        rosten.openNewWindow("company", rosten.webPath + "/system/userShow/" + unid + "?userid=" + userid);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_administrator = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.readSync(rosten.webPath + "/system/userDelete", content, delete_callback);
        };
    };
    add_company = function() {
        var unid = rosten.kernel.getUserInforByKey("idnumber");
        rosten.openNewWindow("company", rosten.webPath + "/system/companyAdd?userid=" + unid);
    };
    read_company = function() {
        change_company();
    };
    change_company = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        rosten.openNewWindow("company", rosten.webPath + "/system/companyShow/" + unid + "?userid=" + userid);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_company = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.readSync(rosten.webPath + "/system/companyDelete", content, delete_callback);
        };
    };
    userType_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:userType_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    userType_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("userType", rosten.webPath + "/system/userTypeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    add_userType = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("userType", rosten.webPath + "/system/userTypeAdd?companyId=" + companyId + "&userid=" + userid);
    };
    read_userType = function() {
        change_userType();
    };
    change_userType = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("userType", rosten.webPath + "/system/userTypeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_userType = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.readSync(rosten.webPath + "/system/userTypeDelete", content, delete_callback);
        };
    };
    uploadLogo = function() {
        var upload = rosten.upLoad();
        rosten.kernel.addConnect(connect.connect(upload, 'callback', function() {
            rosten.alert("上传成功后，请使用<重新登录系统>查看变化！");
        }));
    };
    delete_callback = function(data,ioArgs,gridDom) {
        if (data.result == "true" || data.result == true) {
            rosten.alert("成功删除!").queryDlgClose = function(){
            	if(gridDom){
                	gridDom.refresh();
                }else{
                	rosten.kernel.refreshGrid();
                }
            };
        } else {
            rosten.alert("删除失败!");
        }
    };
    /*
     freshGrid = function(){
     rosten.kernel.refreshGrid();
     }*/

    /*
     * 此功能默认必须存在
     */
    show_systemNaviEntity = function(oString) {
        switch (oString) {
	        case "systemLogManage":
	            var naviJson = {
	                identifier : oString,
	                actionBarSrc : rosten.webPath + "/systemAction/systemLogView",
	                gridSrc : rosten.webPath + "/system/systemLogGrid"
	            };
	            rosten.kernel.addRightContent(naviJson);
	            break;
            case "smsManage":
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/smsView",
                    gridSrc : rosten.webPath + "/system/smsGrid"
                };
                rosten.kernel.addRightContent(naviJson);
                break;
            case "smsGroupManage":
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/smsGroupView",
                    gridSrc : rosten.webPath + "/system/smsGroupGrid"
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = read_smsGroup;
                break;
            case "questionManage":
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/questionView",
                    gridSrc : rosten.webPath + "/system/questionGrid"
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = read_question;
                break;
            case "advertiseManage":
                rosten.kernel.setHref(rosten.webPath + "/system/advertise", oString);
                break;
            case "logSet":
                rosten.kernel.setHref(rosten.webPath + "/system/logoSet", oString);
                break;
            case "systemToolManage":
                rosten.kernel.setHref(rosten.webPath + "/system/systemTool", oString);
                break;
            case "companyManage":
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/companyView",
                    gridSrc : rosten.webPath + "/system/companyGrid"
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = read_company;
                break;
            case "adminManage":
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/administratorView",
                    gridSrc : rosten.webPath + "/system/administratorGrid"
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = read_administrator;
                break;
            case "modelManage":
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/modelView",
                    gridSrc : rosten.webPath + "/system/modelGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_model;
                break;

            case "departManage" :
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                rosten.kernel.setHref(rosten.webPath + "/system/depart?companyId=" + companyId, oString);
                break;
            case "groupManage" :
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/groupView",
                    gridSrc : rosten.webPath + "/system/groupGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_group;
                break;
            case "userManage" :
            	//账号管理
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/userView",
                    gridSrc : rosten.webPath + "/system/userGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_user;
                break;
                
            case "userManage1" :
              //2014-7-13修改为人事系统中的员工信息
              var companyId = rosten.kernel.getUserInforByKey("companyid");
              rosten.kernel.setHref(rosten.webPath + "/staff/depart?companyId=" + companyId, oString);
              break; 
            case "userTypeManage" :
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/userTypeView",
                    gridSrc : rosten.webPath + "/system/userTypeGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_userType;
                break;
            case "roleManage" :
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/roleView",
                    gridSrc : rosten.webPath + "/system/roleGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_role;
                break;
            case "permissionManage":
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/permissionView",
                    gridSrc : rosten.webPath + "/system/permissionGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_permission;
                break;
            case "resourceManage":
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/resourceView",
                    gridSrc : rosten.webPath + "/system/resourceGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_resource;
                break;
            case "serviceManage":
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemAction/serviceView",
                    gridSrc : rosten.webPath + "/system/serviceGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                rostenGrid.onRowDblClick = change_service;
                break;
            case "systemModelInit":
            	var _1 = rosten.confirm("系统初始化后将无法恢复，是否继续?");
                _1.callback = function() {
                	//初始化人事系统相关模块
                	var companyId = rosten.kernel.getUserInforByKey("companyid");
                	rosten.readSync(rosten.webPath + "/hrm/modelInit/" + companyId, {}, function(data){
                		if (data.result == "true" || data.result == true) {
                            rosten.alert("成功!").queryDlgClose = function(){
                            	refreshSystem();
                            }
                        } else {
                            rosten.alert("失败!");
                        }
                	});
                };
                break;  
            case "systemCodeManage":
                var companyId = rosten.kernel.getUserInforByKey("companyid");
                var naviJson = {
                    identifier : oString,
                    actionBarSrc : rosten.webPath + "/systemExtendAction/systemCodeView",
                    gridSrc : rosten.webPath + "/systemExtend/systemCodeGrid?companyId=" + companyId
                };
                rosten.kernel.addRightContent(naviJson);

                var rostenGrid = rosten.kernel.getGrid();
                break;
        }

    };
    connect.connect("show_naviEntity", show_systemNaviEntity);
});
