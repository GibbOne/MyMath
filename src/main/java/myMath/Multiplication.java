package myMath;

public class Multiplication {
    int factor1;
    int factor2;
    int result;
    
    public Multiplication(int factor1, int factor2)
    {
        this.factor1 = factor1;
        this.factor2 = factor2;    
        this.result = factor1 * factor2;
    }

    public String GetQuestion()
    {
        return factor1 + "x" + factor2 + "= ?";
    }

    public String GetResult()
    {
        return factor1 + "x" + factor2 + "=" + result;
    }

    public Boolean CheckResult(int possibleResult)
    {
        return (result == possibleResult);
    }
}
