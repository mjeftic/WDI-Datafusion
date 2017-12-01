package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import model.Club;
import model.ClubXMLReader;
import model.Player;
import model.PlayerXMLFormatter;
import model.PlayerXMLReader;

public class AppFusionator {

	public static void main(String[] args) throws Exception {
		// Load the Data into FusibleDataSet
		FusibleDataSet<Player, Attribute> ds1 = new FusibleHashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/output/FINAL_fused_player.xml"), "/players/player", ds1);

		FusibleDataSet<Club, Attribute> ds2 = new FusibleHashedDataSet<>();
		new ClubXMLReader().loadFromXML(new File("data/output/FINAL_fused_club.xml"), "/clubs/club", ds2);

		List<Player> freeplayers = new ArrayList<Player>(ds1.get());
		List<Club> clubs = new ArrayList<Club>(ds2.get());
		List<Player> playerofclub;

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		int count = 0;
		int notfound = 0;
		int totalclubplayers = 0;

		Element element_clubs = doc.createElement("clubs");
		doc.appendChild(element_clubs);

		for (int i = 0; i < clubs.size(); i++) {

			Club tempclub = clubs.get(i);

			Element element_club = doc.createElement("club");
			element_club.appendChild(createTextElement("name", tempclub.getName(), doc));
			element_club.appendChild(createTextElement("capacity", tempclub.getCapacity(), doc));
			element_clubs.appendChild(element_club);

			List<Player> clubplayers = tempclub.getPlayers();
			totalclubplayers = totalclubplayers + clubplayers.size();
			playerofclub = new ArrayList<Player>();

			for (int j = 0; j < clubplayers.size(); j++) {
				Player clubplayer = clubplayers.get(j);
				int flag = 0;
				for (int k = 0; k < freeplayers.size(); k++) {
					Player freeplayer = freeplayers.get(k);
					if (freeplayer.getId().split("\\+")[0].equals(clubplayer.getId())) {
						count++;
						playerofclub.add(freeplayer);
						flag = 1;
						break;
					}
				}
				if (flag == 0) {
					notfound++;
					// System.out.println(clubplayer.getName() + " " +
					// clubplayer.getId());
				}

			}

			// Alle Spieler von einem Club gefunden

			Element element_players = doc.createElement("players");
			

			for (Player player : playerofclub) {
				Element element_player = doc.createElement("player");
			//	element_player.appendChild(createTextElement("id", player.getId(), doc));

				element_player.appendChild(createTextElement("name", player.getName(), doc));
				String date = player.getBirthdate().toString();
				element_player.appendChild(createTextElement("brithdate", date, doc));
				element_player.appendChild(createTextElement("nationality", player.getNationality(), doc));
				element_player.appendChild(createTextElement("position", player.getPosition(), doc));
				element_player.appendChild(createTextElement("age", Double.toString(player.getAge()), doc));
				if (player.getHeight() != null) {
					element_player.appendChild(createTextElement("height", Double.toString(player.getHeight()), doc));
				}

				if (player.getWeight() != null) {
					element_player.appendChild(createTextElement("weight", Double.toString(player.getWeight()), doc));

				}

				if (player.getRating() != null) {
					element_player.appendChild(createTextElement("rating", Double.toString(player.getRating()), doc));

				}
				if (player.getValue() != null) {
					element_player.appendChild(createTextElement("value", Double.toString(player.getValue()), doc));

				}

				element_players.appendChild(element_player);

			}
			element_club.appendChild(element_players);

		}
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("data/output/FINAL_masterfuser.xml"));
		transformer.transform(source, result);
		System.out.println("test");
		System.out.println(count);
		System.out.println(notfound);
		System.out.println("Total Clubs" + totalclubplayers);
		System.out.println("Free Player" + freeplayers.size());

	}

	public static Element createTextElement(String name, String value, Document doc) {
		Element elem = doc.createElement(name);
		if (value != null) {
			elem.appendChild(doc.createTextNode(value));
		}
		return elem;
	}
}
