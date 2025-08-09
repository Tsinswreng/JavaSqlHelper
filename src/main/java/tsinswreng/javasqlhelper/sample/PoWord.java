package tsinswreng.javasqlhelper.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class PoWord extends PoBase implements I_Id<IdWord>{
	public static String N_OWNER = "owner";
	public static String N_HEAD = "head";
	public static String N_LANG = "lang";

	private IdWord id;
	private IdUser owner;

	private String head;
	private String lang;

}
