package edu.jsp.btm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NotAnAuthrouzedUserException extends RuntimeException {
	private String message;
}
