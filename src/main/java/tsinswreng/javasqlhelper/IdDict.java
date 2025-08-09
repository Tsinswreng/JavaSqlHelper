package tsinswreng.javasqlhelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

/**
 * Java 對應 C# 的 Id_Dict<T> 結構。
 */
@Data
@AllArgsConstructor
public class IdDict<T> {
	private T id;
	private Map<String, Object> dict;
}
