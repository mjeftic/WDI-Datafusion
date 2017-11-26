package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Actor;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlayersFuser extends AttributeValueFuser<List<Player>, Club, Attribute> {

	public PlayersFuser() {
		super(new FavourSources<List<Player>, Club, Attribute>());
	}

	@Override
	protected List<Player> getValue(Club record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getPlayers();
	}

	@Override
	public void fuse(RecordGroup<Club, Attribute> group, Club fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<List<Player>, Club, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setPlayers(fused.getValue());
		fusedRecord.setAttributeProvenance(Club.PLAYERS, fused.getOriginalIds());
		
	}

	@Override
	public boolean hasValue(Club record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Club.PLAYERS);
	}
}


