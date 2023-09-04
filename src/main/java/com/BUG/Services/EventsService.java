package com.BUG.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BUG.Entities.EventsEntity;

@Service
public interface EventsService {

	public List<EventsEntity> getAllEvents(int pageNum, int pageSize);
	public String addNewEvent(EventsEntity e);
}
