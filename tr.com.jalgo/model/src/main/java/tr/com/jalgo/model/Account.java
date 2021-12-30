package tr.com.jalgo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * In Java, all non-static methods are by default "virtual functions." Only
 * methods marked with the keyword final, which cannot be overridden, along with
 * private methods, which are not inherited, are non-virtual.
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Accounts")
public class Account extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	private Exchange exchange;

	private String apiKey;

	private String secretKey;

	private String listenKey;

	public Account(long id) {
		this.id = id;
	}

	public Account(String apiKey, String secretKey) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
	}
}
