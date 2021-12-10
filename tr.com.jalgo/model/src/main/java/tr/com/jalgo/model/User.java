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
@Table(name="Users")
public class User extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	public User(long	id) {
		this.id = id;
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username,String password) {
		this.username = username;
		this.password=password;
	}

	 

}
