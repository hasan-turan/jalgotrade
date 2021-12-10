package tr.com.jalgo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="Symbols")
public class Symbol extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String baseSymbol;
	private String counterSymbol;

	public Symbol(long id) {
		this.id=id;
	}
	
	
	public Symbol(String baseSymbol, String counterSymbol) {
		this.baseSymbol = baseSymbol;
		this.counterSymbol = counterSymbol;
	}

}
