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
	private String value;

	public Interval(String value) {
		this.value = value;
	}
	public Interval(long id) {
		this.id = id;
	}
	 
}
