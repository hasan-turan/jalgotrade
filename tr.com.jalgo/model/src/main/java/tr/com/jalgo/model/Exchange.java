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
@Table(name="Exchanges")
public class Exchange extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String url;

	private String wsUrl;
	
	private Account account;

	public Exchange( String url, String wsUrl) {
		 
		this.url = url;
		this.wsUrl = wsUrl;
	}

	public Exchange(String name) {
		this.name = name;
	}

	public Exchange(long id) {
		this.id = id;
	}

	public Exchange(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Exchange(String apiKey, String secretKey, String apiUrl, String websocketUrl) {
		 this.account.setApiKey(apiKey);
		 this.account.setSecretKey(secretKey);
		 this.url=apiUrl;
		 this.wsUrl=websocketUrl;
	}

}
