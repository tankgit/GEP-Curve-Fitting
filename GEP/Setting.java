package GEP;

import Tools.ParseCSV;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class Setting {

    public static int NumberOfVariables;

    public static int MaxIterationOfEvolve=2000;

    public static int IterationOfConstant=1000;

    public static int NumberOfChromosome=200;

    public static int HeadSize=10;

    public static float MutationRatio=0.3f;

    public static float DropOutRatio=0.75f;

    public static float ConstantMax=1000.f;

    public static float ConstantMin=-1000.f;

    public static float ConstantStep=0.01f;

    public static Vector<Operator> operators=new Vector<>();

    public Setting()
    {
        operators.add(new Operator("+"));
        operators.add(new Operator("*"));
        operators.add(new Operator("/"));
    }

    public Setting(String path)
    {
        List<String[]> dataList= ParseCSV.readCSV(new File(path));
        if(dataList!=null&&!dataList.isEmpty())
        {
            int i=0;
            MaxIterationOfEvolve= Integer.parseInt(dataList.get(i++)[1]);
            IterationOfConstant= Integer.parseInt(dataList.get(i++)[1]);
            NumberOfChromosome = Integer.parseInt(dataList.get(i++)[1]);
            HeadSize=Integer.parseInt(dataList.get(i++)[1]);
            MutationRatio =Float.parseFloat(dataList.get(i++)[1]);
            DropOutRatio =Float.parseFloat(dataList.get(i++)[1]);
            ConstantMax=Float.parseFloat(dataList.get(i++)[1]);
            ConstantMin= Float.parseFloat(dataList.get(i++)[1]);
            ConstantStep=Float.parseFloat(dataList.get(i++)[1]);
            for(String ope:dataList.get(i))
            {
                if(!ope.equals("Operators"))
                operators.add(new Operator(ope));
            }

        }
    }


}
