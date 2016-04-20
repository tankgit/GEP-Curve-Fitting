import GEP.*;
import Tools.*;



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

        Display.displayExpression(runGEP.population.chromosomes.get(0));
        System.out.println("\nFitness = "+runGEP.population.chromosomes.get(0).fitness);
        System.out.println();

        System.out.println("Finished");
    }

    private static void baseTest()
    {
        System.out.println("Chromosome Test:");
        Operator add=new Operator(2,0);
        Operator mul=new Operator(2,1);

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