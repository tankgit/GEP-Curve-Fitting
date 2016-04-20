package GEP;

import Tools.*;

import java.io.File;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class RunGEP {

    public Vector<Data> datas=new Vector<>();

    public Population population=new Population();

    private int NumberOfEvolveIteration;

    private int NumberOfConstantIteration;

    public RunGEP()
    {
        this.NumberOfEvolveIteration=0;
        this.NumberOfConstantIteration=0;
    }

    public void loadDatas(String dataPath)
    {
        Vector<String[]> dataList= ParseCSV.readCSV(new File(dataPath));
        for(String[] data:dataList)
            this.datas.add(new Data(data));
        Setting.NumberOfVariables=dataList.get(0).length-1;
    }

    public void run()
    {
        createFirstGeneration();

        evolveGeneration();
        //Display.displayChromosome(population.chromosomes.get(0));
        for(this.NumberOfConstantIteration=0;this.NumberOfConstantIteration<Setting.IterationOfConstant;this.NumberOfConstantIteration++)
        {
            population.evolveConstant();
            CalcuFitnessForChromosome();
        }
        population.sort();

    }

    public void createFirstGeneration()
    {
        population.firstGeneration();
        CalcuFitnessForChromosome();
        population.sort();
    }

    public void evolveGeneration()
    {
        //TODO: using Min ?
        int iteration=Setting.MaxIterationOfEvolve;

        for(;this.NumberOfEvolveIteration<iteration;this.NumberOfEvolveIteration++)
        {
            population.nextGeneration();
            CalcuFitnessForChromosome();
        }
        population.sort();
    }

    private void CalcuFitnessForChromosome()
    {
        if(this.datas.size()>0)
        {
            for(Chromosome chromosome:population.chromosomes)
            {
                for(Data data:this.datas)
                {
                    chromosome.CalcuFitness(data);
                }
                chromosome.fitness/=this.datas.size();
            }
        }
    }
}
