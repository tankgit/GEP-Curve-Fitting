package GEP;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Queue;

import static java.lang.Math.abs;
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
            if(chromosome.elementAt(0).getType()==Type.OPERATOR)
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
    
    public static float CalcuFitnessFromData(Data data,GeneNode root)
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
}