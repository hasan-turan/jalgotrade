package tr.com.jalgo.repository.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Query {
	@Getter
	@Setter
	private String sql;
	
	@Getter
	@Setter
	private Object[] parameters;
}
