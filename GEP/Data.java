package GEP;

import java.util.Vector;
/**
 * Created by tank on 4/19/16.
 */
public class Data{
    public Vector<Float> x=new Vector<>();
    public float fx;

    public Data(){}

    public Data(String[] s){
        this.fx=Float.parseFloat(s[0]);
        int len=s.length;
        for(int i=1;i<len;i++)
            this.x.add(Float.parseFloat(s[i]));
    }
}