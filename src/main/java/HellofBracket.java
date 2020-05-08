import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
public class HellofBracket {

    String constructPostfix(String exp) throws JSONException { //(((0)-(1))+((2)-(3))+(4)+((5))+((6)-(7)-(8))+((9)))

        // initializing empty String for result
        //  String result = new String("");
        JSONObject jo = new JSONObject();
        Map rootExp = new LinkedHashMap();
        //Stack<JSONArray> resultSbeStack=new Stack<>();

        Stack<String> operandStack = new Stack<>();
        Stack<Map> expStack = new Stack<>();
        Stack<Map> expResultStack = new Stack<>();
        Stack<JSONArray> sbeStack = new Stack<>();
        Stack sbeResultStack = new Stack<>();
        Stack<JSONObject> sbeExpResultStack = new Stack<>();
        Map expArray = null;

        Map expObj = null;
        JSONArray sbe = null;

        // initializing empty stack
        Stack<String> operatorStack = new Stack<>();
        /* try {*/

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            // If the scanned character is an operand, add it to output.
            if (Character.isLetterOrDigit(c)) {
                String str = String.valueOf(c);

                while (Character.isLetterOrDigit(exp.charAt(i + 1)) || exp.charAt(i + 1) == '_') {
                    char nextChar = exp.charAt(i + 1);
                    str += nextChar;
                    i++;
                }
                operandStack.push(str);
                //  result += c;
            }

            // If the scanned character is an '(', push it to the stack.
            else if (c == '(') {

                expObj = new LinkedHashMap();
                expStack.push(expObj);
                operatorStack.push(String.valueOf(c));
            }

            //  If the scanned character is an ')', pop and output from the stack
            // until an '(' is encountered.
            else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek().charAt(0) != '(') {
                    String operator = operatorStack.pop();
                    //   result += operator;
                    expObj = expStack.pop();

                    if (operator.equals("=") || operator.equals("!=") || operator.equals("<=") || operator.equals(">=") || operator.equals("<") || operator.equals(">")) {
                        expObj.put("typ", getOperator(operator));
                        expObj.put("vst", operandStack.pop());
                        expObj.put("vtp", "str");
                        expObj.put("key", operandStack.pop());
                        expArray = new LinkedHashMap(4);
                        expArray.put("exp", expObj);

                        expResultStack.push(expArray);
                    } else if (isOperator(operator)) {
                        sbe = sbeStack.pop();
                        while (!expResultStack.isEmpty()) {
                            sbe.put(expResultStack.pop());
                        }
                        while (!sbeResultStack.isEmpty()) {
                            sbe.put(sbeResultStack.pop());
                        }
                        if (sbeStack.isEmpty()) {
                            while (!sbeExpResultStack.isEmpty()) {
                                sbe.put(sbeExpResultStack.pop());
                            }

                        }
                        rootExp.put("typ", getOperation(operator));
                        rootExp.put("sbe", sbe);
                        jo = new JSONObject();
                        jo.put("exp", rootExp);


                        if (sbeStack.isEmpty()){
                            sbeExpResultStack.push(jo);
                        }
                        else{
                            sbeResultStack.push(jo);
                        }
                        /*rootExp.put("typ",getOperation(String.valueOf(operator)));
                        rootExp.put("sbe", sbe);
                        jo.put("exp",rootExp);*/

                        // System.out.println(jo.toString());
                    }

                }

                if (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    return "Invalid Expression"; // invalid expression
                } else {
                    operatorStack.pop();
                }
            } else if (c == '=') {
                operatorStack.push(String.valueOf(c));
            } else if (c == '!') {

                String str = String.valueOf(c);

                while (!Character.isLetterOrDigit(exp.charAt(i + 1))) {
                    char nextChar = exp.charAt(i + 1);
                    str += nextChar;
                    i++;
                }
                operatorStack.push(str);

            } else if (c == '<') {

                String str = String.valueOf(c);

                while (!Character.isLetterOrDigit(exp.charAt(i + 1))) {
                    char nextChar = exp.charAt(i + 1);
                    str += nextChar;
                    i++;
                }
                operatorStack.push(str);

            } else if (c == '>') {

                String str = String.valueOf(c);

                while (!Character.isLetterOrDigit(exp.charAt(i + 1))) {
                    char nextChar = exp.charAt(i + 1);
                    str += nextChar;
                    i++;
                }
                operatorStack.push(str);

            } else // an operator is encountered
            {

                if (!operatorStack.isEmpty()) {
                    if (!operatorStack.peek().equals(String.valueOf(c))) {
                        operatorStack.push(String.valueOf(c));
                        sbe = new JSONArray();
                        while (!expResultStack.isEmpty()) {
                            sbe.put(expResultStack.pop());

                        }
                        while (!sbeResultStack.isEmpty()) {
                            sbe.put(sbeResultStack.pop());

                        }

                        sbeStack.push(sbe);


                    } else {
                        sbe = sbeStack.peek();
                        while (!expResultStack.isEmpty()) {
                            sbe.put(expResultStack.pop());
                        }
                        while (!sbeResultStack.isEmpty()) {
                            sbe.put(sbeResultStack.pop());

                        }

                    }

                }
            }

        }
        // pop all the operators from the stack
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().equals("(")) {
                return "Invalid Expression";
            } else {
                if (isOperator(operatorStack.pop())) {
                    sbe = sbeStack.pop();
                    while (!expResultStack.isEmpty()) {
                        //  expObj=expResultStack.pop();
                        sbe.put(expResultStack.pop());
                        sbeResultStack.push(sbe);
                    }
                    while (!sbeResultStack.isEmpty()) {
                        sbe.put(sbeResultStack.pop());
                        sbeResultStack.push(sbe);
                    }

                }
            }
        }

        System.out.println("Postfix expression is" + jo);

        /* }*/
       /* catch (EmptyStackException e)
        {
            System.out.println(exp);
        }*/
        return jo.toString();
    }

    boolean isOperator(String c) {
        if (c.equals("+") || c.equals("-"))
        {
            return true;
        }
        return false;
    }

    private String getOperation(String c) {
        String operation = "or";
        if (c.equals("+")) {
            operation = "and";
            // System.out.println("Operation is" + chrArray[startIndex + 1]);
        }
        return operation;
    }

    private String getOperator(String c){

        String operation = "eq";

        switch(c){
            case "!=":
                return "neq";
            case "<=":
                return "lte";
            case ">=":
                return "gte";
            case "<":
                return "lt";
            case ">":
                return "gt";
            case "=":
                return "eq";
        }

        return operation;
    }
    static int Prec(char ch) {
        switch (ch) {
            case '+':
            case '=':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    public static void main(String args[]) throws FileNotFoundException, JSONException {

        HellofBracket et = new HellofBracket();
        try
        {
            /*FileInputStream file = new FileInputStream(new File("C:\\SearchTerms_used.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                if(row.getRowNum()==0)
                    continue;
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
               // String postfix=row.getCell(0).toString(); */
          //  String postfix= "((((ABC=GP))AND((GRP=AX))AND((AC=CDG)OR(AC=OY)OR(AC=BA)OR(AC=PAR)OR(AC=PA)OR(AC=FR)OR(AC=IS)OR(AC=PA))AND((EC=NC)OR(EC=ER)OR(EC=JK)OR(EC=LGA)OR(EC=CD)OR(EC=AR)OR(EC=AC)OR(EC=JF)OR(EC=NW)))OR(((SB=AB))AND((PCC=FU))AND((GRP=AX))AND((AC=CDG)OR(AC=OY)OR(AC=BA)OR(AC=PAR)OR(AC=PA)OR(AC=FR)OR(AC=IS)OR(AC=PA))AND((EC=NC)OR(EC=ER)OR(EC=JK)OR(EC=LGA)OR(EC=CD)OR(EC=AR)OR(EC=AC)OR(EC=JF)OR(EC=NW))))";
              String postfix= "(((ABC=GP))AND((GRP=AX))";
            postfix = postfix.replace(")AND(", ")+(");
            postfix = postfix.replace(")OR(", ")-(");
            postfix=postfix.replaceAll("\\s+","");
            postfix="("+postfix+")";
            System.out.println(" expression is" + postfix);
            char[] charArray = postfix.toCharArray();
            String str = et.constructPostfix(postfix);
            System.out.println(str);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*String postfix="((VY=NO)OR(VY=5))AND((ABC=NO)OR(ABC=PK))AND((DATE>=20150422)AND(DATE<=20150531))AND(HT=1S)AND((AC=LX)OR(AC=SA)OR(AC=BW)OR(AC=DA)OR(AC=DF)OR(AC=ER)OR(AC=FL)OR(AC=JK)OR(AC=LX)OR(AC=LGA)OR(AC=MC)OR(AC=NO)OR(AC=NC)OR(AC=MS)OR(AC=NC)OR(AC=ORD)OR(AC=PL)OR(AC=SAN)OR(AC=SF)OR(AC=SJC)OR(AC=SA))";
        postfix = postfix.replace(")AND(", ")+(");
        postfix = postfix.replace(")OR(", ")-(");
        postfix=postfix.replaceAll("\\s+","");
        postfix="("+postfix+")";
        System.out.println(" expression is" + postfix);
        char[] charArray = postfix.toCharArray();
        String str = et.constructPostfix(postfix);*/
    }
}
