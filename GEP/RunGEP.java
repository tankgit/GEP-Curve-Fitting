package GEP;

import Tools.*;

import java.io.File;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class RunGEP {

    public Vector<Data> trainingDataSet =new Vector<>();

    public Vector<Data> testDataSet= new Vector<>();

    public Population population=new Population();

    public float bestFitness=Float.MAX_VALUE;

    public int stablePeriod;

    public int unstablePeriod;

    public boolean period;


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

        //TODO: Need to optimize: sometimes the fitness can't converge to a little value.
        if(Setting.ForceFitness==0)
            evolveGeneration();
        else
            while(Setting.ForceFitness<this.bestFitness)
                evolveGeneration();

        evolveConstant();

        smoothConstant();
    }

    private void smoothConstant()
    {
        for(int i=0;i<population.chromosomes.size();i++)
        {
            Chromosome c=new Chromosome();
            for(int j=0;j<population.chromosomes.get(i).chromosome.size();j++)
            {
                Element e=population.chromosomes.get(i).chromosome.get(j);
                if(e.getType()==Type.CONSTANT)
                {
                    float f=(float)e.getValue();
                    float a=(int)(f*10.0f)/10.0f;
                    if (f - a < 0.025) f = a;
                    else if (f - a > 0.075) f = a + 0.1f;
                    else f = a + 0.05f;
                    c.chromosome.add(new Element(f));
                }else c.chromosome.add(e);
            }
            c.Initialize();
            for(Data d:trainingDataSet)
                c.CalcuTrainingFitness(d);
            if(c.trainingFitness<population.chromosomes.get(i).trainingFitness)
                population.chromosomes.set(i,c);
        }
        for(int i=0;i<population.chromosomes.size();i++)
        {
            Chromosome c = new Chromosome();
            for (int j = 0; j < population.chromosomes.get(i).chromosome.size(); j++) {
                Element e = population.chromosomes.get(i).chromosome.get(j);
                c.chromosome.add(e);
                c.Initialize();
            }
            for (int k = 0; k < c.chromosome.size(); k++) {
                if (c.chromosome.get(k).getType() == Type.CONSTANT) {
                    float f = (float) c.chromosome.get(k).getValue();
                    float a = (int) (f * 10.0f) / 10.0f;
                    if (f - a < 0.025) f = a;
                    else if (f - a > 0.075) f = a + 0.1f;
                    else f = a + 0.05f;
                    c.chromosome.set(k, new Element(f));
                    c.Initialize();
                    for (Data d : trainingDataSet)
                        c.CalcuTrainingFitness(d);
                    if (c.trainingFitness < population.chromosomes.get(i).trainingFitness)
                        population.chromosomes.set(i, c);
                }

            }


        }
        population.sort();
    }


    public void createFirstGeneration()
    {
        population.firstGeneration();
        CalcuFitnessForTrainingDataSet();
        population.sort();
    }

    public void evolveGeneration()
    {
        this.stablePeriod=0;
        this.unstablePeriod=Integer.MAX_VALUE;
        this.period=false;
        int progress=100;
        for(int i=0;i<Setting.MaxIterationOfEvolve;i++)
        {
            bestFitness=population.chromosomes.get(0).trainingFitness;
            if(bestFitness==0.0||bestFitness<Setting.ForceFitness)return;
            if((int)(100*(float)i/Setting.MaxIterationOfEvolve)!=progress)
            {
                progress=(int)(100*(float)i/Setting.MaxIterationOfEvolve);
                Display.displayProgressBar("Evolve",progress+1,bestFitness,0);
            }

            population.nextGeneration();

            CalcuFitnessForTrainingDataSet();

            mutatePeriod(this.period);
        }
        System.out.println();
        population.sort();
    }

    public void evolveConstant()
    {
        int progress=100;
        for(int i=0;i<Setting.IterationOfConstant;i++)
        {
            bestFitness=population.chromosomes.get(0).trainingFitness;
            if(bestFitness==0.0)return;
            if((int)(100*(float)i/Setting.IterationOfConstant)!=progress)
            {
                progress=(int)(100*(float)i/Setting.IterationOfConstant);
                Display.displayProgressBar("Improve",progress+1,population.chromosomes.get(0).trainingFitness,1);
            }
            population.evolveConstant();
            CalcuFitnessForTrainingDataSet();
        }
        System.out.println();
        population.sort();
    }

    public void mutatePeriod(boolean period)
    {
        if(!period)
        {
            if (bestFitness == population.chromosomes.get(0).trainingFitness)
                this.stablePeriod++;
            else this.stablePeriod = 0;

            if(this.stablePeriod==Setting.MaxStablePeriodRatio*Setting.MaxIterationOfEvolve && unstablePeriod!=0)
            {
                this.unstablePeriod=(int)(this.stablePeriod* Tools.randomNumber(0.f,1.0f));
                this.period=true;
                this.stablePeriod=0;
            }
        }else {
            this.unstablePeriod--;
            if(this.unstablePeriod==0)
                this.period=false;
        }
    }

    private void CalcuFitnessForTrainingDataSet()
    {
        if(this.trainingDataSet.size()>0)
        {
            for(Chromosome chromosome:population.chromosomes)
            {
                chromosome.trainingFitness=0;
                for(Data data:this.trainingDataSet)
                {
                    chromosome.CalcuTrainingFitness(data);
                }
                chromosome.trainingFitness /=this.trainingDataSet.size();
            }
        }
    }

    public void CalcuFitnessForTestDataSet()
    {
        if(this.testDataSet.size()>0)
        {
            for(Chromosome chromosome:population.chromosomes)
            {
                chromosome.trainingFitness=0;
                for(Data data:this.testDataSet)
                {
                    chromosome.CalcuTestFitness(data);
                }
                chromosome.testFitness /=this.testDataSet.size();
            }
        }
    }

}
