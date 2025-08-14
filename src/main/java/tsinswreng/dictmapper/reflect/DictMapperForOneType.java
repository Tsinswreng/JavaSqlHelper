//TODO 緩存元數據、只緩存BeanInfo不夠
package tsinswreng.dictmapper.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tsinswreng.dictmapper.IDictMapperForOneType;

@Data
@Builder
@NoArgsConstructor
public class DictMapperForOneType<T> implements IDictMapperForOneType {
	private static final ConcurrentHashMap<Class<?>, BeanInfo> beanInfoCache = new ConcurrentHashMap<>();
	private Class<T> targetType;

	public DictMapperForOneType(Class<T> targetType) {
		this.targetType = targetType;
	}

	private BeanInfo getBeanInfo(Class<?> clazz) throws IntrospectionException {
		return beanInfoCache.computeIfAbsent(clazz, c -> {
			try {
				return Introspector.getBeanInfo(c, Object.class);
			} catch (IntrospectionException e) {
				throw new RuntimeException("Failed to get BeanInfo for class: " + c.getName(), e);
			}
		});
	}

	@Override
	public Map<String, Object> toDictShallow(Object obj) {
		Map<String, Object> R = new LinkedHashMap<>();
		try {
			BeanInfo beanInfo = getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.equalsIgnoreCase("class")) {
					continue;
				}
				Method getter = property.getReadMethod();
				if (getter != null) {
					Object value = getter.invoke(obj);
					R.put(key, value);
				}
			}
		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return R;
	}

	@Override
	public Map<String, Class<?>> getTypeDictShallow() {
		Map<String, Class<?>> R = new HashMap<>();
		try {
			BeanInfo beanInfo = getBeanInfo(targetType);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.equalsIgnoreCase("class")) {
					continue;
				}
				R.put(key, property.getPropertyType());
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return R;
	}

	@Override
	public Object assignShallow(Object obj, Map<String, Object> dict) {
		try {
			BeanInfo beanInfo = getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.equalsIgnoreCase("class")) {
					continue;
				}
				Method setter = property.getWriteMethod();
				if (setter != null && dict.containsKey(key)) {
					setter.invoke(obj, dict.get(key));
				}
			}
		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
