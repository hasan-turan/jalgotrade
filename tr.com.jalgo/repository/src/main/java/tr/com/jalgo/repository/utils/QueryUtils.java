package tr.com.jalgo.repository.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import tr.com.jalgo.common.utils.ReflectionUtils;
import tr.com.jalgo.model.BaseModel;
import tr.com.jalgo.model.exceptions.RepositoryException;
import tr.com.jalgo.repository.jdbc.Query;

public class QueryUtils  {

	public static <T extends BaseModel> String generateInsertQuery(T param,String tableName) {
		 StringBuilder queryBuilder = new StringBuilder();
		 queryBuilder.append("INSERT INTO " + tableName + "(" );
		 
		 
		 
		 
		 queryBuilder.append(")");
		 return queryBuilder.toString();
	}
	
	 
	
	public static <T extends BaseModel> Query  generateSelectQuery(T param) {
		
		String tableName= getTableName(param);
		
		String SQL = "SELECT * FROM " + tableName + " WHERE 1=1";
		List<Object> params = new ArrayList<Object>();

		if (param.getId() > 0) {
			SQL += " AND Id=?";
			params.add(param.getId());
		}
 
 
		return new Query(SQL, params.toArray());
	}
	
	private static <T extends BaseModel> String getTableName(T param) {
		Annotation tableAnnotation= ReflectionUtils.getClassAnnotation(param, Table.class);
		if(tableAnnotation!=null	)
			return ((Table)tableAnnotation).name();
		
		throw new RepositoryException("Annotation type of " + Table.class.getName() +" could not be found in" + param.getClass().getName());
	}
}
