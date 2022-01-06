package tr.com.jalgo.model;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "Exchanges")
public class Exchange extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String liveUrl;

	private String liveWsUrl;

	private String testUrl;

	private String testWsUrl;

	private ExchangeAccount account;

	public String getUrl(EnvironmentType accountType) {
		if (accountType == EnvironmentType.LIVE)
			return liveUrl;
		else if (accountType == EnvironmentType.TEST)
			return testUrl;
		else
			throw new RuntimeException("Account type can not be determined in exchange");
	}
	
	public String getWsUrl(EnvironmentType accountType) {
		if (accountType == EnvironmentType.LIVE)
			return liveWsUrl;
		else if (accountType == EnvironmentType.TEST)
			return testWsUrl;
		else
			throw new RuntimeException("Account type can not be determined in exchange");
	}

	public Exchange(long id) {
		this.id = id;
	}

	//@formatter:off
	public Exchange(
			String apiKey, 
			String secretKey, 
			String url, 
			String wsUrl,
			EnvironmentType type) {
	//@formatter:on
		this.account = new ExchangeAccount(apiKey, secretKey, type);
		this.liveUrl = url;
		this.liveWsUrl = wsUrl;

	}

}
