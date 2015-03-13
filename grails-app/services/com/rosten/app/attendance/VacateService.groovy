package com.rosten.app.attendance

import com.rosten.app.util.GridUtil

class VacateService {
	
	
	//2015-3-13----------------增加出勤解释单-----------------------------------------------------
	def getVacateExplainListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new VacateExplain())
	}
	
	def getVacateExplainDataStore ={params->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllVacateExplain(offset,max,params.company,params.user)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	
	def getAllVacateExplain ={offset,max,company,user->
		def c = VacateExplain.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
			
			order("createDate", "desc")
		}
		return c.list(pa,query)
	}
	
	def getVacateExplainCount ={company,user->
		def c = VacateExplain.createCriteria()
		def query = {
			 eq("company",company)
		}
		return c.count(query)
	}
	//-------------------------------------------------------------------------------
	
	def getVacateListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new Vacate())
	}
	
	def getVacateDataStore ={params->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllVacate(offset,max,params.company,params.user)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	
	def getAllVacate ={offset,max,company,user->
		def c = Vacate.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
			
//			if(!user.getAllRolesValue().contains("系统管理员") && !"admin".equals(user.getUserType())){
				readers{
					eq("id",user.id)
				}
//			}
			order("createDate", "desc")
		}
		return c.list(pa,query)
	}
	
	def getVacateCount ={company,user->
		def c = Vacate.createCriteria()
		def query = {
			 eq("company",company)
//			 if(!user.getAllRolesValue().contains("系统管理员") && !"admin".equals(user.getUserType())){
				 readers{
					 eq("id",user.id)
				 }
//			 }
		}
		return c.count(query)
	}
	
	
	def getAllAskForDataStore ={params->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllAskFor(offset,max,params.company,params.user)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	
	def getAllVacateCount ={company->
		def c = Vacate.createCriteria()
		def query = {
			 eq("company",company)
			 }
		return c.count(query)
	}
	
	def getAllAskFor ={offset,max,company,user->
		def c = Vacate.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
			order("createDate", "desc")
		}
		return c.list(pa,query)
	}
	
}
