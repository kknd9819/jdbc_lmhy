package com.zz.model.basic.dao.annotation;

import com.zz.util.shengyuan.CamelCaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;



public class AnnotationHelper {

	/**
	 * 返回insert或select字段
	 * 
	 * @param fields
	 * @return
	 */
	public static String getEntityInsertFields(Field[] fields) {
		if (fields == null || fields.length <= 0) {
			return null;
		}
		StringBuilder filedsBuilder = new StringBuilder(500);
		int index = 0;
		for (Field field : fields) {
			
			field.setAccessible(true);
			 
			if(field.getModifiers() >= Modifier.STATIC){
				continue;
			}
			
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			
			if (!field.isAnnotationPresent(Id.class)) {
				if (index > 0) {
					filedsBuilder.append(",");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					filedsBuilder.append(column.name());
				} else {
					filedsBuilder.append(CamelCaseUtils.toUnderlineName(field.getName()));
				}

				index++;
			}
		}
		return filedsBuilder.toString();
	}

	/**
	 * 返回@Id所对应的字段名称 如：
	 * 
	 * @Id private Long id; 则返回"id"字符串
	 * @param fields
	 * @return 主键字符串
	 */
	public static String getEntityId(Field[] fields) {
		if (fields == null || fields.length <= 0) {
			return null;
		}
		String id = null;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				id = field.getName();
				break;
			}
		}
		return id;
	}

	/**
	 * 获取实体类Entity注解名称 如：@Entity(name="sys_user") ,则返回"sys_user"字符串
	 * 
	 * @param entityClass
	 *            实体类
	 * @return 返回Entity名称
	 */
	public static <T> String getEntityTable(Class<T> entityClass) {
		if (entityClass == null) {
			return null;
		}

		if (entityClass.isAnnotationPresent(Entity.class)) {
			Entity entity = entityClass.getAnnotation(Entity.class);
			return entity.name();
		}
		return null;
	}

	public static String getInsertValueParams(Field[] fields) {
		if (fields == null || fields.length <= 0) {
			return null;
		}
		StringBuilder filedsBuilder = new StringBuilder(500);
		int index = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			
			if(field.getModifiers() >= Modifier.STATIC){
				continue;
			}
			
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			if (!field.isAnnotationPresent(Id.class)) {
				if (index > 0) {
					filedsBuilder.append(",");
				}
				filedsBuilder.append(":");
				filedsBuilder.append(field.getName());
				index++;
			}
		}
		return filedsBuilder.toString();
	}

	public static String getUpdateValueParams(Field[] fields) {
		if (fields == null || fields.length <= 0) {
			return null;
		}
		StringBuilder filedsBuilder = new StringBuilder(500);
		int index = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			
			if(field.getModifiers() >= Modifier.STATIC){
				continue;
			}
			
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			if (!field.isAnnotationPresent(Id.class)) {
				if (index > 0) {
					filedsBuilder.append(",");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					filedsBuilder.append(column.name());
				} else {
					filedsBuilder.append(CamelCaseUtils.toUnderlineName(field.getName()));
				}
				filedsBuilder.append(" = ");
				filedsBuilder.append(":");
				filedsBuilder.append(field.getName());
				index++;
			}
		}
		return filedsBuilder.toString();
	}

	public static List<String> getEntityFields(Field[] fields) {
		if (fields == null || fields.length <= 0) {
			return null;
		}
		List<String> listString = new ArrayList<String>();
		for (Field field : fields) {
			field.setAccessible(true);
			
			if(field.getModifiers() >= Modifier.STATIC){
				continue;
			}
			
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}

			listString.add(field.getName());
		}
		return listString;
	}
}
