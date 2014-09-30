package com.rosten.app.system

import com.rosten.app.util.GridUtil

class SystemExtendService {
	
	def getSystemCodeItemListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new SystemCodeItem())
	}
	def getSystemCodeItemListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllSystemCodeItem(offset,max,params.systemCode,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllSystemCodeItem={offset,max,systemCode,searchArgs->
		def c = SystemCode.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			if(systemCode){
				eq("systemCode",systemCode)
			}
			order("code", "desc")
			
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.list(pa,query)
	}
	def getSystemCodeItemCount ={systemCode,searchArgs->
		def c = SystemCodeItem.createCriteria()
		def query = {
			if(systemCode){
				eq("systemCode",systemCode)
			}
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	
    def getSystemCodeListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new SystemCode())
	}
	def getSystemCodeListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllSystemCode(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllSystemCode={offset,max,company,searchArgs->
		def c = SystemCode.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
			order("code", "desc")
			
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.list(pa,query)
	}
	def getSystemCodeCount ={company,searchArgs->
		def c = SystemCode.createCriteria()
		def query = {
			eq("company",company)
			
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
}
