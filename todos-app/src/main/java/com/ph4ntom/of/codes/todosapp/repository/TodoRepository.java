package com.ph4ntom.of.codes.todosapp.repository;

import com.ph4ntom.of.codes.todosapp.model.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TodoRepository extends JpaRepository<Todo, Integer> {

  Page<Todo> findByTitleContainingIgnoreCase(final String keyword, final Pageable pageable);

  @Query("UPDATE Todo t SET t.done = :done WHERE t.id = :id")
  @Modifying
  void updateDoneStatus(final Integer id, final boolean done);
}
