package com.infatuation.logsplorer.repository;

import com.infatuation.logsplorer.entity.Log;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Custom repository class for log searching. We are taking advantage of CriteriaBuilder
	 * and Predicate to do conditional query.
	 * Query param search is case insensitive and optional.
	 * If no search param is given then we return the entire log collection.
	 * @param code - Query for Status Code. eg, 200, 500
	 * @param method - Query for HTTP Method. eg, Get, GET, PosT
	 * @param user - Query for Auth User
	 * @return - Return subset of log collection based on query param given, sorted by Date field in descending order. Return all collection if no query param given. 
	 */
	public List<String> searchLogsWithCriteria(String code, String method, String user){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> query = builder.createQuery(String.class);
		Root<Log> log = query.from(Log.class);

		List<Predicate> predicateList = new ArrayList<>();

		if(!StringUtils.isEmpty(code)){
			Predicate codePredicate = builder.equal(
					builder.lower(log.get("status")), Integer.parseInt(code.toLowerCase()));
			predicateList.add(codePredicate);
		}
		if(!StringUtils.isEmpty(method)){
			Predicate methodPredicate = builder.equal(
					builder.lower(log.get("method")), method.toLowerCase());
			predicateList.add(methodPredicate);
		}
		if(!StringUtils.isEmpty(user)){
			Predicate userPredicate = builder.equal(
					builder.lower(log.get("authUser")), user.toLowerCase());
			predicateList.add(userPredicate);
		}

		Predicate[] pArr = predicateList.toArray(new Predicate[predicateList.size()]);
		query.select(log.<String>get("raw"))
				.where(builder.and(pArr))
				.orderBy(builder.desc(log.get("date")));

		return entityManager.createQuery(query).getResultList();
	}
}
