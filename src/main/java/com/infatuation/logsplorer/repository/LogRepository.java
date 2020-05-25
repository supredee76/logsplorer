package com.infatuation.logsplorer.repository;

import com.infatuation.logsplorer.entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {
    /**
     * This is Spring Data JPA interface for accessing data stored in Relational database.
     * It provide basic CRUD operation.
     */
}
