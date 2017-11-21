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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} representing a movie.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Club extends AbstractRecord<Attribute> implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	private String name;
	private List<Player> players;	
	
	
	public static final Attribute NAME = new Attribute("name");
	public static final Attribute PLAYERS = new Attribute("players");


	public Club(String identifier, String provenance) {
		super(identifier, provenance);
		players = new LinkedList<>();
	}


	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<Player> getPlayers() {
		return players;
	}



	public void setPlayers(List<Player> players) {
		this.players = players;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Club){
			return this.getIdentifier().equals(((Club) obj).getIdentifier());
		}else
			return false;
	}



	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return id;
	}


	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}


	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		if(attribute==NAME)
			return getName() != null && !getName().isEmpty();
		else if(attribute==PLAYERS)
			return getPlayers() != null && getPlayers().size() > 0;
		else
			return false;
	}



	
	
	
	
	

	
	
}
