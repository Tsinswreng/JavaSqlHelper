package tsinswreng.javasqlhelper;

import java.util.Map;

public interface IDictMapperShallow {
	Map<Class<?>, IDictMapperForOneType> getType_Mapper();

	Map<String, Class<?>> getTypeDictShallow(Class<?> type);

	Map<String, Object> toDictShallow(Class<?> type, Object obj);

	void assignShallow(Class<?>type, Object target, Map<String, Object> dict);
}
