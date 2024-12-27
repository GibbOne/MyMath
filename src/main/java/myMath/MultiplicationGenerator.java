package myMath;

import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class MultiplicationGenerator {
    
    Random rand;
    Queue<Multiplication> operationToRepeatOften;

    public MultiplicationGenerator()
    {
        rand = new Random();
        operationToRepeatOften = new LinkedList<Multiplication>();
    }

    public Multiplication NewMultiplication()
    {
        if (rand.nextBoolean() & operationToRepeatOften.size() > 0)
        {
            return operationToRepeatOften.poll();
        }
        return new Multiplication(rand.nextInt(2, 11), rand.nextInt(2, 11));
    }

    public void AddOperationToRepeatOften(Multiplication op)
    {
        operationToRepeatOften.add(op);
    }

}
