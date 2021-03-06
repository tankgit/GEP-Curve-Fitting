package Tools;
import GEP.*;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 * Created by tank on 4/19/16.
 */
public class Tools{
    //TODO: refine the tree from bi-tree to multi-tree
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
        float r=abs(data.fx-Calcul(data,root));
        return r<0.0001f?0:r;
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
                float[] operands=new float[]{ope1,ope2};
                return ope.rule(operands);
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
            case OPERATOR: return new Element(randomOperator());
            default: System.out.println("Element get NULL type!");return null;
        }
    }

    private static Type randomType(int seed)
    {
        switch(seed%3){
            case 0:return Type.VARIABLE;
            case 1:return Type.CONSTANT;
            case 2:return Type.OPERATOR;
            default: System.out.println("Type get NULL type!"); return Type.NULL;
        }
    }

    public static float randomNumber(float min,float max)
    {
        return (float)(random()*(max-min)+min);
    }
    // random(int): [min,max-1]
    //TODO: random() need to be changed since its return value is not clear.
    private static int randomVariable()
    {
        return (int)randomNumber(0,Setting.NumberOfVariables-0.01f);
    }

    private static Operator randomOperator()
    {
        return Setting.operators.get((int)randomNumber(0,Setting.operators.size()-0.01f));
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
        float key=chromosomes.get((left+right)/2).trainingFitness;
        int i=left;
        int j=right;

        while(i<j)
        {
            while(chromosomes.get(i).trainingFitness <key)i++;
            while(chromosomes.get(j).trainingFitness >key)j--;
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