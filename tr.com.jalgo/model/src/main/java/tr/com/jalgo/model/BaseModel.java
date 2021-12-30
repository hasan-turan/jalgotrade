package tr.com.jalgo.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

 
import lombok.Getter;
import lombok.Setter;

 
@MappedSuperclass
public class BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	@Column(name = "Id")
	protected long id;
	
	
 
	
	 
}
