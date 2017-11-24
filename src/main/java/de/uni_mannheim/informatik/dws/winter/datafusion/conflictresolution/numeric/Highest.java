package de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.numeric;

import java.util.Collection;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Fusible;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Highest <RecordType extends Matchable & Fusible<SchemaElementType>, SchemaElementType extends Matchable> extends
ConflictResolutionFunction<Double, RecordType, SchemaElementType>{

	@Override
	public FusedValue<Double, RecordType, SchemaElementType> resolveConflict(
			Collection<FusibleValue<Double, RecordType, SchemaElementType>> values) {

		if (values.size() == 0) {
			return new FusedValue<>((Double) null);
		} else {

			double highest_value = 0.0;
			

			for (FusibleValue<Double, RecordType, SchemaElementType> value : values) {
				
				if (highest_value < value.getValue() && value.getValue()<99) {
					highest_value = value.getValue();				
				}	
			}

			return new FusedValue<>(highest_value);

		}
	}

	
}
