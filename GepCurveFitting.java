import GEP.*;
import Tools.*;

import java.util.Vector;


/**
 * Created by tank on 4/19/16.
 */
class GepCurveFitting{
    public static void main(String args[])
    {
        //baseTest();

//      *  String dataPath=args[1];
        //TODO: set a default setting while no input file.
        new Setting("src/Settings.CSV");



        RunGEP runGEP=new RunGEP();
        runGEP.loadDatas("src/Data.CSV");
        runGEP.run();

        Display.displayChromosome(runGEP.population.chromosomes.get(0));
        System.out.println("\nFitness = "+runGEP.population.chromosomes.get(0).fitness);
        System.out.println();

        //displayAllChromosome(runGEP.population.chromosomes);
        System.out.println("Finished");
    }

    private static void displayAllChromosome(Vector<Chromosome> chromosomes)
    {
        for(Chromosome chromosome:chromosomes)
        {
            Display.displayChromosome(chromosome);
            System.out.println("Fitness = "+chromosome.fitness);
        }
    }

    private static void baseTest()
    {
        System.out.println("Chromosome Test:");
        Operator add=new Operator(2,0);
        Operator mul=new Operator(2,1);
        Operator div=new Operator(2,2);

        Chromosome chromosome=new Chromosome();
        chromosome.Add(new Element(add));
        chromosome.Add(new Element(mul));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(mul));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(0));
        chromosome.Add(new Element(0));
        chromosome.Initialize();

        System.out.println();
        Display.displayChromosome(chromosome);

        Data d=new Data();
        d.fx=9.f;
        d.x.add(3.f);

        System.out.println();
        System.out.println("Fitness = "+Tools.CalcuFitnessFromData(d,chromosome.root));

    }
}