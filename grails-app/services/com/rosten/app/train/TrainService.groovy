package com.rosten.app.train

import com.rosten.app.util.GridUtil

class TrainService {

	def getTrainCourseListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new TrainCourse())
	}
	def getTrainCourseListDataStore ={params->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllTrainCourse(offset,max,params.company)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	def getAllTrainCourse ={offset,max,company->
		def c = TrainCourse.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
			order("createDate", "desc")
		}
		return c.list(pa,query)
	}
	def getTrainCourseCount ={company->
		def c = TrainCourse.createCriteria()
		def query = { eq("company",company) }
		return c.count(query)
	}
	def getTrainMessageListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new TrainMessage())
	}
	
	def getTrainMessageDataStore ={params->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllTrainMessage(offset,max,params.company)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	
	def getAllTrainMessage ={offset,max,company->
		def c = TrainMessage.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
		}
		return c.list(pa,query)
	}
	
	def getTrainMessageCount ={company->
		def c = TrainMessage.createCriteria()
		def query = { eq("company",company) }
		return c.count(query)
	}
	
	
	def getDegreeStudyListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new DegreeStudy())
	}
	
	def getDegreeStudyDataStore ={params->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllDegreeStudy(offset,max,params.company)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	
	def getAllDegreeStudy ={offset,max,company->
		def c = DegreeStudy.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
		}
		return c.list(pa,query)
	}
	
	def getDegreeStudyCount ={company->
		def c = DegreeStudy.createCriteria()
		def query = { eq("company",company) }
		return c.count(query)
	}
	
}
