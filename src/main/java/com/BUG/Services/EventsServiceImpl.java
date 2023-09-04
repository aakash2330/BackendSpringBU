package com.BUG.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.BUG.Entities.EventsEntity;
import com.BUG.Repositories.EventsRepo;
import com.BUG.Repositories.UserRepo;

@Component
public class EventsServiceImpl implements EventsService {
	
	
	@Autowired
	private EventsRepo eventsRepo;

	@Override
	public List<EventsEntity> getAllEvents(int pageNum , int pageSize) {
		Pageable page = PageRequest.of(pageNum, pageSize);
		Page<EventsEntity> eventEntityPage = eventsRepo.findAll(page);
		List<EventsEntity> eventEntityList = eventEntityPage.getContent();
		return eventEntityList;
	}

	@Override
	public String addNewEvent(EventsEntity e) {
		eventsRepo.save(e);
		return "EVENT ADDED";
	}

}
