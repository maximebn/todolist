package com.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	public static final String UNRECOGNIZEDUSER = "Id de l'utilisateur non reconnu";
	public static final String UNRECOGNIZEDPROJECT = "Id du projet non reconnu";
	public static final String UNRECOGNIZEDTASK = "Id de la tâche non reconnu";
	
	private static final long serialVersionUID = -1749672979746392283L;
	
	public NotFoundException() {
	}

	public NotFoundException(String msg) {
		super(msg);
	}
}
