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
public class Player extends AbstractRecord<Attribute> implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String brithdate;
	private String nationality;
	private int age;
	private int rating;
	private String position;
	private int height;
	private int weight;
	
	public static final Attribute NAME = new Attribute("name");
	public static final Attribute BIRTHDATE = new Attribute("brithdate");
	public static final Attribute NATIONALITY = new Attribute("nationality");
	public static final Attribute AGE = new Attribute("age");
	public static final Attribute RATING = new Attribute("rating");
	public static final Attribute POSITION = new Attribute("position");
	public static final Attribute HEIGHT = new Attribute("height");
	public static final Attribute WEIGHT = new Attribute("weight");


	public Player(String identifier, String provenance) {
		super(identifier, provenance);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBirthdate() {
		return brithdate;
	}


	public void setBirthdate(String birthdate) {
		this.brithdate = birthdate;
	}


	public String getNationality() {
		return nationality;
	}


	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}

	
	@Override
	public String toString() {
		return String.format("[Player: %s / %s / %s]", getName(),
				getPosition());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player){
			return this.getIdentifier().equals(((Player) obj).getIdentifier());
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
		if(attribute==NAME)
			return getName() != null && !getName().isEmpty();
		else if(attribute==BIRTHDATE)
			return getBirthdate() != null && !getBirthdate().isEmpty();
		else if(attribute==AGE)
				return getAge() != 0;
		else if(attribute==NATIONALITY)
			return getNationality() != null && !getNationality().isEmpty();
		else if(attribute==RATING)
			return getRating() != 0;
		else if(attribute==POSITION)
			return getPosition() != null && !getNationality().isEmpty();
		else if(attribute==WEIGHT)
			return getWeight() != 0;
		else if(attribute==HEIGHT)
			return getHeight() != 0;
		else
		return false;
	}
	
	
	
}
