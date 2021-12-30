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
@Table(name="Parities")
 
public class Parity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Asset baseAsset;
	private Asset counterAsset;

	public Parity(long id) {
		this.id=id;
	}
	
	public Parity(long id,Asset baseAsset, Asset counterAsset) {
		this.id=id;
		this.baseAsset = baseAsset;
		this.counterAsset = counterAsset;
	}
	public Parity(Asset baseAsset, Asset counterAsset) {
		this.baseAsset = baseAsset;
		this.counterAsset = counterAsset;
	}

}
