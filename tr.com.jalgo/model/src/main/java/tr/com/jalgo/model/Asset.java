package tr.com.jalgo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Assets")
public class Asset extends BaseModel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String symbol;
	private String description;
	
	public Asset(long id) {
		this.id=id;
	}
	
	
	public Asset(String symbol) {
		this.symbol=symbol;
	}
	
	
	public Asset(long id,String symbol) {
		this.id=id;
		this.symbol=symbol;
	}
}
