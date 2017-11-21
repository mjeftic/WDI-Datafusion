package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Actor;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class PlayersEvaluationRule extends EvaluationRule<Club, Attribute>{

	@Override
	public boolean isEqual(Club record1, Club record2, Attribute schemaElement) {
		Set<String> players1 = new HashSet<>();

		for (Player a : record1.getPlayers()) {
			// note: evaluating using the actor's name only suffices for simple
			// lists
			// in your project, you should have actor ids which you use here
			// (and in the identity resolution)
			players1.add(a.getName());
		}

		Set<String> players2 = new HashSet<>();
		for (Player a : record2.getPlayers()) {
			players2.add(a.getName());
		}

		return players1.containsAll(players2) && players2.containsAll(players1);
	}

	@Override
	public boolean isEqual(Club record1, Club record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
