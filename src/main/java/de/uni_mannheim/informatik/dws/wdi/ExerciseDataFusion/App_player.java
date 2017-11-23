package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.ActorsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.CapacityEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DirectorEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.NameEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.PlayerBirthdateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.PlayerNameEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.PlayerNationalityEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.PlayersEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.ActorsFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.CapacityFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DirectorFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.PlayerBirthdateFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.PlayerNameFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.PlayerNationalityFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.PlayersFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.TitleFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Player;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.PlayerXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.PlayerXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.FusiblePlayerFactory;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.FusibleMovieFactory;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.MovieXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class App_player 
{
    public static void main( String[] args ) throws Exception
    {
    	// Load the Data into FusibleDataSet
    			FusibleDataSet<Player, Attribute> ds1 = new FusibleHashedDataSet<>();
    			new PlayerXMLReader().loadFromXML(new File("data/input/fifa17.xml"),"/stadiums/stadium/clubs/club/players/player", ds1);
    			ds1.printDataSetDensityReport();
    		

    			FusibleDataSet<Player, Attribute> ds2 = new FusibleHashedDataSet<>();
    			new PlayerXMLReader().loadFromXML(new File("data/input/fut17.xml"), "/stadiums/stadium/clubs/club/players/player", ds2);
    			ds2.printDataSetDensityReport();

    			FusibleDataSet<Player, Attribute> ds3 = new FusibleHashedDataSet<>();
    			new PlayerXMLReader().loadFromXML(new File("data/input/transfermarkt.xml"), "/stadiums/stadium/clubs/club/players/player", ds3);
    			ds3.printDataSetDensityReport();
    			

    			// Maintain Provenance
    			// Scores (e.g. from rating)
    			ds1.setScore(3.0);
    			ds2.setScore(1.0);
    			ds3.setScore(2.0);

    			//TODO
    			// load correspondences
    			CorrespondenceSet<Player, Attribute> correspondences = new CorrespondenceSet<>();
    			correspondences.loadCorrespondences(new File("data/correspondences/fifa17_2_fut17_correspondences.csv"), ds1, ds2);
    			//correspondences.loadCorrespondences(new File("data/correspondences/fifa17_2_trans_correspondences.csv"), ds1, ds3);
    			//correspondences.loadCorrespondences(new File("data/correspondences/fut17_2_trans_correspondences.csv"), ds2, ds3);    			
    			// write group size distribution
    			correspondences.printGroupSizeDistribution();

    			// define the fusion strategy
    			DataFusionStrategy<Player, Attribute> strategy = new DataFusionStrategy<>(new FusiblePlayerFactory());
    			
    			
    			// add attribute fusers
    			strategy.addAttributeFuser(Player.NAME, new PlayerNameFuser(), new PlayerNameEvaluationRule());
    			strategy.addAttributeFuser(Player.BIRTHDATE, new PlayerBirthdateFuser(), new PlayerBirthdateEvaluationRule());
    			strategy.addAttributeFuser(Player.NATIONALITY, new PlayerNationalityFuser(), new PlayerNationalityEvaluationRule());
    			/*strategy.addAttributeFuser(Player.AGE, new PlayerAgeFuser(), new PlayerAgeRule());
    			strategy.addAttributeFuser(Player.RATING, new PlayerRating(), new PlayerRatingEvaluationRule());
    			strategy.addAttributeFuser(Player.POSITION, new PlayerPosition(), new PlayerPositionEvaluationRule());
    			strategy.addAttributeFuser(Player.HEIGHT, new PlayerHeight(), new PlayerHeightEvaluationRule());
    			strategy.addAttributeFuser(Player.WEIGHT, new PlayerWeight(), new PlayerWeightEvaluationRule());*/
    		
    			
    			// create the fusion engine
    			DataFusionEngine<Player, Attribute> engine = new DataFusionEngine<>(strategy);

    			// print consistency report
    			engine.printClusterConsistencyReport(correspondences, null);
    			
    			// run the fusion
    			FusibleDataSet<Player, Attribute> fusedDataSet = engine.run(correspondences, null);

    			// write the result
    			new PlayerXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

    			// load the gold standard
    			DataSet<Player, Attribute> gs = new FusibleHashedDataSet<>();
    			//new PlayerXMLReader().loadFromXML(new File("data/goldstandard/gs_Player.xml"), "/Players/Player", gs);
    			//new PlayerXMLReader().loadFromXML(new File("data/goldstandard/gs_Player_stadium_2_fifa17.csv"),"/stadiums/stadium/Players/Player", gs);
    			
    			// evaluate
    			DataFusionEvaluator<Player, Attribute> evaluator = new DataFusionEvaluator<>(
    					strategy, new RecordGroupFactory<Player, Attribute>());
    			evaluator.setVerbose(true);
    			double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

    			System.out.println(String.format("Accuracy: %.2f", accuracy));
    }
}
