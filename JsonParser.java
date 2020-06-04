/**
Author: Gal Fudim
Date: November 2019
*/

import java.io.*;
import java.util.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;

@SuppressWarnings("unchecked")
public class JsonParser {
    public static void main(String[] args) {
        URL url;
        String text, date = "", time = "", currDest, currTimeUnparsed, output;
        JSONObject curr, jsonData;
        JSONArray trainData, currDestArray;
        int currTime, numTrains;
        List <String> dests;
        Map <Integer, List <String>> timeAndDest = new TreeMap<>();

        try {
            // Fetching data from BART API, with the following parameters: cmd=etd (current departure information), orig=MONT (Montgomery St. station), key=MW9S-E7SL-26DU-VV8V (test key), json=y (produces JSON output)
            url = new URL("http://api.bart.gov/api/etd.aspx?cmd=etd&orig=MONT&key=MW9S-E7SL-26DU-VV8V&json=y");
            // Reading and saving each line of the JSON output
            Scanner sc = new Scanner(url.openStream());
            text = "";
            while (sc.hasNext()) {
                text += sc.nextLine();
            }
            sc.close();
            // Parsing JSON output
            JSONParser parse = new JSONParser();
            jsonData = (JSONObject) parse.parse(text);
            // Examining output of root dictionary
            jsonData = (JSONObject) jsonData.get("root");
            // Current date and time
            date = (jsonData.get("date")).toString();
            time = (jsonData.get("time")).toString();
            // Extracting relevant train information from root dictionary
            trainData = (JSONArray) jsonData.get("station");
            jsonData = (JSONObject) trainData.get(0);
            trainData = (JSONArray) jsonData.get("etd");
            for (int i = 0; i < trainData.size(); i++) {
                curr = (JSONObject) trainData.get(i);
                currDest = curr.get("destination").toString();
                currDestArray = (JSONArray) curr.get("estimate");
                for (int j = 0; j < currDestArray.size(); j++) {
                    currTimeUnparsed = (((JSONObject) currDestArray.get(j)).get("minutes")).toString();
                    currTime = (currTimeUnparsed.equals("Leaving") ? 0 : Integer.parseInt(currTimeUnparsed));
                    // If no trains are departing at this time, add a new destination list to the associated time key
                    if (timeAndDest.get(currTime) == null) {
                        dests = new ArrayList <>();
                        dests.add(currDest);
                        // Otherwise if there are already trains departing at this time, append the destination to the existing list associated with the time key
                    } else {
                        dests = timeAndDest.get(currTime);
                        dests.add(currDest);
                    }
                    timeAndDest.put(currTime, dests);
                }
            }
            trainData.add(jsonData);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        numTrains = Math.min(timeAndDest.keySet().size(), 10);
        output = "--------------------------------------------------\n";
        output += "Montgomery St.\t\t" + time + "\t\t" + date;
        output += "\n--------------------------------------------------\n";
        for (int i: timeAndDest.keySet()) {
            if (numTrains <= 0) {
                break;
            }
            for (int j = 0; j < timeAndDest.get(i).size(); j++) {
                output += (i + " min\t");
                output += timeAndDest.get(i).get(j) + "\n";
                numTrains--;
            }
        }
        System.out.println(output);
    }
}