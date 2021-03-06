package com.rosten.app.staff

import java.text.SimpleDateFormat
import java.util.Date;

import com.rosten.app.system.User
import com.rosten.app.util.SystemUtil

class StaffLog {
	String id
	
	//处理类型：部门调动,退休，离职等
	String type
	
	String reson
	
	//原部门
	String oldDepart
	
	//现在部门
	String nowDepart
	
	User dealUser
	
	//实际处理时间
	Date dealDate
	
	//创建时间
	Date createDate = new Date()
	
	def getFormattedCreatedDate(){
		if(createDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm")
			return sd.format(createDate)
		}else{
			return ""
		}
	}
	
	static belongsTo = [personInfor:PersonInfor]
	
    static constraints = {
		reson nullable:true,blank:true
		oldDepart nullable:true,blank:true
		nowDepart nullable:true,blank:true
		dealDate nullable:true,blank:true
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "ROSTEN_STAFF_LOG"
		
		//兼容mysql与oracle
		def systemUtil = new SystemUtil()
		if(systemUtil.getDatabaseType().equals("oracle")){
			reson sqlType:"clob"
		}else{
			reson sqlType:"text"
		}
	}
}
