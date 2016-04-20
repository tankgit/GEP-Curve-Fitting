package GEP;
/**
 * Created by tank on 4/19/16.
 */
public class Operator{


    //TODO: What to
    public class OperatorBase{
        private int numOperand;
        private char sign;
        private int type;
    }

    public class Addition{

    }

    public class Multiply{}

    public class Division{}

    //TODO: redefine the operator, implement numOperand binding with type.

    //number of operands
    private int numOperand;

    //sign of operator
    private char sign;

    //type of operator
    private int type;

    public Operator(int numOperand,int type)
    {
        this.numOperand=numOperand;
        this.type=type;
        try{
            setSign(this.type);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }        
    }

    private void setSign(int type) throws Exception
    {
        switch(type)
        {
            case 0: this.sign='+';
            return;
            case 1: this.sign='*';
            return;
            case 2: this.sign='/';
            return;
            default: 
            throw new Exception("No such type of operator!");

        }
    }
    
    public float rule(float ope1,float ope2)
    {
        switch(type)
        {
            case 0: return Add(ope1,ope2);
            case 1: return Mul(ope1,ope2);
            case 2: return Div(ope1,ope2);
        }
        return 0;
    }
    
    private float Add(float ope1,float ope2)
    {
        return ope1+ope2;
    }
    
    private float Mul(float ope1,float ope2)
    {
        return ope1*ope2;
    }
    
    private float Div(float ope1,float ope2)
    {
        if(ope2==0)return Float.MAX_VALUE;
        return ope1/ope2;
    }

    public int getType()
    {
        return this.type;
    }
    
    public char getSign()
    {
        return this.sign;
    }
    

    
	
}
