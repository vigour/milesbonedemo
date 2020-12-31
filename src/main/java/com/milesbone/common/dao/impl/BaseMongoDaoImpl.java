package com.milesbone.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.milesbone.common.dao.IBaseMongoDao;

public abstract class BaseMongoDaoImpl<T> implements IBaseMongoDao<T> {
		
		protected abstract Class<T> getEntityClass();
		
		@Autowired
		protected MongoTemplate mongoTemplate;

		@Override
		public void save(T entity) {
			mongoTemplate.save(entity);
		}

		@Override
		public void update(T entity) {
			Map<String, Object> map = null;
			try {
				map = parseEntity(entity);
			}catch (Exception e) {
				e.printStackTrace();
			}
			String id = null;
			Object value = null;
			Update update = new Update();
			if(map!=null && map.size()>0) {
				for(String key:map.keySet()) {
					if(key.startsWith("{")) {
						id = key.substring(key.indexOf("{")+1,key.indexOf("}"));
						value = map.get(key);
					}else {
						update.set(key, map.get(key));
					}
				}
			}
			mongoTemplate.updateFirst(new Query().addCriteria(Criteria.where(id).is(value)), update, getEntityClass());
		}

		@Override
		public void delete(Serializable... ids) {
			if(ids!=null&&ids.length>0) {
				for(Serializable id:ids) {
					mongoTemplate.remove(mongoTemplate.findById(id, getEntityClass()));
				}
			}
			
		}

		@Override
		public T find(Serializable id) {
			return mongoTemplate.findById(id, getEntityClass());
		}

		@Override
		public List<T> findAll() {
			return mongoTemplate.findAll(getEntityClass());
		}

		@SuppressWarnings("deprecation")
		@Override
		public List<T> findAll(String order) {
			List<Order> orderlist = parseOrder(order);
			if(orderlist==null||orderlist.size()==0) {
				return findAll();
			}
			
			return mongoTemplate.find(new Query().with(new Sort(orderlist)), getEntityClass());
		}

		@Override
		public List<T> findByProp(String propName, Object value) {
			return findByProp(propName, value, null);
		}

		@SuppressWarnings("deprecation")
		@Override
		public List<T> findByProp(String propName, Object value, String order) {
			Query query = new Query();
			query.addCriteria(Criteria.where(propName).is(value));
			List<Order> orderlist = parseOrder(order);
			if(orderlist!=null && orderlist.size()>0) {
				query.with(new Sort(orderlist));
			}
			return null;
		}

		@Override
		public List<T> findByProps(String[] propName, Object[] values) {
			return findByProps(propName, values, null);
		}

		@Override
		public List<T> findByProps(String[] propName, Object[] values, String order) {
			Query query = createQuery(propName, values, order);
			return mongoTemplate.find(query, getEntityClass());
		}

		@Override
		public T uniqueByProp(String propName, Object value) {
			return mongoTemplate.findOne(new Query().addCriteria(Criteria.where(propName).is(value)),getEntityClass());
		}

		@Override
		public T uniqueByProps(String[] propName, Object[] values) {
			Query query = createQuery(propName, values, null);
			return mongoTemplate.findOne(query, getEntityClass());
		}
		@Override
		public int countByCondition(String[] params, Object[] values) {
			Query query = createQuery(params, values, null);
			Long count = mongoTemplate.count(query, getEntityClass());
			return count.intValue();
		}

		
		protected Map<String, Object> parseEntity(T t) throws Exception{
			Map<String, Object> map = new HashMap<>();
			String id = "";
			Field[] declaredFields = getEntityClass().getDeclaredFields();
			for(Field field:declaredFields) {
				if(field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					map.put("{"+field.getName()+"}", field.get(t));
					id = field.getName();
					break;
				}
			}
			
			Method[] declaredMethods = getEntityClass().getDeclaredMethods();
			if( declaredFields != null&& declaredFields.length > 0 ) {
				for(Method method:declaredMethods) {
					if(method.getName().startsWith("get")&&method.getModifiers()==Modifier.PUBLIC) {
						String fieldName = parse2FieldName(method.getName());
						if(!fieldName.equals(id)) {
							map.put(fieldName, method.invoke(t));
						}
					}
				}
			}
			return map;
		}
		
		private String parse2FieldName(String method) {
			String name = method.replace("get", "");
			name = name.substring(0, 1).toLowerCase()+name.substring(1);
			return name;
		}
		
		@SuppressWarnings("deprecation")
		public Query createQuery(String[] propName,Object[] values,String order) {
			Query query = new Query();
			//where
			if(propName!=null&&values!=null) {
				for(int i=0;i<propName.length;i++) {
					query.addCriteria(Criteria.where(propName[i]).is(values[i]));
				}
			}
			
			List<Order> orderlist = parseOrder(order);
			if(orderlist!=null && orderlist.size()>0) {
				query.with(new Sort(orderlist));
			}
			return query;
		}
		
		public List<Order> parseOrder(String order){
			List<Order> list = null;
			if(order!=null && !"".equals(order)) {
				list = new ArrayList<>();
				String[] fields = order.split(",");
				Order o = null;
				String[] items = null;
				for(int i=0;i<fields.length;i++) {
					if(fields[i]==null) {
						continue;
					}
					items = fields[i].split(" ");
					if(items.length==1) {
						o = new Order(Direction.ASC,items[0]);
					}else if(items.length==2) {
						o = new Order("desc".equalsIgnoreCase(items[1])?Direction.DESC:Direction.ASC, items[0]);
					}else {
						throw new RuntimeException("order field parse error");
					}
					list.add(o);
				}
			}
			return list;
		}
	}

