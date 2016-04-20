package Tools;
import GEP.*;

import javax.sound.midi.SysexMessage;
import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 * Created by tank on 4/19/16.
 */
public class Tools{
    
    public static GeneNode CreateTree(Vector<Element> chromosome)
    {
        if(chromosome.size()>0)
        {
            GeneNode root=new GeneNode(chromosome.elementAt(0));
            Queue<GeneNode> opeQueue=new LinkedList<>();
            if(chromosome.elementAt(0).getType()== Type.OPERATOR)
            {
                opeQueue.add(root);
                for(int i=1;i<chromosome.size()&&opeQueue.size()>0;i++)
                {
                    GeneNode next=new GeneNode(chromosome.elementAt(i));
                    if(next.element.getType()==Type.OPERATOR)
                    {
                        opeQueue.add(next);
                    }
                    
                    if(opeQueue.peek().left==null)
                    {
                        opeQueue.peek().left=next;
                    }
                    else
                    {
                        opeQueue.peek().right=next;
                        opeQueue.poll();
                    }
                }
            }
             return root;
        }
        else
        {
             return null;
        }
    }
    
    public static float CalcuFitnessFromData(Data data, GeneNode root)
    {
        return abs(data.fx-Calcul(data,root));
    }
    
    private static float Calcul(Data data,GeneNode root)
    {
        Element e=root.element;
        switch(e.getType())
        {
            case CONSTANT:
                return (float)e.getValue();
            case VARIABLE:
                return data.x.elementAt((int)e.getValue());
            case OPERATOR:
            {
                float ope1=Calcul(data,root.left);
                float ope2=Calcul(data,root.right);
                Operator ope=(Operator)e.getValue();
                return ope.rule(ope1,ope2);
            }
        }
        return 0;
    }

    public static Chromosome randomChromosome(int head, int tail)
    {
        Chromosome chromosome=new Chromosome();
        chromosome.Clear();
        int sizeOfTree=head+tail;
        for(int i=0;i<sizeOfTree;i++)
        {
            boolean inHead=(i<head);
            chromosome.Add(randomElement(inHead));
        }
        chromosome.Initialize();
        return chromosome;
    }

    public static Element randomElement(boolean inHead)
    {
        Type type= randomType((int)randomNumber(0,3));
        if(!inHead&&type==Type.OPERATOR)
            type=Type.CONSTANT;
        switch(type){
            case CONSTANT: return new Element(randomNumber(Setting.ConstantMin,Setting.ConstantMax));
            case VARIABLE: return new Element(randomVariable());
            case OPERATOR: return new Element(new Operator(2,(int)randomNumber(0,Setting.operators.size())));
            default: System.out.println("Element get NULL type!");return null;
        }
    }

    public static Type randomType(int seed)
    {
        switch(seed%3+1){
            case 1:return Type.VARIABLE;
            case 2:return Type.CONSTANT;
            case 3:return Type.OPERATOR;
            default: System.out.println("Type get NULL type!"); return Type.NULL;
        }
    }

    // random(int): [min,max-1]
    public static int randomVariable()
    {
        return (int)randomNumber(0,Setting.NumberOfVariables);
    }

    // random(float): [min,max)
    public static float randomNumber(float min,float max)
    {
        return (float)(random()*(max-min)+min);
    }

    public static void refineConstant(Element e)
    {
        if(e.getType()==Type.CONSTANT)
        {
            float constant = (float) e.getValue() + Setting.ConstantStep * (int) randomNumber(-1, 1);
            e.setConstant(constant);
        }
        else {
            System.out.println("Constant refining error.");
            System.exit(0);
        }
    }


    public static void sortByFitness(Vector<Chromosome> chromosomes,int left,int right)
    {
        float key=chromosomes.get((left+right)/2).fitness;
        int i=left;
        int j=right;

        while(i<j)
        {
            while(chromosomes.get(i).fitness<key)i++;
            while(chromosomes.get(j).fitness>key)j--;
            if(i<=j)
            {
                Chromosome tem=chromosomes.get(i);
                chromosomes.set(i++,chromosomes.get(j));
                chromosomes.set(j--,tem);
            }
        }
        if(j<right)sortByFitness(chromosomes,left,j);
        if(i>left)sortByFitness(chromosomes,i,right);
    }
}