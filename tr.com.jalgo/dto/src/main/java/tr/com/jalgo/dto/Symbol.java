package tr.com.jalgo.dto;

import java.io.Serializable;

import lombok.Data;


@Data
public class Symbol implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String baseSymbol;
	private String counterSymbol;
}
