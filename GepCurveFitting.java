import GEP.*;

class GepCurveFitting{
    public static void main(String args[]){
        System.out.println("Test class \"Operator\":");
        Operator adds=new Operator(2,0);
        System.out.println(""+adds.getSign());
        System.out.println(""+adds.rule(2,5));
        
        System.out.println("Test class \"Element\":");
        Element element=new Element(1);
        System.out.println(""+element.getValue());
        System.out.println(""+element.getType());
        
        System.out.println("Test class \"Chromosome\":");
        Operator add=new Operator(2,0);
        Operator mul=new Operator(2,1);
        Operator div=new Operator(2,2);
        
        Chromosome chromosome;
    }
}