package Tools;



import GEP.*;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Created by tank on 4/19/16.
 */
public class Display {


    private static void printElement(Element element)
    {
        switch(element.getType()){
            case CONSTANT:
                System.out.print(""+element.getValue());
                break;
            case VARIABLE:
                System.out.print("X"+element.getValue());
                break;
            case OPERATOR:
                Operator ope=(Operator)element.getValue();
                System.out.print(""+ope.getSign());
                break;
        }
    }

    public static void displayExpression(Chromosome chromosome)
    {
        System.out.println("Expression:");
        Queue<GeneNode> q=new LinkedList<>();
        q.add(chromosome.root);
        while(q.size()>0)
        {
            if(q.peek().left!=null)
            {
                q.add(q.peek().left);
            }
            if(q.peek().right!=null)
            {
                q.add(q.peek().right);
            }
            printElement(q.peek().element);
            System.out.print("|");
            q.poll();
        }
        System.out.println();
    }

    public static void displayChromosome(Chromosome chromosome)
    {
        System.out.println("Chromosome:");
        for(Element e:chromosome.chromosome)
        {
            printElement(e);
            System.out.print("|");
        }
        System.out.println();
    }

}
