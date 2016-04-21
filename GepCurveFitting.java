import GEP.*;
import Tools.*;



/**
 * Created by tank on 4/19/16.
 */
class GepCurveFitting{
    public static void main(String[] args)
    {
        //baseTest();

//      *  String dataPath=args[1];
        //TODO: set a default setting while no input file.
        new Setting("src/Settings.CSV");
        RunGEP runGEP=new RunGEP();
        runGEP.loadDatas("src/ApB.csv","src/ApB.csv");
        runGEP.run();

        Display.displayChromosome(runGEP.population.chromosomes.get(0));
        Display.displayExpression(runGEP.population.chromosomes.get(0));
        Display.displayMathExpression(runGEP.population.chromosomes.get(0));
        System.out.println("Training Fitness = "+runGEP.population.chromosomes.get(0).trainingFitness);
        System.out.println();
        System.out.println("Test Fitness = "+runGEP.population.chromosomes.get(0).testFitness);
        System.out.println();

        System.out.println("Finished");
    }

    private static void baseTest()
    {
        System.out.println("Chromosome Test:");
        Operator add=new Operator(Operator.operatorType.ADDITION);
        Operator mul=new Operator(Operator.operatorType.MULTIPLY);

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
        Display.displayExpression(chromosome);

        Data d=new Data();
        d.fx=9.f;
        d.x.add(3.f);

        System.out.println();
        System.out.println("Fitness = "+Tools.CalcuFitnessFromData(d,chromosome.root));

    }
}