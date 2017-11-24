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
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.PlayersEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.ActorsFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.CapacityFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DirectorFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.PlayersFuser;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.TitleFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Club;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.ClubXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.ClubXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.FusibleClubFactory;
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

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	// Load the Data into FusibleDataSet
    			FusibleDataSet<Club, Attribute> ds1 = new FusibleHashedDataSet<>();
    			new ClubXMLReader().loadFromXML(new File("data/input/fifa17.xml"), "/stadiums/stadium/clubs/club", ds1);
    			ds1.printDataSetDensityReport();
    		

    			FusibleDataSet<Club, Attribute> ds2 = new FusibleHashedDataSet<>();
    			new ClubXMLReader().loadFromXML(new File("data/input/fut17.xml"), "/stadiums/stadium/clubs/club", ds2);
    			ds2.printDataSetDensityReport();

    			FusibleDataSet<Club, Attribute> ds3 = new FusibleHashedDataSet<>();
    			new ClubXMLReader().loadFromXML(new File("data/input/stadium.xml"), "/stadiums/stadium/clubs/club", ds3);
    			ds3.printDataSetDensityReport();
    			

    			// Maintain Provenance
    			// Scores (e.g. from rating)
    			ds1.setScore(3.0);
    			ds2.setScore(2.0);
    			ds3.setScore(1.0);

    			//TODO
    			// load correspondences
    			CorrespondenceSet<Club, Attribute> correspondences = new CorrespondenceSet<>();
    			correspondences.loadCorrespondences(new File("data/correspondences/club_fut17_2_fifa17_correspondences.csv"), ds2, ds1);
    			correspondences.loadCorrespondences(new File("data/correspondences/club_stadium_2_fifa17_correspondences.csv"), ds3, ds1);
    			
    			// write group size distribution
    			correspondences.printGroupSizeDistribution();

    			// define the fusion strategy
    			DataFusionStrategy<Club, Attribute> strategy = new DataFusionStrategy<>(new FusibleClubFactory());
    			// add attribute fusers
    			
    			strategy.addAttributeFuser(Club.PLAYERS, new PlayersFuser(), new PlayersEvaluationRule());
    			strategy.addAttributeFuser(Club.NAME, new NameFuser(), new NameEvaluationRule());
    			strategy.addAttributeFuser(Club.CAPACITY, new CapacityFuser(), new CapacityEvaluationRule());
    			
    			// create the fusion engine
    			DataFusionEngine<Club, Attribute> engine = new DataFusionEngine<>(strategy);

    			// print consistency report
    			engine.printClusterConsistencyReport(correspondences, null);
    			
    			// run the fusion
    			FusibleDataSet<Club, Attribute> fusedDataSet = engine.run(correspondences, null);

    			// write the result
    			new ClubXMLFormatter().writeXML(new File("data/output/club_fused.xml"), fusedDataSet);

    			// load the gold standard
    			DataSet<Club, Attribute> gs = new FusibleHashedDataSet<>();
    			new ClubXMLReader().loadFromXML(new File("data/goldstandard/gs_club.xml"), "/clubs/club", gs);
    			//new ClubXMLReader().loadFromXML(new File("data/goldstandard/gs_club_stadium_2_fifa17.csv"),"/stadiums/stadium/clubs/club", gs);
    			
    			// evaluate
    			DataFusionEvaluator<Club, Attribute> evaluator = new DataFusionEvaluator<>(
    					strategy, new RecordGroupFactory<Club, Attribute>());
    			evaluator.setVerbose(true);
    			double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

    			System.out.println(String.format("Accuracy: %.2f", accuracy));
    }
}
