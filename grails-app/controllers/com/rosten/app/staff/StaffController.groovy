package com.rosten.app.staff

import com.rosten.app.system.Company
import com.rosten.app.system.User
import com.rosten.app.share.ShareService
import com.rosten.app.util.FieldAcl
import com.rosten.app.system.Depart
import com.rosten.app.util.Util
import com.rosten.app.system.UserDepart
import grails.converters.JSON
import com.rosten.app.system.UserRole
import com.rosten.app.system.UserType
import com.rosten.app.system.SystemService
import com.rosten.app.system.Role
import java.io.OutputStream
import com.rosten.app.export.ExcelExport
import com.rosten.app.export.WordExport;

class StaffController {
	def springSecurityService
	def systemService
	def shareService
	def staffService
	
	def asignAccount ={
		def model =[:]
		model["company"] = Company.get(params.companyId)
		
		def personInfor = PersonInfor.get(params.id)
		def user = personInfor.user
		model["user"] = user
		model["personInforId"] = params.id
		
		if(user){
			//具有角色
			def allowrolesName=[]
			def allowrolesId =[]
			UserRole.findAllByUser(user).each{
				allowrolesName << it.role.authority
				allowrolesId << it.role.id
			}
			
			model["allowrolesName"] = allowrolesName.join(',')
			model["allowrolesId"] = allowrolesId.join(",")
			model["username"] = Util.strRight(user.username, "-")
		}
		
		
		render(view:'/staff/asignAccount',model:model)
	}
	def asignAccountSubmit ={
		def json
		try{
			//所属机构
			def currentUser = springSecurityService.getCurrentUser()
			def company = currentUser.company
			
			def user 
			if(params.id && !"".equals(params.id)){
				user = User.get(params.id)
			}else{
				user = new User()
				user.enabled = true
			}
			if(params.userNameFront){
				params.username = params.userNameFront + params.username
			}
			
			user.properties = params
			user.clearErrors()
			
			user.company = company
			user.save()
			
			def personInfor = PersonInfor.get(params.personInforId)
			personInfor.user = user
			
			if(personInfor.save(flush:true)){
				json = [result:'true']
			}else{
				json = [result:'false']
			}
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	def importStaff ={
		def model =[:]
		model["company"] = Company.get(params.id)
		render(view:'/staff/importStaff',model:model)
	}
	def staffRetire ={
		//退休
		def json
		try{
			def user = User.get(params.id)
			def personInfor = PersonInfor.findByUser(user)
			if(personInfor){
				personInfor.status = "已退休"
				personInfor.save()
				
				//增加处理日志
				def staffLog = new StaffLog()
				staffLog.type = "退休"
				staffLog.dealUser = springSecurityService.getCurrentUser()
				staffLog.user = user
				staffLog.reson = params.dataStr
				
				staffLog.save(flush:true)
			}
				
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	
	def staffLeave ={
		//离职
		def json
		try{
			def user = User.get(params.id)
			def personInfor = PersonInfor.findByUser(user)
			if(personInfor){
				personInfor.status = "已离职"
				personInfor.save()
				
				//增加处理日志
				def staffLog = new StaffLog()
				staffLog.type = "离职"
				staffLog.dealUser = springSecurityService.getCurrentUser()
				staffLog.user = user
				staffLog.reson = params.dataStr
				
				staffLog.save(flush:true)
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	
	def staffChangeDepart ={
		def json,oldDepartName
		try{
			def user = User.get(params.userId)
			def departEntity = Depart.get(params.newDepartId)
			oldDepartName = user.getDepartName()
			
			if(departEntity){
				UserDepart.removeAll(user)
				UserDepart.create(user, departEntity)
				
				//增加处理日志
				def staffLog = new StaffLog()
				staffLog.type = "部门调动"
				staffLog.oldDepart = oldDepartName
				staffLog.nowDepart = departEntity.departName
				staffLog.dealUser = springSecurityService.getCurrentUser()
				staffLog.user = user
				
				staffLog.save(flush:true)
				
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
		
	}
	def serachPerson ={
		def company = Company.get(params.companyId)
		
		def c = User.createCriteria()
		def _userList = c.list({
			eq("company",company)
			or{
				like("username","%" + params.serchInput +  "%")
				like("chinaName","%" + params.serchInput +  "%")
				like("telephone","%" + params.serchInput +  "%")
			}
		})
		def smap =[:]
		if(_userList && _userList.size()>0){
			def _user = _userList[0]
			
			smap["userId"] = _user.id
			smap["username"] = _user.username
			smap["phone"] = _user.telephone
			smap["mobile"] = _user.telephone
			smap["email"] = _user.email
			
			smap["userDepartId"] = _user.getDepartEntity()?.id
			smap["userDepart"] = _user.getDepartEntity()?.departName
			
			def personInfor = PersonInfor.findByUser(_user)
			if(personInfor){
				smap["sex"] = personInfor.sex
				smap["idCard"] = personInfor.idCard
				smap["birthday"] = personInfor.birthday
				smap["city"] = personInfor.city
				smap["nationality"] = personInfor.nationality
				smap["birthAddress"] = personInfor.birthAddress
				smap["nativeAddress"] = personInfor.nativeAddress
				smap["politicsStatus"] = personInfor.politicsStatus
				smap["marriage"] = personInfor.marriage
				smap["religion"] = personInfor.religion
			}
		}
		render smap as JSON
	}
	def staffDepartChange ={
		def model = [:]
		model.companyId = params.companyId
		render(view:'/staff/changeDepart',model:model)
	}
	def userDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def user = User.get(it)
				if(user){
					//先刪除关联信息
					PersonInfor.findByUser(user)?.delete(flush:true)
					user.delete(flush: true)
				}
			}
			json = [result:'true']
		}catch(Exception e){
			log.debug(e);
			json = [result:'error']
		}
		render json as JSON
		
	}
	
	def userSave ={
		def model=[:]
		//用户类型
		def userType
		if(params.userTypeName){
			userType = UserType.findByTypeName(params.userTypeName)
		}
		
		//所属机构
		def company
		if(params.companyId){
			company = Company.get(params.companyId)
		}
		
		//账号信息
		def user
		if(params.username || params.id){
			/*
			 *保存登录用户账号信息;如果账号已经存在则登录名不变
			 */
			def username
			user = new User()
			if(params.id && !"".equals(params.id)){
				user = User.get(params.id)
				username = user.username
			}else{
				user.enabled = true
				if(params.userNameFront){
					username = params.userNameFront + params.username
				}
			}
			user.properties = params
			user.username = username
			user.clearErrors()
			
			if(params.userTypeName && !params.userTypeName.equals(user.getUserTypeName())){
				if(userType){
					user.userTypeEntity = userType
				}
			}
			
			if(params.companyId){
				user.company = company
			}
			
			user.save()
		}
		
		//添加个人概况信息
		def personInfor = new PersonInfor()
		if(params.personInforId){
			personInfor = PersonInfor.get(params.personInforId)
		}
		personInfor.properties = params
		personInfor.user = user
		
		personInfor.clearErrors()
		
		personInfor.birthday = Util.convertToTimestamp(params.birthday)
		personInfor.intoday = Util.convertToTimestamp(params.intoday)
		personInfor.staffOnDay = Util.convertToTimestamp(params.staffOnDay)
		
		if(userType && !userType.equals(personInfor.userTypeEntity)){
			personInfor.userTypeEntity = userType
		}
		personInfor.company = company
		
		//部门信息
		def depart
		if(params.allowdepartsId){
			//首先清空原有的departs
			if(personInfor.departs && personInfor.size()>0){
				personInfor.departs.clear()
			}
			
			depart = Depart.get(params.allowdepartsId)
			if(depart){
				personInfor.addToDeparts(depart)
			}
		}
		
		if(personInfor.save(flush:true)){
			if(user){
				UserDepart.removeAll(user)
				if(params.allowdepartsId){
					if(depart){
						UserDepart.create(user, depart)
					}
				}
				
				UserRole.removeAll(user)
				if(params.allowrolesId){
					params.allowrolesId.split(",").each{
						def role = Role.get(it)
						UserRole.create(user, role)
					}
				}
			}
			
			//通讯方式----------------------
			def contactInfor = ContactInfor.findByPersonInfor(personInfor)
			if(!contactInfor){
				contactInfor = new ContactInfor()
			}
			//清除params中的id号
			params.remove("id")
			
			contactInfor.properties = params
			contactInfor.clearErrors()
			
			contactInfor.personInfor = personInfor
			contactInfor.save(flush:true)
			
			//家庭成员--------------------------------------------------------------
			FamilyInfor.findAllByPersonInfor(personInfor).each{
				it.delete()
			}
			
			JSON.parse(params.staffFamily).eachWithIndex{elem, i ->
				def familyInfor = new FamilyInfor(elem)
				familyInfor.personInfor = personInfor
				familyInfor.save(flush:true)
			}
			//--------------------------------------------------------------
			
			model["result"] = "true"
		}else{
			personInfor.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def userAdd ={
		redirect(action:"userShow",params:params)
	}
	def userShow ={
		/*
		 * 参数中的id号修改为个人概况的id号
		 */
		def model =[:]
		
		//当前登录用户
		def loginUser = User.get(params.userid)
		model["loginUser"]= loginUser
		
		//当前选中的部门
		model["departId"] = params.currentDepartId;
		
		//显示类型
		model["type"] = params.type
		
		//个人概况
		if(params.id){
			def personInfor = PersonInfor.get(params.id)
			//登录名
			def registerUser = personInfor.user
			if(registerUser){
				//存在账号信息
				model["user"] = personInfor.user
				
				//具有角色
				def allowrolesName=[]
				def allowrolesId =[]
				UserRole.findAllByUser(registerUser).each{
					allowrolesName << it.role.authority
					allowrolesId << it.role.id
				}
				
				model["allowrolesName"] = allowrolesName.join(',')
				model["allowrolesId"] = allowrolesId.join(",")
				model["username"] = Util.strRight(registerUser.username, "-")
			}else{
				model["user"] = new User()
			}
			model["personInfor"] = personInfor
		}else{
			model["user"] = new User()
			model["personInfor"] = new PersonInfor()
		}
		if(params.companyId){
			def company = Company.get(params.companyId)
			model["company"] = company
		}
		
		model["userType"] = "normal"
		
		FieldAcl fa = new FieldAcl()
		fa.readOnly +=["allowdepartsName","allowrolesName"]
		if(loginUser!=null){
			if(systemService.checkIsRosten(loginUser.username)){
				model["userType"] = "super"
			}else if(loginUser.sysFlag){
				model["userType"] = "admin"
			}else{
				model["userType"] = "normal"
			}
		}
		model["fieldAcl"] = fa
		render(view:'/staff/user',model:model)
	}
	
	def userGrid ={
		def departEntity = Depart.get(params.departId)
		def json=[:]
		if(params.refreshHeader){
			def _gridHeader =[]

			_gridHeader << ["name":"序号","width":"26px","colIdx":0,"field":"rowIndex"]
			_gridHeader << ["name":"登录名","width":"auto","colIdx":1,"field":"username"]
			_gridHeader << ["name":"姓名","width":"auto","colIdx":2,"field":"chinaName","formatter":"personInfor_formatTopic"]
			_gridHeader << ["name":"部门","width":"auto","colIdx":3,"field":"departName"]
			_gridHeader << ["name":"编制类别","width":"auto","colIdx":4,"field":"type"]
			_gridHeader << ["name":"性别","width":"auto","colIdx":5,"field":"sex"]
			_gridHeader << ["name":"出生年月","width":"auto","colIdx":6,"field":"birthday"]
			_gridHeader << ["name":"身份证号","width":"auto","colIdx":7,"field":"idCard"]
			_gridHeader << ["name":"手机号码","width":"auto","colIdx":8,"field":"mobile"]
			_gridHeader << ["name":"民族","width":"auto","colIdx":9,"field":"nationality"]
			_gridHeader << ["name":"政治面貌","width":"auto","colIdx":10,"field":"politicsStatus"]
			_gridHeader << ["name":"状态","width":"auto","colIdx":11,"field":"status"]

			json["gridHeader"] = _gridHeader
		}
		def totalNum = 0
		if(params.refreshData){
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)

			def offset = (nowPage-1) * perPageNum
			def max  = perPageNum

			def _json = [identifier:'id',label:'name',items:[]]
			
			//查询搜索主体为personInfor个人概况信息
			def c = PersonInfor.createCriteria()
			def pa=[max:max,offset:offset]
			def query = {
				createAlias('user', 'a')
				departs{
					eq("id",params.departId)
				}
				order("a.username", "asc")
			}
			def userList = c.list(pa,query)
			
			totalNum = userList.unique().size()
			
			def idx = 0
			userList.each{
				def _user = it.user
				def contactInfor = ContactInfor.findByPersonInfor(it)
				
				def sMap =[:]
				sMap["rowIndex"] = idx+1
				sMap["id"] = it?.id
				sMap["username"] = _user?_user.username:""
				sMap["userId"] = _user?_user.id:""
				sMap["chinaName"] = it.chinaName
				sMap["departName"] = departEntity.departName
				sMap["type"] = it?.getUserTypeName()
				sMap["sex"] = it?.sex
				sMap["birthday"] = it?.getFormatteBirthday()
				sMap["idCard"] = it?.idCard
				sMap["mobile"] = contactInfor?.mobile
				sMap["nationality"] = it?.nationality
				sMap["politicsStatus"] = it?.politicsStatus
				sMap["status"] = it?.status
				
				_json.items+=sMap
				
				idx += 1
			}

			json["gridData"] = _json
		}
		
		if(params.refreshPageControl){
			json["pageControl"] = ["total":totalNum.toString()]
		}
		render json as JSON
	}
	def staffGrid ={
		def company = Company.get(params.companyId)
		def json=[:]
		if(params.refreshHeader){
			def _gridHeader =[]

			_gridHeader << ["name":"序号","width":"26px","colIdx":0,"field":"rowIndex"]
			_gridHeader << ["name":"登录名","width":"auto","colIdx":1,"field":"username"]
			_gridHeader << ["name":"姓名","width":"auto","colIdx":2,"field":"chinaName","formatter":"personInfor_formatTopic_normal"]
			_gridHeader << ["name":"部门","width":"auto","colIdx":3,"field":"departName"]
			_gridHeader << ["name":"编制类别","width":"auto","colIdx":4,"field":"type"]
			_gridHeader << ["name":"性别","width":"auto","colIdx":5,"field":"sex"]
			_gridHeader << ["name":"出生年月","width":"auto","colIdx":6,"field":"birthday"]
			_gridHeader << ["name":"身份证号","width":"auto","colIdx":7,"field":"idCard"]
			_gridHeader << ["name":"手机号码","width":"auto","colIdx":8,"field":"mobile"]
			_gridHeader << ["name":"民族","width":"auto","colIdx":9,"field":"nationality"]
			_gridHeader << ["name":"政治面貌","width":"auto","colIdx":10,"field":"politicsStatus"]
			_gridHeader << ["name":"状态","width":"auto","colIdx":11,"field":"status"]

			json["gridHeader"] = _gridHeader
		}
		def totalNum = 0
		if(params.refreshData){
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)

			def offset = (nowPage-1) * perPageNum
			def max  = perPageNum

			def _json = [identifier:'id',label:'name',items:[]]
			
//			def personList = PersonInfor.findAllByCompany(company,[max: max, sort: "chinaName", order: "asc", offset: offset])
			
			def c = PersonInfor.createCriteria()
			def pa=[max:max,offset:offset]
			def query = {
				eq("company",company)
				
				if("staffAdd".equals(params.type)){
					not {'in'("status",["在职","退休","离职"])}
					order("chinaName", "asc")
				}else{
					'in'("status",["在职","退休","离职"])
					createAlias('user', 'a')
					order("a.username", "asc")
				}
			}
			def personList = c.list(pa,query)
			totalNum = personList.size()
			
			def idx = 0
			personList.each{
				def _user = it.user
				def personInfor = it
				def contactInfor = ContactInfor.findByPersonInfor(personInfor)
				
				def sMap =[:]
				sMap["rowIndex"] = idx+1
				sMap["id"] = it.id
				sMap["username"] = _user?_user.username:""
				sMap["userId"] = _user?_user.id:""
				sMap["chinaName"] = personInfor?.chinaName
				sMap["departName"] = personInfor?.getUserDepartName()
				sMap["type"] = personInfor?.getUserTypeName()
				sMap["sex"] = personInfor?.sex
				sMap["birthday"] = personInfor?.getFormatteBirthday()
				sMap["idCard"] = personInfor?.idCard
				sMap["mobile"] = contactInfor?.mobile
				sMap["nationality"] = personInfor?.nationality
				sMap["politicsStatus"] = personInfor?.politicsStatus
				sMap["status"] = personInfor?.status
				
				_json.items+=sMap
				
				idx += 1
			}

			json["gridData"] = _json
		}
		
		if(params.refreshPageControl){
			json["pageControl"] = ["total":totalNum.toString()]
		}
		render json as JSON
	}
	def personInforView ={
		def model =[:]
		model["departId"] = params.id
		render(view:'/staff/personManageView',model:model)
	}
	
	def depart ={
		def model =[:]
		model["company"] = Company.get(params.companyId)
		render(view:'/staff/personManage',model:model)
	}
	
	def getPersonInfor ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company = currentUser.company
		
		//部门信息
		def depart
		def personInfor = PersonInfor.get(params.id)
		if(!personInfor){
			personInfor = new PersonInfor()
			if(params.departId){
				depart = Depart.get(params.departId)
			}
		}else{
			depart = personInfor.departs[0]
		}
		
		if(depart){
			model["departName"] = depart.departName
			model["departId"] = depart.id
		}
		
		model["company"] = company
		model["personInforEntity"] = personInfor
		
		//用户类型
		def userTypeList = UserType.findAllByCompany(company)
		model["userTypeList"] = userTypeList
		
		if(userTypeList && userTypeList.size()>0){
			model["userTypeEntity"] = userTypeList[0]
		}
		
		//血型
		model["bloodList"] = shareService.getSystemCodeItems(company,"rs_blood")
		
		//健康状况
		model["healthList"] = shareService.getSystemCodeItems(company,"rs_health")
		
		//政治面貌
		model["politicsStatusList"] = shareService.getSystemCodeItems(company,"rs_politicsStatus")
		
		//专业技术等级
		model["techGradeList"] = shareService.getSystemCodeItems(company,"rs_techGrade")
		
		FieldAcl fa = new FieldAcl()
//		if( user && "normal".equals(user.getUserType()) && !currentUser.equals(user)){
//			//非管理员并且不是本人时，不允许修改所有相关字段
//			fa.readOnly += ["chinaName"]
//		}
		model["fieldAcl"] = fa
		
		render(view:'/staff/personInfor',model:model)
	}
	def getContactInfor ={
		def model =[:]
		def entity
		
		def user = PersonInfor.get(params.id)
		if(user){
			entity = ContactInfor.findByPersonInfor(user)
		}else{
			entity = new ContactInfor()
		}
		
		model["contactInforEntity"] = entity
		FieldAcl fa = new FieldAcl()
		
		model["fieldAcl"] = fa
		render(view:'/staff/contactInfor',model:model)
	}
	def getDegree ={
		def model =[:]
		def entity
		
		def user = PersonInfor.get(params.id)
		if(user){
			entity = Degree.findByPersonInfor(user)
		}else{
			entity = new Degree()
		}
		model["degreeEntity"] = entity
		FieldAcl fa = new FieldAcl()
	
		model["fieldAcl"] = fa
		render(view:'/staff/degree',model:model)
	}
    def getWorkResume={
		def model =[:]
		def entity
		
		def user = PersonInfor.get(params.id)
		if(user){
			entity = WorkResume.findByPersonInfor(user)
		}else{
			entity = new WorkResume()
		}
		model["workResumeEntity"] = entity
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/staff/workResume',model:model)
	}
	
	def getFamily ={
		def json=[:]
		
		def personInfor = PersonInfor.get(params.id)
		if(params.refreshHeader){
			json["gridHeader"] = staffService.getFamilyInforListLayout()
		}
		
		//搜索功能
		def searchArgs =[:]
		
		if(params.refreshData){
			if(!personInfor){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["personInfor"] = personInfor
				
				def gridData = staffService.getFamilyInforListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		if(params.refreshPageControl){
			if(!personInfor){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = staffService.getFamilyInforCount(personInfor,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
			
		}
		render json as JSON
	}
	
	def familyInforShow ={
		def model =[:]
		if(params.id){
			model["familyInfor"] = FamilyInfor.get(params.id)
		}else{
			model["familyInfor"] = new FamilyInfor()
		}
		render(view:'/staff/familyInfor',model:model)
	}
	
	def exportPerson={
		OutputStream os = response.outputStream
		
		response.setContentType('application/vnd.ms-excel')
		response.setHeader("Content-disposition", "attachment; filename=" + new String("张三.xls".getBytes("GB2312"), "ISO_8859_1"))
		
		def excel = new ExcelExport()
		excel.mbxz(os)
	}
	
	def printPerson={
		def word = new WordExport()
		word.downloadZip(response)
	}
	
}
