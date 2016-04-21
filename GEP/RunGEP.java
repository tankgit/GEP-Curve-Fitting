package GEP;

import Tools.*;

import java.io.File;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class RunGEP {

    public Vector<Data> trainingDataSet =new Vector<>();

    public Vector<Data> testDataSet= new Vector<>();

    public Population population=new Population();


    public void loadDatas(String trainingDataPath, String testDataPath)
    {
        Vector<String[]> trainingList = ParseCSV.readCSV(new File(trainingDataPath));
        Vector<String[]> testList = ParseCSV.readCSV(new File(testDataPath));
        if(testList.get(0).length!=trainingList.get(0).length);
        for(String[] data: trainingList)
            this.trainingDataSet.add(new Data(data));
        for(String[] data: testList)
            this.testDataSet.add(new Data(data));
        Setting.NumberOfVariables= trainingList.get(0).length-1;
    }

    public void run()
    {
        createFirstGeneration();

        evolveGeneration();

        evolveConstant();

        CalcuFitnessForTestDataSet();
    }

    public void createFirstGeneration()
    {
        population.firstGeneration();
        CalcuFitnessForTrainingDataSet();
        population.sort();
    }

    public void evolveGeneration()
    {
        int progress=100;
        for(int i=0;i<Setting.MaxIterationOfEvolve;i++)
        {
            if((int)(100*(float)i/Setting.MaxIterationOfEvolve)!=progress)
            {
                progress=(int)(100*(float)i/Setting.MaxIterationOfEvolve);
                Display.displayProgressBar("Evolve",progress+1,0);
            }
            population.nextGeneration();
            CalcuFitnessForTrainingDataSet();
        }
        System.out.println();
        population.sort();
    }

    public void evolveConstant()
    {
        int progress=100;
        for(int i=0;i<Setting.IterationOfConstant;i++)
        {
            if((int)(100*(float)i/Setting.IterationOfConstant)!=progress)
            {
                progress=(int)(100*(float)i/Setting.IterationOfConstant);
                Display.displayProgressBar("Improve",progress+1,1);
            }
            population.evolveConstant();
            CalcuFitnessForTrainingDataSet();
        }
        System.out.println();
        population.sort();
    }

    private void CalcuFitnessForTrainingDataSet()
    {
        if(this.trainingDataSet.size()>0)
        {
            for(Chromosome chromosome:population.chromosomes)
            {
                for(Data data:this.trainingDataSet)
                {
                    chromosome.CalcuTrainingFitness(data);
                }
                chromosome.trainingFitness /=this.trainingDataSet.size();
            }
        }
    }

    private void CalcuFitnessForTestDataSet()
    {
        if(this.testDataSet.size()>0)
        {
            for(Chromosome chromosome:population.chromosomes)
            {
                for(Data data:this.testDataSet)
                {
                    chromosome.CalcuTestFitness(data);
                }
                chromosome.testFitness /=this.testDataSet.size();
            }
        }
    }

}
