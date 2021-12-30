package tr.com.jalgo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Indicators")
public class Indicator extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Formula")
	private String formula;

	public Indicator(long id) {
		this.id = id;
	}

	public Indicator(String title, String description, String formula) {
		this.title = title;
		this.description = description;
		this.formula = formula;
	}
}
