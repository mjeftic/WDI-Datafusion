package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlayerAgeFuser extends AttributeValueFuser<Integer, Player, Attribute>{
	public PlayerAgeFuser(ConflictResolutionFunction<Integer, Player, Attribute> conflictResolution) {
		super(conflictResolution);
		// TODO passt das so?ß
	}


	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

		// get the fused value
		FusedValue<Integer, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

		// set the value for the fused record
		fusedRecord.setAge(fused.getValue());

		// add provenance info
		fusedRecord.setAttributeProvenance(Player.AGE, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.AGE);
	}

	@Override
	protected Integer getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getAge();
	}
}
