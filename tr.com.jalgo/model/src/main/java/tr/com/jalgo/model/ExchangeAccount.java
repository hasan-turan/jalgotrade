package tr.com.jalgo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.jalgo.model.types.EnvironmentType;

/**
 * In Java, all non-static methods are by default "virtual functions." Only
 * methods marked with the keyword final, which cannot be overridden, along with
 * private methods, which are not inherited, are non-virtual.
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ExchangeAccounts")
public class ExchangeAccount extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	private Exchange exchange;

	private String apiKey;

	private String secretKey;
 
	private String listenKey;
	
	private EnvironmentType type;

	public ExchangeAccount(long id) {
		this.id = id;
	}

	public ExchangeAccount(String apiKey, String secretKey,EnvironmentType type) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.type=type;
	}
}
