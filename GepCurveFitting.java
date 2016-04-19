import GEP.*;
import Tools.*;

import java.io.File;

/**
 * Created by tank on 4/19/16.
 */
class GepCurveFitting{
    public static void main(String args[])
    {
        baseTest();
//        String dataPath=args[1];
        new Setting("src/Settings.CSV");

        RunGEP runGEP=new RunGEP();

        runGEP.loadDatas("src/Data.CSV");

        runGEP.run();
    }

    private static void baseTest()
    {
        Operator add=new Operator(2,0);
        Operator mul=new Operator(2,1);
        Operator div=new Operator(2,2);

        Chromosome chromosome=new Chromosome();
        chromosome.Add(new Element(add));
        chromosome.Add(new Element(mul));
        chromosome.Add(new Element(div));
        chromosome.Add(new Element(2.f));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(5.f));
        chromosome.Add(new Element(0));
        chromosome.Initialize();
        Display.displayChromosome(chromosome);

        Data data=new Data();
        data.x.add(2.f);
        data.fx=6.5f;
        float fitness= Tools.CalcuFitnessFromData(data,chromosome.root);

        System.out.println("fitness="+fitness);
    }
}