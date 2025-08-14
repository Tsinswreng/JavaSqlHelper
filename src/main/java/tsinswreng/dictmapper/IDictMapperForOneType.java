package tsinswreng.dictmapper;

import java.util.Map;

public interface IDictMapperForOneType {
	Class<?> getTargetType();
	//void setTargetType(Class<?> v);

	Map<String, Object> toDictShallow(Object obj);
	Map<String, Class<?>> getTypeDictShallow();
	Object assignShallow(Object obj, Map<String, Object> dict);

}
