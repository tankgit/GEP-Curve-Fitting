package GEP;

import Tools.ParseCSV;

import java.io.File;
import java.util.List;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class Setting {

    //private int NumberOfVariables;

    public int MaxIterationOfEvolve;

    public int IterationOfConstant;

    public int NumberOfChromosome;

    public int HeadSize;

    public int SwapRatio;

    public int MutationRatio;

    public float ConstantMax;

    public float ConstantMin;

    public float ConstantStep;

    public Vector<Operator> operators=new Vector<>();

    public Setting(String path)
    {
        List<String[]> dataList= ParseCSV.readCSV(new File(path));
        if(dataList!=null&&!dataList.isEmpty())
        {
            int i=0;
            this.MaxIterationOfEvolve= Integer.parseInt(dataList.get(i++)[1]);
            this.IterationOfConstant= Integer.parseInt(dataList.get(i++)[1]);
            this.NumberOfChromosome = Integer.parseInt(dataList.get(i++)[1]);
            this.HeadSize=Integer.parseInt(dataList.get(i++)[1]);
            this.SwapRatio=Integer.parseInt(dataList.get(i++)[1]);
            this.MutationRatio=Integer.parseInt(dataList.get(i++)[1]);
            this.ConstantMax=Float.parseFloat(dataList.get(i++)[1]);
            this.ConstantMin= Float.parseFloat(dataList.get(i++)[1]);
            this.ConstantStep=Float.parseFloat(dataList.get(i++)[1]);
            for(String ope:dataList.get(i))
            {
                System.out.println();
                if(!ope.equals("Operators"))
                this.operators.add(new Operator(2,Integer.parseInt(ope)));
            }

        }
    }


}
