package GEP;

import Tools.Tools;

import java.util.Vector;
/**
 * Created by tank on 4/19/16.
 */
public class Chromosome{
    
    public Vector<Element> chromosome=new Vector<Element>();
    
    public float fitness;
    
    public GeneNode root;
    
    public Chromosome()
    {
        this.fitness=0;
        this.root=null;
    }
    
    public Chromosome(Vector<Element> chromosome)
    {
        Initialize();
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
        this.fitness=0;
        this.root= Tools.CreateTree(this.chromosome);
    }
    
    public void CalcuFitness(Data data)
    {
        this.fitness+=Tools.CalcuFitnessFromData(data,this.root);
        //System.out.println(""+this.fitness);

        //TODO: fitness may hava weird value, set to maximum(float);
    }
}