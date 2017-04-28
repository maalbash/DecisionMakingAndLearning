package engine;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import processing.core.PApplet;


/**
 * Created by mohz2 on 4/27/2017.
 */
public class Logger {

    public PApplet app;

    public JSONObject jsonObject;
    public JSONArray jsonArray;
    public int numOfData;
    public long updateTime;

    public Logger(PApplet app) {
        this.app = app;
        this.jsonObject = new JSONObject();
        this.jsonArray = new JSONArray();
        this.updateTime = app.millis();
        numOfData = 0;
    }


    public void populateJsonArray() {
        if (app.millis() - updateTime >= 50) {
            JSONObject temp = new JSONObject();

            temp.put("LOS", (Engine.monster.playerVisible())? "true" : "false"); //LOS
            temp.put("DISTANCE", (Engine.monster.canShoot())? "true" : "false");//Distance within shooting range
            temp.put("MONSTER_STATE", Engine.monster.getMonster_state().name());

            jsonArray.add(temp);

            updateTime = app.millis();
            numOfData++;
        }
    }

    public void writeJsonToFile(){

        try (FileWriter file = new FileWriter("C:/Users/mohz2/Desktop/GradSchool/Semester4/Building Game AI/HWs/HW3/data2.json")){
            jsonObject.put("DATA",jsonArray);
            file.write(jsonObject.toJSONString());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
