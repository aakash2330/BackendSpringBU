package com.BUG.Entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponseEntity {
	private	List<UserEntity> userEntityList;
	private int page_number;
	private int page_size;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	private boolean firstPage;
}	
