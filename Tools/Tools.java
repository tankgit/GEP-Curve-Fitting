package Tools;
import GEP.*;

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

    public static void CreateRandomChromosome(Chromosome chromosome,int head,int tail)
    {
        chromosome.Clear();
        int sizeOfTree=head+tail;
        for(int i=0;i<sizeOfTree;i++)
        {
            boolean inHead=(i<head);
            chromosome.Add(CreateRandomElement(inHead));
        }
        chromosome.Initialize();
    }

    public static Element CreateRandomElement(boolean inHead)
    {
        Type type=RandomType((int)random()*10);
        if(!inHead&&type==Type.OPERATOR)
            type=Type.CONSTANT;
        switch(type){
            case CONSTANT: return new Element(random()*(Setting.ConstantMax-Setting.ConstantMin)+Setting.ConstantMin);
            case VARIABLE: return new Element((int)random()*Setting.NumberOfVariables);
            case OPERATOR: return new Element((int)random()*Setting.operators.size());
            default: return null;
        }
    }

    public static Type RandomType(int seed)
    {
        switch(seed%3+1){
            case 1:return Type.VARIABLE;
            case 2:return Type.CONSTANT;
            case 3:return Type.OPERATOR;
            default: return Type.NULL;
        }
    }
}