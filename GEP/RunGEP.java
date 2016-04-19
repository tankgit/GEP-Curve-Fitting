package GEP;

import Tools.ParseCSV;
import kotlin.reflect.jvm.internal.impl.renderer.KeywordStringsGenerated;

import java.io.File;
import java.util.Set;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class RunGEP {

    public Vector<Data> datas=new Vector<>();

    public Population population=new Population();

    private int NumberOfEvolveIteration;

    private int NumberOfConstantIteration;

    public RunGEP()
    {
        this.NumberOfEvolveIteration=0;
        this.NumberOfConstantIteration=0;
    }

    public void loadDatas(String dataPath)
    {
        Vector<String[]> dataList= ParseCSV.readCSV(new File(dataPath));
        for(String[] data:dataList)
            this.datas.add(new Data(data));
        Setting.NumberOfVariables=dataList.get(0).length-1;
    }

    public void run()
    {
        CreateFirstGeneration();

    }

    public void CreateFirstGeneration()
    {
        population.firstGeneration();
        
    }
}
