package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.ShortestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class CapacityFuser extends AttributeValueFuser<String, Club, Attribute> {
	
	public CapacityFuser() {
		super(new ShortestString<Club, Attribute>());
	}

	@Override
	public void fuse(RecordGroup<Club, Attribute> group, Club fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

		// get the fused value
		FusedValue<String, Club, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

		// set the value for the fused record
		if(fused.getValue() != null)
		{
			fusedRecord.setCapacity(fused.getValue());
		}
	

		// add provenance info
		fusedRecord.setAttributeProvenance(Club.CAPACITY, fused.getOriginalIds());
	}

	@Override
	public boolean hasValue(Club record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Club.CAPACITY);
	}

	@Override
	protected String getValue(Club record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getCapacity();
	}


}
