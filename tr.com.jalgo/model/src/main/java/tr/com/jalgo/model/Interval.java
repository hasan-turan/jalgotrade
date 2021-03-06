package tr.com.jalgo.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Interval extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public Interval(String name) {
		this.name = name;
	}
	public Interval(long id) {
		this.id = id;
	}
	 
}
