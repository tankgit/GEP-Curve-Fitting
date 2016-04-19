package GEP;
/**
 * Created by tank on 4/19/16.
 */
public class Element{
    
    private Type type;
    private Object value;
    
    
    public Element(Object value)
    {
        try{
            this.value=value;
            switch(value.getClass().getName())
            {
                case "java.lang.Integer":
                    this.type=Type.VARIABLE;
                    break;
                case "java.lang.Float":
                    this.type=Type.CONSTANT;
                    break;
                case "GEP.Operator":
                    this.type=Type.OPERATOR;
                    break; 
                default:
                    throw new Exception("No such type of element!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public Type getType()
    {
        return this.type;
    }
    
    public Object getValue()
    {
        return this.value;
    }
}