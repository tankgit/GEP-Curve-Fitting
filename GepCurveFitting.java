import GEP.*;
import Tools.*;

import java.util.Date;
import java.util.Vector;


/**
 * Created by tank on 4/19/16.
 */
class GepCurveFitting{
    public static void main(String[] args)
    {
        //baseTest();
        RunGEP runGEP=new RunGEP();

        int i=0;
        if(args.length==0||args.length>4)
            man();
        if(args[0].equals("-s")||args[0].equals("--setting"))
            if(args.length<=1)
                man();
            else {
                new Setting(args[1]);
                i=2;
            }
        else {
            new Setting();
            System.out.println("No input settings file, set as default.");
        }
        System.out.println("Setting finished!");

        String train=null,test=null;
        if(args.length==i||(args.length>=3&&i==0))
        {
            man();
        }
        else{
            train=args[i];
            i++;
            if(args.length-1==i)
                test=args[i];
            else {
                test=train;
                System.out.println("No input test data, set as training data.");
            }
        }
        runGEP.loadDatas(train,test);

        System.out.println("Data loaded!");


        runGEP.run();

        Result(runGEP);

        //baseTest(runGEP.trainingDataSet);

    }

    private static void man()
    {
        System.out.println("Usage:\n\t.. [-s SettingFile] TrainingFile [TestFile]\n\tTip:\n\t-Set as default without [-r]\n\t-Set as TrainingFile without [TestFile]");
        System.exit(0);
    }

    private static void Result(RunGEP runGEP)
    {
        System.out.println("\n\033[01;34m[ Gene Expression Programming Result ]\033[0m\n");
        Display.displayChromosome(runGEP.population.chromosomes.get(0));
        Display.displayExpression(runGEP.population.chromosomes.get(0));
        Display.displayMathExpression(runGEP.population.chromosomes.get(0));
        System.out.println("Training Fitness = "+runGEP.population.chromosomes.get(0).trainingFitness);
        System.out.println();
        System.out.println("Test Fitness = "+runGEP.population.chromosomes.get(0).testFitness);
        System.out.println();

    }

    private static void baseTest(Vector<Data> datas)
    {
        System.out.println("Chromosome Test:");
        Operator add=new Operator("+");
        Operator mul=new Operator("*");
        Operator div=new Operator("/");

        Chromosome chromosome=new Chromosome();
        chromosome.Add(new Element(mul));
        chromosome.Add(new Element(add));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(add));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(div));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(mul));
        chromosome.Add(new Element(add));
        chromosome.Add(new Element(1));
        chromosome.Add(new Element(1));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(0));
        chromosome.Initialize();

        System.out.println();
        Display.displayExpression(chromosome);

        /*Data d=new Data();
        d.fx=9.f;
        d.x.add(3.f);
        System.out.println();
        System.out.println("Fitness = "+Tools.CalcuFitnessFromData(d,chromosome.root));
*/
        for(Data data:datas)
        {
            chromosome.CalcuTrainingFitness(data);
        }
        System.out.println(chromosome.trainingFitness/datas.size());
    }
}