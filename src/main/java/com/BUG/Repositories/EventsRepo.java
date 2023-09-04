package com.BUG.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BUG.Entities.EventsEntity;

public interface EventsRepo extends JpaRepository<EventsEntity, Integer>{
		
}
