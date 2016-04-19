package GEP;

import Tools.ParseCSV;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class Setting {

    public static int NumberOfVariables;

    public static int MaxIterationOfEvolve;

    public static int IterationOfConstant;

    public static int NumberOfChromosome;

    public static int HeadSize;

    public static int SwapRatio;

    public static int MutationRatio;

    public static float ConstantMax;

    public static float ConstantMin;

    public static float ConstantStep;

    public static Vector<Operator> operators=new Vector<>();

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
            SwapRatio=Integer.parseInt(dataList.get(i++)[1]);
            MutationRatio=Integer.parseInt(dataList.get(i++)[1]);
            ConstantMax=Float.parseFloat(dataList.get(i++)[1]);
            ConstantMin= Float.parseFloat(dataList.get(i++)[1]);
            ConstantStep=Float.parseFloat(dataList.get(i++)[1]);
            for(String ope:dataList.get(i))
            {
                System.out.println();
                if(!ope.equals("Operators"))
                operators.add(new Operator(2,Integer.parseInt(ope)));
            }

        }
    }


}
