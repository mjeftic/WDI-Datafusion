/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package model;

import java.time.format.DateTimeFormatter;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVDataSetFormatter;

/**
 * @author Oliver Lehmberg (oli@dwslab.de)
 *
 */
public class PlayerCSVFormatter extends CSVDataSetFormatter<Player, Attribute> {

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.io.CSVDataSetFormatter#getHeader(de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	
	@Override
	public String[] getHeader(DataSet<Player, Attribute> dataset) {
		return new String[] { "id", "name", "nationality", "brithdate", "age", "rating", "position", "height", "weight","value" };
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.io.CSVDataSetFormatter#format(de.uni_mannheim.informatik.wdi.model.Matchable, de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	public String[] format(Player record, DataSet<Player, Attribute> dataset) {
		
		String date = record.getBirthdate().toString();
		
		return new String[] {
				
				record.getIdentifier(),
				record.getName(),
				record.getNationality(),
				date,
				Double.toString(record.getAge()),
				Double.toString(record.getRating()),
				record.getPosition(),
				Double.toString(record.getHeight()),
				Double.toString(record.getWeight()),
				Double.toString(record.getValue()),
		};
	}

}
