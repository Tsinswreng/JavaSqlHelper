package tsinswreng.javasqlhelper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Column {
	private String dbName;

	private String dbType;

	private Class<?> rawCodeType;

	private Class<?> upperCodeType;

	private List<String> additionalSqls;

	private IUpperTypeMapFn upperTypeMapper;

	private boolean notNull;

}
