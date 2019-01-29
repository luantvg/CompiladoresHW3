import java.util.*;

public class MiListener extends PostfijoBaseListener {

    Stack<Float> pila = new Stack<>();
    HashMap<String,Float> variables = new HashMap();
    @Override public void exitProg(PostfijoParser.ProgContext ctx){
        if(pila.size() != 0)
        {
            System.out.println("resultado: " + pila.pop());
        }
    }
    @Override public void exitFactor(PostfijoParser.FactorContext ctx)
    {
        if(ctx.getChildCount() == 1)
        {
            if(variables.containsKey(ctx.getText()))
            {
                System.out.println(ctx.getText() + " tiene el valor de: " + variables.get(ctx.getText()));
                return;
            }
            else
            {
                System.out.println("No tiene la llave de ctx: " + ctx);
            }
            Float i = new Float(ctx.getText());
            pila.push(i);
        }
    }
    @Override public void exitExpr(PostfijoParser.ExprContext ctx) {
        if(ctx.getChildCount() == 3) {
            System.out.println("3 hijos");
            if(ctx.getChild(1).getText().equals("="))
            {
                Float a  = pila.pop();
                variables.put(ctx.getChild(0).getText(),a);
                System.out.println(ctx.getChild(0).getText() + " asociado a: " + variables.get(ctx.getChild(0).getText()));
                pila.push(a);
                return;
            }
            Float b = pila.pop();
            Float a = pila.pop();
            if(ctx.getChild(1).getText().equals("+"))
            {
                Float c = a+b;
                pila.push(c);
            }
            if(ctx.getChild(1).getText().equals("-"))
            {
                Float c = a- b;
                pila.push(c);
            }
        }
    }
    @Override public void exitTerm(PostfijoParser.TermContext ctx)
    {
        if(ctx.getChildCount() == 3)
        {
            Float b = pila.pop();
            Float a = pila.pop();
            if(ctx.getChild(1).getText().equals("*"))
            {
                Float c = a*b;
                pila.push(c);
            }
            if(ctx.getChild(1).getText().equals("/"))
            {
                Float c = a / b;
                pila.push(c);
            }
        }
    }
}