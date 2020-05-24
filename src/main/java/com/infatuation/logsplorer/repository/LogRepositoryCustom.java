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

	public List<String> searchLogsWithCriteria(String code, String method, String user){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> query = builder.createQuery(String.class);
		Root<Log> log = query.from(Log.class);

		List<Predicate> predicateList = new ArrayList<>();

		if(!StringUtils.isEmpty(code)){
			Predicate codePredicate = builder.equal(log.get("status"), code);
			predicateList.add(codePredicate);
		}
		if(!StringUtils.isEmpty(method)){
			Predicate methodPredicate = builder.equal(log.get("method"), method);
			predicateList.add(methodPredicate);
		}
		if(!StringUtils.isEmpty(user)){
			Predicate userPredicate = builder.equal(log.get("authUser"), user);
			predicateList.add(userPredicate);
		}

		Predicate[] pArr = predicateList.toArray(new Predicate[predicateList.size()]);
		query.select(log.<String>get("raw"))
				.where(builder.and(pArr));
		System.out.println(String.format("============== code:%s , method:%s , user:%s", code, method, user));
		return entityManager.createQuery(query).getResultList();
	}
}
