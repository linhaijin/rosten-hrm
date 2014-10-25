package com.rosten.app.train

import grails.converters.JSON

import com.rosten.app.util.FieldAcl
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User

class TrainController {
	def trainService
	def springSecurityService
	
	def  staffItemShow ={
		def model =[:]
		if(params.id){
			model["trainMessage"] = TrainMessage.get(params.id)
		}else{
			model["trainMessage"] = new TrainMessage()
		}
		render(view:'/train/staffItemShow',model:model)
	}
	def staffListGrid ={
		def json=[:]
		
		def trainCourse = TrainCourse.get(params.id)
		if(params.refreshHeader){
			json["gridHeader"] = trainService.getStaffListLayout()
		}
		
		//2014-9-1 增加搜索功能
		def searchArgs =[:]
		
		if(params.refreshData){
			if(!trainCourse){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["trainCourse"] = trainCourse
				
				def gridData = trainService.getStaffItemListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		if(params.refreshPageControl){
			if(!trainCourse){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = trainService.getStaffItemCount(trainCourse,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
			
		}
		render json as JSON
	}
	
	//培训班查询条件
	def trainCourseSearchView ={
		def model =[:]
		render(view:'/train/trainCourseSearch',model:model)
	}
	
	//出国进修查询条件
	def forgeinStudySearchView ={
		def model =[:]
		render(view:'/train/forgeinStudySearch',model:model)
	}
	
	def trainCourseDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def trainCourse = TrainCourse.get(it)
				if(trainCourse){
					trainCourse.delete(flush: true)
				}
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	def trainCourseSave ={
		def json=[:]
		def trainCourse = new TrainCourse()
		if(params.id && !"".equals(params.id)){
			trainCourse = TrainCourse.get(params.id)
		}else{
			if(params.companyId){
				trainCourse.company = Company.get(params.companyId)
			}
		}
		trainCourse.properties = params
		trainCourse.clearErrors()
		
		if(trainCourse.save(flush:true)){
			json["result"] = "true"
		}else{
			trainCourse.errors.each{
				println it
			}
			json["result"] = "false"
		}
		render json as JSON
	}
	def trainCourseAdd ={
		redirect(action:"trainCourseShow",params:params)
	}
	def trainCourseShow ={
		def model =[:]
		
		def user = User.get(params.userid)
		def company = Company.get(params.companyId)
		def trainCourse = new TrainCourse()
		if(params.id){
			trainCourse = TrainCourse.get(params.id)
		}
		model["user"]=user
		model["company"] = company
		model["trainCourse"] = trainCourse
		
		FieldAcl fa = new FieldAcl()
		if("normal".equals(user.getUserType())){
			//普通用户
//			fa.readOnly += ["description"]
		}
		model["fieldAcl"] = fa
		
		render(view:'/train/trainCourse',model:model)
	}
	def trainCourseGrid ={
		def json=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			json["gridHeader"] = trainService.getTrainCourseListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		
		if(params.courseName && !"".equals(params.courseName)) searchArgs["courseName"] = params.courseName
		
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			json["gridData"] = trainService.getTrainCourseListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = trainService.getTrainCourseCount(company,searchArgs)
			json["pageControl"] = ["total":total.toString()]
		}
		render json as JSON
	}
	
	/**
	 * 员工培训信息
	 */
	def trainMessageGrid ={
		def json=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			json["gridHeader"] = trainService.getTrainMessageListLayout()
		}
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			json["gridData"] = trainService.getTrainMessageDataStore(args)
			
		}
		if(params.refreshPageControl){
			def total = trainService.getTrainMessageCount(company)
			json["pageControl"] = ["total":total.toString()]
		}
		render json as JSON
		
	}
	
	/**
	 * 培训信息增加
	 */
	def trainMessageAdd ={
		redirect(action:"trainMessageShow",params:params)
	}
	
	def trainMessageShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		
		def user = User.get(params.userid)
		def company = Company.get(params.companyId)
		def trainMessage = new TrainMessage()
		if(params.id){
			trainMessage = TrainMessage.get(params.id)
		}else{
			trainMessage.user = currentUser
		}
		
		model["company"] = company
		model["trainMessage"] = trainMessage
		
		FieldAcl fa = new FieldAcl()
		if("normal".equals(user.getUserType())){
			//普通用户
//			fa.readOnly += ["description"]
		}
//		fa.readOnly += ["trainAddress"]
		model["fieldAcl"] = fa
		
		render(view:'/train/trainMessage',model:model)
	}
	
	/**
	 * 培训班选择
	 */
	def courseSelect ={
		def courseList =[]
		def company = Company.get(params.companyId)
		TrainCourse.findAllByCompany(company).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.courseName
			courseList << json
		}
		render courseList as JSON
	}
	
	/**
	 * 培训信息保存
	 */
	def trainMessageSave ={
		def json=[:]
		def trainMessage = new TrainMessage()
		if(params.id && !"".equals(params.id)){
			trainMessage = TrainMessage.get(params.id)
		}else{
			if(params.companyId){
				trainMessage.company = Company.get(params.companyId)
			}
		}
		trainMessage.properties = params
		trainMessage.clearErrors()
		
		trainMessage.trainCourse = TrainCourse.get(params.courseId)
		trainMessage.user = User.get(params.userId)
		
		if(trainMessage.save(flush:true)){
			json["result"] = "true"
		}else{
			trainMessage.errors.each{
				println it
			}
			json["result"] = "false"
		}
		render json as JSON
	}
	
	/**
	 * 删除
	 */
	def trainMessageDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def trainMessage = TrainMessage.get(it)
				if(trainMessage){
					trainMessage.delete(flush: true)
				}
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	
	/**
	 * 学历学位进修信息
	 */
	def degreeStudyGrid ={
		def json=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			json["gridHeader"] = trainService.getDegreeStudyListLayout()
		}
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			json["gridData"] = trainService.getDegreeStudyDataStore(args)
			
		}
		if(params.refreshPageControl){
			def total = trainService.getDegreeStudyCount(company)
			json["pageControl"] = ["total":total.toString()]
		}
		render json as JSON
		
	}
	
	/**
	 * 增加
	 */
	def degreeStudyAdd ={
		redirect(action:"degreeStudyShow",params:params)
	}
	
	def degreeStudyShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def user = User.get(params.userid)
		def company = Company.get(params.companyId)
		def degreeStudy = new DegreeStudy()
		if(params.id){
			degreeStudy = DegreeStudy.get(params.id)
		}else{
			degreeStudy.user = currentUser
		}
		
		model["company"] = company
		model["degreeStudy"] = degreeStudy
		
		FieldAcl fa = new FieldAcl()
		if("normal".equals(user.getUserType())){

		}
		model["fieldAcl"] = fa
		
		render(view:'/train/degreeStudy',model:model)
	}
	
	/**
	 * 保存
	 */
	def degreeStudySave ={
		def json=[:]
		def degreeStudy = new DegreeStudy()
		def currentUser = springSecurityService.getCurrentUser()
		if(params.id && !"".equals(params.id)){
			degreeStudy = DegreeStudy.get(params.id)
		}else{
			if(params.companyId){
				degreeStudy.company = Company.get(params.companyId)
			}
			degreeStudy.user = currentUser
			
			degreeStudy.startDate = Util.convertToTimestamp(params.startDate)
			degreeStudy.endDate = Util.convertToTimestamp(params.endDate)
		}
		degreeStudy.properties = params
		degreeStudy.clearErrors()
		
		if(degreeStudy.save(flush:true)){
			json["result"] = "true"
		}else{
			degreeStudy.errors.each{
				println it
			}
			json["result"] = "false"
		}
		render json as JSON
	}
	
	/**
	 * 删除
	 */
	def degreeStudyDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def degreeStudy = DegreeStudy.get(it)
				if(degreeStudy){
					degreeStudy.delete(flush: true)
				}
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	
	
	/**
	 * 出国进修信息
	 */
	def forgeinStudyGrid ={
		def json=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			json["gridHeader"] = trainService.getForgeinStudyListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		
		if(params.appYear && !"".equals(params.appYear)) searchArgs["appYear"] = params.appYear
		
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			json["gridData"] = trainService.getForgeinStudyDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = trainService.getForgeinStudyCount(company,searchArgs)
			json["pageControl"] = ["total":total.toString()]
		}
		render json as JSON
		
	}
	
	/**
	 * 增加
	 */
	def forgeinStudyAdd ={
		redirect(action:"forgeinStudyShow",params:params)
	}
	
	def forgeinStudyShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def user = User.get(params.userid)
		def company = Company.get(params.companyId)
		def forgeinStudy = new ForgeinStudy()
		if(params.id){
			forgeinStudy = ForgeinStudy.get(params.id)
		}else{
			forgeinStudy.user = currentUser
		}
		
		model["company"] = company
		model["forgeinStudy"] = forgeinStudy
		
		FieldAcl fa = new FieldAcl()
		if("normal".equals(user.getUserType())){

		}
		model["fieldAcl"] = fa
		
		render(view:'/train/forgeinStudy',model:model)
	}
	
	/**
	 * 保存
	 */
	def forgeinStudySave ={
		def json=[:]
		def forgeinStudy = new ForgeinStudy()
		def currentUser = springSecurityService.getCurrentUser()
		if(params.id && !"".equals(params.id)){
			forgeinStudy = ForgeinStudy.get(params.id)
		}else{
			if(params.companyId){
				forgeinStudy.company = Company.get(params.companyId)
			}
			forgeinStudy.user = currentUser
			
			forgeinStudy.abroadDate = Util.convertToTimestamp(params.abroadDate)
			forgeinStudy.returneDate = Util.convertToTimestamp(params.returneDate)
		}
		forgeinStudy.properties = params
		forgeinStudy.clearErrors()
		
		if(forgeinStudy.save(flush:true)){
			json["result"] = "true"
		}else{
			forgeinStudy.errors.each{
				println it
			}
			json["result"] = "false"
		}
		render json as JSON
	}
	
	/**
	 * 删除
	 */
	def forgeinStudyDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def forgeinStudy = ForgeinStudy.get(it)
				if(forgeinStudy){
					forgeinStudy.delete(flush: true)
				}
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
    
}
