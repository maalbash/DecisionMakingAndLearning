package Learning;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utility.GameConstants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohz2 on 4/28/2017.
 */
public class Learner {
    public ArrayList<Record> seekRecords,shootRecords, pathFollowingRecords;
    public JSONArray jsonArray;
    public JSONObject jsonObject;
    public long DataSize;
    public float netEntropy;
    public Learner(){
        this.seekRecords = new ArrayList<>();
        this.shootRecords = new ArrayList<>();
        this.pathFollowingRecords = new ArrayList<>();
        this.jsonArray = new JSONArray();
        this.jsonObject = new JSONObject();
    }

    public void loadData(){
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("C:/Users/mohz2/Desktop/GradSchool/Semester4/Building Game AI/HWs/HW3/data2.json"));
            this.jsonObject = (JSONObject) obj;

            this.jsonArray = (JSONArray) jsonObject.get("DATA");
            DataSize = this.jsonArray.size();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void runLearning(){
        calculateNetEntropy();
        calculateInformationGain();
    }

    public void calculateNetEntropy(){
        float probSeek, probShoot, probPathFollow;
        netEntropy = 0;
        for(Object o: jsonArray) {
            JSONObject obj = (JSONObject) o;
            switch ((String)obj.get("MONSTER_STATE"))
            {
                case "SHOOTING":
                    addToShootingList(obj);
                    break;
                case "SEEKING":
                    addToSeekingList(obj);
                    break;
                case "PATHFOLLOWING":
                    addToPathFollowingList(obj);
                    break;
            }
        }
        probSeek = (float)this.seekRecords.size()/(float)this.jsonArray.size();
        probShoot = (float)this.shootRecords.size()/(float) this.jsonArray.size();
        probPathFollow = (float) this.pathFollowingRecords.size()/ (float) this.jsonArray.size();

        netEntropy = - (probShoot*log2(probShoot)) - (probSeek*log2(probSeek)) - (probPathFollow*log2(probPathFollow));
    }

    public void calculateInformationGain(){
        HashMap<GameConstants.MONSTER_STATE,Integer> trueLOS = new HashMap<>();
        HashMap<GameConstants.MONSTER_STATE,Integer> falseLOS = new HashMap<>();
        HashMap<GameConstants.MONSTER_STATE,Integer> trueDistance = new HashMap<>();
        HashMap<GameConstants.MONSTER_STATE,Integer> falseDistance = new HashMap<>();

        trueLOS.put(GameConstants.MONSTER_STATE.SEEKING,0);
        trueLOS.put(GameConstants.MONSTER_STATE.SHOOTING,0);
        trueLOS.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,0);
        falseLOS.put(GameConstants.MONSTER_STATE.SEEKING,0);
        falseLOS.put(GameConstants.MONSTER_STATE.SHOOTING,0);
        falseLOS.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,0);

        trueDistance.put(GameConstants.MONSTER_STATE.SEEKING,0);
        trueDistance.put(GameConstants.MONSTER_STATE.SHOOTING,0);
        trueDistance.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,0);
        falseDistance.put(GameConstants.MONSTER_STATE.SEEKING,0);
        falseDistance.put(GameConstants.MONSTER_STATE.SHOOTING,0);
        falseDistance.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,0);

        for(Record record:seekRecords)
        {
            if(record.LOS)
                trueLOS.put(GameConstants.MONSTER_STATE.SEEKING,(trueLOS.get(GameConstants.MONSTER_STATE.SEEKING)+1));
            else
                falseLOS.put(GameConstants.MONSTER_STATE.SEEKING,(trueLOS.get(GameConstants.MONSTER_STATE.SEEKING)+1));

            if(record.SHOOTINGDISTANCE)
                trueDistance.put(GameConstants.MONSTER_STATE.SEEKING,(trueLOS.get(GameConstants.MONSTER_STATE.SEEKING)+1));
            else
                falseDistance.put(GameConstants.MONSTER_STATE.SEEKING,(trueLOS.get(GameConstants.MONSTER_STATE.SEEKING)+1));
        }

        for(Record record:shootRecords)
        {
            if(record.LOS)
                trueLOS.put(GameConstants.MONSTER_STATE.SHOOTING,(trueLOS.get(GameConstants.MONSTER_STATE.SHOOTING)+1));
            else
                falseLOS.put(GameConstants.MONSTER_STATE.SHOOTING,(trueLOS.get(GameConstants.MONSTER_STATE.SHOOTING)+1));

            if(record.SHOOTINGDISTANCE)
                trueDistance.put(GameConstants.MONSTER_STATE.SHOOTING,(trueLOS.get(GameConstants.MONSTER_STATE.SHOOTING)+1));
            else
                falseDistance.put(GameConstants.MONSTER_STATE.SHOOTING,(trueLOS.get(GameConstants.MONSTER_STATE.SHOOTING)+1));
        }

        for(Record record:pathFollowingRecords)
        {
            if(record.LOS)
                trueLOS.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,(trueLOS.get(GameConstants.MONSTER_STATE.PATHFOLLOWING)+1));
            else
                falseLOS.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,(trueLOS.get(GameConstants.MONSTER_STATE.PATHFOLLOWING)+1));

            if(record.SHOOTINGDISTANCE)
                trueDistance.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,(trueLOS.get(GameConstants.MONSTER_STATE.PATHFOLLOWING)+1));
            else
                falseDistance.put(GameConstants.MONSTER_STATE.PATHFOLLOWING,(trueLOS.get(GameConstants.MONSTER_STATE.PATHFOLLOWING)+1));
        }

        long trueSize = trueDistance.get(GameConstants.MONSTER_STATE.SEEKING) + trueDistance.get(GameConstants.MONSTER_STATE.SHOOTING) + trueDistance.get(GameConstants.MONSTER_STATE.PATHFOLLOWING);
        trueSize += trueLOS.get(GameConstants.MONSTER_STATE.SEEKING) + trueLOS.get(GameConstants.MONSTER_STATE.SHOOTING) + trueLOS.get(GameConstants.MONSTER_STATE.PATHFOLLOWING);

        long falseSize = falseDistance.get(GameConstants.MONSTER_STATE.SEEKING) + falseDistance.get(GameConstants.MONSTER_STATE.SHOOTING) + falseDistance.get(GameConstants.MONSTER_STATE.PATHFOLLOWING);
        falseSize += falseLOS.get(GameConstants.MONSTER_STATE.SEEKING) + falseLOS.get(GameConstants.MONSTER_STATE.SHOOTING) + falseLOS.get(GameConstants.MONSTER_STATE.PATHFOLLOWING);


    }


    public void addToShootingList(JSONObject obj){
        Record record = new Record();
        String LOS = (String) obj.get("LOS");
        String DISTANCE = (String) obj.get("DISTANCE");
        record.STATE = GameConstants.MONSTER_STATE.SHOOTING;
        record.LOS = (LOS.compareTo("true") == 0);
        record.SHOOTINGDISTANCE = (DISTANCE.compareTo("true") == 0);

        this.shootRecords.add(record);
    }

    public void addToSeekingList(JSONObject obj){
        Record record = new Record();
        String LOS = (String) obj.get("LOS");
        String DISTANCE = (String) obj.get("DISTANCE");
        record.STATE = GameConstants.MONSTER_STATE.SEEKING;
        record.LOS = (LOS.compareTo("true") == 0);
        record.SHOOTINGDISTANCE = (DISTANCE.compareTo("true") == 0);

        this.seekRecords.add(record);
    }

    public void addToPathFollowingList(JSONObject obj){
        Record record = new Record();
        String LOS = (String) obj.get("LOS");
        String DISTANCE = (String) obj.get("DISTANCE");
        record.STATE = GameConstants.MONSTER_STATE.PATHFOLLOWING;
        record.LOS = (LOS.compareTo("true") == 0);
        record.SHOOTINGDISTANCE = (DISTANCE.compareTo("true") == 0);

        this.pathFollowingRecords.add(record);
    }

    public float log2(float a){
        return (float) Math.log(a)/ (float) Math.log(2f);
    }
}
