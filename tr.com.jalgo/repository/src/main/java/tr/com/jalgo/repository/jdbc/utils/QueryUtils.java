package tr.com.jalgo.repository.jdbc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.jdbc.core.JdbcTemplate;

import tr.com.jalgo.common.utils.ReflectionUtils;

public class QueryUtils {

	public static <T> SimpleEntry<String, Object[]> generateInsertQuery(T classObject) throws Exception {
		StringBuilder query = new StringBuilder();
		List<Object> fieldValues = new ArrayList<Object>();

		Class<? extends Object> clazz = classObject.getClass();

		Table table = (Table) ReflectionUtils.getClassAnnotation(clazz, Table.class);
		if (table == null)
			throw new Exception(clazz + " does not have " + Table.class.getName() + " annotation");

		// INSERT INTO {TABLENAME} ( {F1,F2,...Fn} ) VALUES ({?1,?2,?3,...?n})
		query.append("INSERT INTO " + table.name());
		query.append("(");

		// Get all fields with Column annotation
		List<SimpleEntry<Field, Annotation>> fields = ReflectionUtils.getAllFieldsWithAnnotation(clazz, Column.class);

		// convert List of SimpleEntry<Field,Annotation> in to Col1,Col2,Col3...
		query.append(fields.stream().map(fa -> {
			try {
				fieldValues.add(fa.getKey().get(classObject));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ((Column) fa.getValue()).name();
		}).collect(Collectors.joining(",")));

		query.append(")");
		query.append(" VALUES (");

		query.append(fields.stream().map(fa -> "?").collect(Collectors.joining(",")));

		query.append(")");
		return new SimpleEntry<String, Object[]>(query.toString(), fieldValues.toArray());
	}

	public static <T> void executeInsert(Class<T> clazz, JdbcTemplate jdbcTemplate) throws Exception {

		SimpleEntry<String, Object[]> query = generateInsertQuery(clazz);

		jdbcTemplate.update(query.getKey(), query.getValue());

	}
}
