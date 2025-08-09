package tsinswreng.javasqlhelper.sample;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=true)
public class PoLearn extends PoBase implements I_Id<IdLearn> {
	public static String N_WORD_ID = "wordId";
	public static String N_LEARN_RESULT = "learnResult";

	private IdLearn id;
	private IdWord wordId;
	private String learnResult;

}
