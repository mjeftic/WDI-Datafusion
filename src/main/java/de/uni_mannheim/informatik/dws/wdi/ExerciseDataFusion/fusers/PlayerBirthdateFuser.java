package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import java.time.LocalDateTime;

//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
//import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.MostRecent;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlayerBirthdateFuser extends AttributeValueFuser<String, Player, Attribute> {

	public PlayerBirthdateFuser() {
		super(new MostRecent<String, Player, Attribute>());
	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.BIRTHDATE);
	}

	@Override
	protected String getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getBirthdate();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setBirthdate(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.BIRTHDATE, fused.getOriginalIds());
	}

}
