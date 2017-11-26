package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.ClubXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.PlayerXMLReader;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class AppFusionator {
	
	
	
	public static void main( String[] args ) throws Exception
    {
    	// Load the Data into FusibleDataSet
    			FusibleDataSet<Player, Attribute> ds1 = new FusibleHashedDataSet<>();
    			new PlayerXMLReader().loadFromXML(new File("data/output/fused_player.xml"), "/players/player", ds1);
    			
    			FusibleDataSet<Club, Attribute> ds2 = new FusibleHashedDataSet<>();
    			new ClubXMLReader().loadFromXML(new File("data/output/fused_club.xml"), "/clubs/club", ds2);
    			
    			
    			List<Player> players = new ArrayList<Player>(ds1.get());
    			List<Club> clubs = new ArrayList<Club>(ds2.get());
    			

    			
    			for (int i = 0; i <= clubs.size(); i++) {
    				  Club tempclub = clubs.get(i);
    				  List<Player> clubplayers = tempclub.getPlayers();
    				  for(int j = 0; j<= clubplayers.size(); j++)
    				  {
    					  Player clubplayer = clubplayers.get(j);
    					  for(int k = 0; k<= players.size(); k++)
        				  {
    						  Player freeplayer = players.get(k);
    						  String freeplayerid = freeplayer.getId();
    						  String clubplayerid = clubplayer.getId();
    						  System.out.println(freeplayerid);
    						  System.out.println(clubplayerid);
    								  
        				  }
    				  }
    				}
    		
    			
    }
}
