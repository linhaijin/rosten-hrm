package com.rosten.app.salary
import grails.converters.JSON
import com.rosten.app.system.Company
import com.rosten.app.util.Util
import com.rosten.app.staff.PersonInfor

class SalaryController {

 def salaryService
	
 def quartersGrid ={
	 def json=[:]
	 def company = Company.get(params.companyId)
	 if(params.refreshHeader){
		 json["gridHeader"] =salaryService.getQuartersListLayout()
	 }
	 //增加查询条件
	 def searchArgs =[:]
	if(params.quaName && !"".equals(params.quaName)) searchArgs["quaName"] = params.quaName
	 if(params.refreshData){
		 def args =[:]
		 int perPageNum = Util.str2int(params.perPageNum)
		 int nowPage =  Util.str2int(params.showPageNum)
		 
		 args["offset"] = (nowPage-1) * perPageNum
		 args["max"] = perPageNum
		 args["company"] = company
		 json["gridData"] = salaryService.getQuartersListDataStore(args,searchArgs)
		 
	 }
	 if(params.refreshPageControl){
		 def total = salaryService.getQuarters(company,searchArgs)
		 json["pageControl"] = ["total":total.toString()]
	 }
	 render json as JSON
	}
 
 def quartersSearchView ={
	 def model =[:]
	 render(view:'/salary/quartersSearch',model:model)
 }
 
 /**
  * 增加
  */
 def quartersAdd ={
	 redirect(action:"quartersShow",params:params)
 }
 
 def quartersShow ={
	 def model =[:]
	 
	 def company = Company.get(params.companyId)
	 def quarters = new Quarters()
	 if(params.id){
		 quarters = Quarters.get(params.id)
	 }
	 
	 model["company"] = company
	 model["quarters"] = quarters
	 
	 render(view:'/salary/quarters',model:model)
 }
 
 def quartersSave ={
	 def json=[:]
	 def quarters = new Quarters()
	 if(params.id && !"".equals(params.id)){
		 quarters = Quarters.get(params.id)
	 }else{
		 if(params.companyId){
			 quarters.company = Company.get(params.companyId)
		 }
	 }
	 quarters.properties = params
	 quarters.clearErrors()
	 
	 if(quarters.save(flush:true)){
		 json["result"] = "true"
	 }else{
		 quarters.errors.each{
			 println it
		 }
		 json["result"] = "false"
	 }
	 render json as JSON
 }
 
 //删除
 def quartersDelete={
	 def ids = params.id.split(",")
	 def json
	 try{
		 ids.each{
			 def quarters = Quarters.get(it)
			 if(quarters){
				 quarters.delete(flush: true)
			 }
		 }
		 json = [result:'true']
	 }catch(Exception e){
		 json = [result:'error']
	 }
	 render json as JSON
 }
 
 
 def gearGrid ={
	 def json=[:]
	 def company = Company.get(params.companyId)
	 if(params.refreshHeader){
		 json["gridHeader"] =salaryService.getGearListLayout()
	 }
	 //增加查询条件
	 def searchArgs =[:]
	if(params.gearName && !"".equals(params.gearName)) searchArgs["gearName"] = params.gearName
	 if(params.refreshData){
		 def args =[:]
		 int perPageNum = Util.str2int(params.perPageNum)
		 int nowPage =  Util.str2int(params.showPageNum)
		 
		 args["offset"] = (nowPage-1) * perPageNum
		 args["max"] = perPageNum
		 args["company"] = company
		 json["gridData"] = salaryService.getGearListDataStore(args,searchArgs)
		 
	 }
	 if(params.refreshPageControl){
		 def total = salaryService.getGear(company,searchArgs)
		 json["pageControl"] = ["total":total.toString()]
	 }
	 render json as JSON
	}
 
 def gearSearchView ={
	 def model =[:]
	 render(view:'/salary/gearSearch',model:model)
 }
 
 /**
  * 增加
  */
 def gearAdd ={
	 redirect(action:"gearShow",params:params)
 }
 
 def gearShow ={
	 def model =[:]
	 
	 def company = Company.get(params.companyId)
	 def gear = new Gear()
	 if(params.id){
		 gear = Gear.get(params.id)
	 }
	 
	 model["company"] = company
	 model["gear"] = gear
	 
	 render(view:'/salary/gear',model:model)
 }
 
 def gearSave ={
	 def json=[:]
	 def gear = new Gear()
	 if(params.id && !"".equals(params.id)){
		 gear = Gear.get(params.id)
	 }else{
		 if(params.companyId){
			 gear.company = Company.get(params.companyId)
		 }
	 }
	 gear.properties = params
	 gear.clearErrors()
	 
	 if(gear.save(flush:true)){
		 json["result"] = "true"
	 }else{
		 gear.errors.each{
			 println it
		 }
		 json["result"] = "false"
	 }
	 render json as JSON
 }
 
 //删除
 def gearDelete={
	 def ids = params.id.split(",")
	 def json
	 try{
		 ids.each{
			 def gear =Gear.get(it)
			 if(gear){
				 gear.delete(flush: true)
			 }
		 }
		 json = [result:'true']
	 }catch(Exception e){
		 json = [result:'error']
	 }
	 render json as JSON
 }
 
 def radixGrid ={
	 def json=[:]
	 def company = Company.get(params.companyId)
	 if(params.refreshHeader){
		 json["gridHeader"] =salaryService.getRadixListLayout()
	 }
	 //增加查询条件
	 def searchArgs =[:]
	if(params.category && !"".equals(params.category)) searchArgs["category"] = params.category
	 if(params.refreshData){
		 def args =[:]
		 int perPageNum = Util.str2int(params.perPageNum)
		 int nowPage =  Util.str2int(params.showPageNum)
		 
		 args["offset"] = (nowPage-1) * perPageNum
		 args["max"] = perPageNum
		 args["company"] = company
		 json["gridData"] = salaryService.getRadixListDataStore(args,searchArgs)
		 
	 }
	 if(params.refreshPageControl){
		 def total = salaryService.getRadix(company,searchArgs)
		 json["pageControl"] = ["total":total.toString()]
	 }
	 render json as JSON
	}
 
 def radixSearchView ={
	 def model =[:]
	 render(view:'/salary/radixSearch',model:model)
 }
 
 /**
  * 增加
  */
 def radixAdd ={
	 redirect(action:"radixShow",params:params)
 }
 
 def radixShow ={
	 def model =[:]
	 
	 def company = Company.get(params.companyId)
	 def radix = new Radix()
	 if(params.id){
		 radix = Radix.get(params.id)
	 }
	 
	 model["company"] = company
	 model["radix"] = radix
	 
	 render(view:'/salary/radix',model:model)
 }
 
 def radixSave ={
	 def json=[:]
	 def radix = new Radix()
	 if(params.id && !"".equals(params.id)){
		 radix = Radix.get(params.id)
	 }else{
		 if(params.companyId){
			 radix.company = Company.get(params.companyId)
		 }
	 }
	 radix.properties = params
	 radix.clearErrors()
	 
	 if(radix.save(flush:true)){
		 json["result"] = "true"
	 }else{
		 radix.errors.each{
			 println it
		 }
		 json["result"] = "false"
	 }
	 render json as JSON
 }
 
 //删除
 def radixDelete={
	 def ids = params.id.split(",")
	 def json
	 try{
		 ids.each{
			 def radix =Radix.get(it)
			 if(radix){
				 radix.delete(flush: true)
			 }
		 }
		 json = [result:'true']
	 }catch(Exception e){
		 json = [result:'error']
	 }
	 render json as JSON
 }
 
 def riskGoldGrid ={
	 def json=[:]
	 def company = Company.get(params.companyId)
	 if(params.refreshHeader){
		 json["gridHeader"] =salaryService.getRiskGoldListLayout()
	 }
	 //增加查询条件
	 def searchArgs =[:]
	if(params.chinaName && !"".equals(params.chinaName)) searchArgs["chinaName"] = params.chinaName
	 if(params.refreshData){
		 def args =[:]
		 int perPageNum = Util.str2int(params.perPageNum)
		 int nowPage =  Util.str2int(params.showPageNum)
		 
		 args["offset"] = (nowPage-1) * perPageNum
		 args["max"] = perPageNum
		 args["company"] = company
		 json["gridData"] = salaryService.getRiskGoldListDataStore(args,searchArgs)
		 
	 }
	 if(params.refreshPageControl){
		 def total = salaryService.getRiskGoldCount(company,searchArgs)
		 json["pageControl"] = ["total":total.toString()]
	 }
	 render json as JSON
	}
 
 def riskGoldSearchView ={
	 def model =[:]
	 render(view:'/salary/riskGoldSearch',model:model)
 }
 
 /**
  * 增加
  */
 def riskGoldAdd ={
	 redirect(action:"riskGoldShow",params:params)
 }
 
 def riskGoldShow ={
	 def model =[:]
	 
	 def company = Company.get(params.companyId)
	 def riskGold = new RiskGold()
	 if(params.id){
		 riskGold = RiskGold.get(params.id)
	 }
	 
	 model["company"] = company
	 model["riskGold"] = riskGold
	 
	 render(view:'/salary/riskGold',model:model)
 }
 
 def riskGoldSave ={
	 def json=[:]
	 def riskGold = new RiskGold()
	 if(params.id && !"".equals(params.id)){
		 riskGold = RiskGold.get(params.id)
	 }else{
		 if(params.companyId){
			 riskGold.company = Company.get(params.companyId)
		 }
	 }
	 riskGold.properties = params
	 riskGold.clearErrors()
	 
	 riskGold.gjjBl = Util.str2int(params.gjjBl)
	 riskGold.sybxBl = Util.str2int(params.sybxBl)
	 riskGold.ylbxBl = Util.str2int(params.ylbxBl)
	 riskGold.syubxBl = Util.str2int(params.syubxBl)
	 riskGold.ylaobxBl = Util.str2int(params.ylaobxBl)
	 
	 riskGold.personInfor = PersonInfor.get(params.personInforId)
	 
	 if(riskGold.save(flush:true)){
		 json["result"] = "true"
	 }else{
		 riskGold.errors.each{
			 println it
		 }
		 json["result"] = "false"
	 }
	 render json as JSON
 }
 
 //删除
 def riskGoldDelete={
	 def ids = params.id.split(",")
	 def json
	 try{
		 ids.each{
			 def radix =Radix.get(it)
			 if(radix){
				 radix.delete(flush: true)
			 }
		 }
		 json = [result:'true']
	 }catch(Exception e){
		 json = [result:'error']
	 }
	 render json as JSON
 }
 
}
