package sqindia.net.oonbux;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Salman on 6/3/2016.
 */
public class GlobalDatas extends Application {

    public ArrayList<String> country_Datas;
    public ArrayList<String> state_Datas;
    public ArrayList<String> zip_Datas;


    public ArrayList<String> getCountry_Datas() {
        return country_Datas;
    }

    public void setCountry_Datas(ArrayList<String> country_Datas) {
        this.country_Datas = country_Datas;
    }

    public ArrayList<String> getState_Datas() {
        return state_Datas;
    }

    public void setState_Datas(ArrayList<String> state_Datas) {
        this.state_Datas = state_Datas;
    }

    public ArrayList<String> getZip_Datas() {
        return zip_Datas;
    }

    public void setZip_Datas(ArrayList<String> zip_Datas) {
        this.zip_Datas = zip_Datas;
    }
}
