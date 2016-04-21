package GEP;

import Tools.Tools;

import java.util.Vector;
/**
 * Created by tank on 4/19/16.
 */
public class Chromosome{
    
    public Vector<Element> chromosome=new Vector<>();
    
    public float trainingFitness;

    public float testFitness;
    
    public GeneNode root;
    
    public Chromosome()
    {
        this.trainingFitness =0;
        this.root=null;
    }
    
    public void Add(Element element)
    {
        this.chromosome.add(element);
    }
    
    public void Clear()
    {
        this.chromosome.clear();
        this.root=null;
    }
    
    public void Initialize()
    {
        this.trainingFitness =0;
        this.root= Tools.CreateTree(this.chromosome);
    }
    
    void CalcuTrainingFitness(Data data)
    {
        this.trainingFitness +=Tools.CalcuFitnessFromData(data,this.root);

        //TODO: trainingFitness may have weird value, set to maximum(float);
    }

    void CalcuTestFitness(Data data)
    {
        this.testFitness+=Tools.CalcuFitnessFromData(data,this.root);
    }
}