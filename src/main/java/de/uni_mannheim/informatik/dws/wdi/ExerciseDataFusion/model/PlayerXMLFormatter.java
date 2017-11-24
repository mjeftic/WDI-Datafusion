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
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.time.format.DateTimeFormatter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PlayerXMLFormatter extends XMLFormatter<Player> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("player");
	}

	@Override
	public Element createElementFromRecord(Player record, Document doc) {
		Element player = doc.createElement("player");

		player.appendChild(createTextElement("name", record.getName(), doc));
		
		String date = record.getBirthdate().toString();
		
		player.appendChild(createTextElement("brithdate", date, doc));
		player.appendChild(createTextElement("nationality", record.getNationality(), doc));
		player.appendChild(createTextElement("position", record.getPosition(), doc));
		player.appendChild(createTextElement("age", Double.toString(record.getAge()), doc));
		if(record.getHeight() != null)
		{
			player.appendChild(createTextElement("height", Double.toString(record.getHeight()), doc));
		}
		
		if(record.getWeight() != null)
		{
			player.appendChild(createTextElement("weight", Double.toString(record.getWeight()), doc));

		}
		
		if(record.getRating() != null)
		{
			player.appendChild(createTextElement("rating", Double.toString(record.getRating()), doc));

		}
		if(record.getValue() != null)
		{
			player.appendChild(createTextElement("value", Double.toString(record.getValue()), doc));

		}
		

		return player;
	}


}
