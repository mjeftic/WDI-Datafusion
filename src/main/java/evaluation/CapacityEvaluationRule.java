package evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import model.Club;

public class CapacityEvaluationRule extends EvaluationRule<Club, Attribute>{

	SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();

	@Override
	public boolean isEqual(Club record1, Club record2, Attribute schemaElement) {
		// the title is correct if all tokens are there, but the order does not
		// matter
		return sim.calculate(record1.getCapacity(), record2.getCapacity()) == 1.0;
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Club record1, Club record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}
}