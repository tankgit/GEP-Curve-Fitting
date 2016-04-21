package GEP;

import Tools.*;
import com.sun.corba.se.impl.orb.ParserTable;

import java.io.File;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class RunGEP {

    public Vector<Data> trainingDataSet =new Vector<>();

    public Vector<Data> testDataSet= new Vector<>();

    public Population population=new Population();

    private int NumberOfEvolveIteration;

    private int NumberOfConstantIteration;

    public RunGEP()
    {
        this.NumberOfEvolveIteration=0;
        this.NumberOfConstantIteration=0;
    }

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

        for(this.NumberOfConstantIteration=0;this.NumberOfConstantIteration<Setting.IterationOfConstant;this.NumberOfConstantIteration++)
        {
            population.evolveConstant();
            CalcuFitnessForTrainingDataSet();
        }
        population.sort();
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
        int iteration=Setting.MaxIterationOfEvolve;
        for(;this.NumberOfEvolveIteration<iteration;this.NumberOfEvolveIteration++)
        {
            population.nextGeneration();
            CalcuFitnessForTrainingDataSet();
        }
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
