package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.ShortestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlayerNameFuser extends AttributeValueFuser<String, Club, Attribute> {
	
	public PlayerNameFuser() {
		super(new LongestString<Club, Attribute>());
	}

	@Override
	public void fuse(RecordGroup<Club, Attribute> group, Club fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

		// get the fused value
		FusedValue<String, Club, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

		// set the value for the fused record
		fusedRecord.setName(fused.getValue());

		// add provenance info
		fusedRecord.setAttributeProvenance(Club.NAME, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Club record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Club.NAME);
	}

	@Override
	protected String getValue(Club record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getName();
	}


}
