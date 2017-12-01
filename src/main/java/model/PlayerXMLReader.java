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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.text.DateFormatter;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class PlayerXMLReader extends XMLMatchableReader<Player, Attribute>  {

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseDataset(de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	protected void initialiseDataset(DataSet<Player, Attribute> dataset) {
		super.initialiseDataset(dataset);
		
		dataset.addAttribute(Player.NAME);
		dataset.addAttribute(Player.BIRTHDATE);
		dataset.addAttribute(Player.AGE);
		dataset.addAttribute(Player.HEIGHT);
		dataset.addAttribute(Player.NATIONALITY);
		dataset.addAttribute(Player.POSITION);
		dataset.addAttribute(Player.WEIGHT);
		dataset.addAttribute(Player.RATING);
		dataset.addAttribute(Player.VALUE);
		
	}
	
	@Override
	public Player createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
				Player player = new Player(id, provenanceInfo);
				
				// fill the attributes
				player.setName(getValueFromChildElement(node, "name"));
				

			    player.setBirthdate(getValueFromChildElement(node, "brithdate"));

				
				player.setAge((double)Double.parseDouble(getValueFromChildElement(node, "age")));
				try {
					player.setRating((double)Double.parseDouble(getValueFromChildElement(node, "rating")));
				} catch (Exception e) {
					
				}
				try {
					player.setHeight((double)Double.parseDouble(getValueFromChildElement(node, "height")));
				} catch (Exception e) {
				}
				
				try {
					player.setWeight((double)Double.parseDouble(getValueFromChildElement(node, "weight")));
				} catch (Exception e) {
					
				}
				try {
					player.setValue((double)Double.parseDouble(getValueFromChildElement(node, "value")));
				} catch (Exception e) {
					
				}
				
				player.setNationality(getValueFromChildElement(node, "nationality"));
				player.setPosition(getValueFromChildElement(node, "position"));
		

		return player;
	}

}
