package com.momentum.batch.server.manager.service.common;

import org.springframework.data.domain.Page;

import java.util.List;

public class RestPreconditions {

	private RestPreconditions() {
		// Intentionally left empty
	}

	public static <T> T checkFound(T resource) throws ResourceNotFoundException {
		if (resource == null) {
			throw new ResourceNotFoundException();
		}
		return resource;
	}

	public static <T> List<T> checkListFound(List<T> resource) throws ResourceNotFoundException {
		if (resource == null || resource.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return resource;
	}

	public static <T> Page<T> checkPageFound(Page<T> resource) throws ResourceNotFoundException {
		if (resource == null || resource.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return resource;
	}
}
