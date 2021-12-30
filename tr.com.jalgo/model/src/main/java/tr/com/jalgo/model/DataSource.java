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
@Table(name = "DataSources")
public class DataSource extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String url;

	public DataSource(String name) {
		this.name = name;
	}

	public DataSource(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public DataSource(long id) {
		this.id = id;
	}

	public DataSource(long id, String name) {
		this.id = id;
		this.name = name;
	}

}
