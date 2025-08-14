package tsinswreng.dictmapper.reflect;

import java.util.HashMap;
import java.util.Map;

import tsinswreng.dictmapper.*;

public class DictMapperShallow implements IDictMapperShallow {

	private final Map<Class<?>, IDictMapperForOneType> typeMapperMap = new HashMap<>();

	@Override
	public Map<Class<?>, IDictMapperForOneType> getType_Mapper() {
		return typeMapperMap;
	}

	@Override
	public Map<String, Class<?>> getTypeDictShallow(Class<?> type) {
		IDictMapperForOneType mapper = typeMapperMap.get(type);
		if (mapper == null) {
			mapper = new DictMapperForOneType<>(type);
			typeMapperMap.put(type, mapper);
		}
		return mapper.getTypeDictShallow();
	}

	@Override
	public Map<String, Object> toDictShallow(Class<?> type, Object obj) {
		IDictMapperForOneType mapper = typeMapperMap.get(type);
		if (mapper == null) {
			mapper = new DictMapperForOneType<>(type);
			typeMapperMap.put(type, mapper);
		}
		return mapper.toDictShallow(obj);
	}

	@Override
	public void assignShallow(Class<?> type, Object target, Map<String, Object> dict) {
		IDictMapperForOneType mapper = typeMapperMap.get(type);
		if (mapper == null) {
			mapper = new DictMapperForOneType<>(type);
			typeMapperMap.put(type, mapper);
		}
		mapper.assignShallow(target, dict);
	}
}
