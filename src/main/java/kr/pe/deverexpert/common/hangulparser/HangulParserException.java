package kr.pe.deverexpert.common.hangulparser;

import java.io.Serializable;

public class HangulParserException extends Exception implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3980846042650290725L;

	public HangulParserException() {
		super();
	}

	public HangulParserException(String message) {
		super(message);
	}

}
