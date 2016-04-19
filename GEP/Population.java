package GEP;

import Tools.*;
import sun.font.CreatedFontTracker;

import java.util.Vector;

/**
 * Created by tank on 4/19/16.
 */
public class Population {

    public Vector<Chromosome> chromosomes=new Vector<>(Setting.NumberOfChromosome);

    public int tail;

    public int head;

    public int sizeOfTree;

    public int numberOfParent;

    private boolean sort=false;

    public Population()
    {
        this.numberOfParent=chromosomes.size()-(chromosomes.size()*Setting.MutationRatio/100);
        head=Setting.HeadSize;

        //TODO: need to be redefined globally after finishing operator operands settings.
        int MaxNumOfOperands=2;
        tail=head*(MaxNumOfOperands-1)+1;
        sizeOfTree=head+tail;
    }

    public void firstGeneration()
    {
        for(Chromosome chromosome:this.chromosomes)
            Tools.CreateRandomChromosome(chromosome,head,tail);
    }


}
