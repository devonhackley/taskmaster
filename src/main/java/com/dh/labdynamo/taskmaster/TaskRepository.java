package com.dh.labdynamo.taskmaster;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EnableScan
public interface TaskRepository extends CrudRepository<Task, UUID> {
    Optional<Task> findById(UUID id);
    List<Task> findAllByAssignee(String name);
}
