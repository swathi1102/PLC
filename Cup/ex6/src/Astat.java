package src;

import java.util.HashMap;

public class Astat {

    int statementType;
    public static int assignment = 0;
    public static int varDeclaration = 1;
    public static int varDeclarationAndType = 2;
    public static int varDeclarationAndTypeAndValue = 3;
    public static int print = 4;
    public static int ifthen = 5;
    public static int block = 6;
    public static int whileloop = 7;
    public static int function = 8;
    public static int returnStatement = 9;
    public static int forloop = 10;
    public static int param = 11;
    public static int echo = 12;
    public static int newline = 13;
    /*
     * assignment statement: variable = expr
     *
     */
    String assVariable;
    Aexp assExpr = null;
    String assType;
    
    public static Astat makeId(String id){
        Astat statement = new Astat();
        statement.assVariable = id;
        
        return statement;
    }
        Lstat asslist;
    public static Astat declaration(Lstat variable, String type , Aexp expr){
       
        Astat statement = new Astat();
        statement.statementType = varDeclarationAndTypeAndValue;
        statement.asslist = variable;
        statement.assExpr = expr;
        statement.assType = type;
        //System.out.println(statement.assExpr.getexp());
        return statement;
    }
    public static Astat declaration(Lstat variable, String type){
        Astat statement = new Astat();
        statement.statementType = varDeclarationAndType;
        statement.assType = type;
        statement.asslist = variable;
        
        return statement;
    }
    
    public static Astat declaration(String id,String type){
        Astat statement = new Astat();
        statement.statementType = varDeclarationAndType;
        statement.assVariable = id;
        statement.assType = type;
        
        return statement;
    }
       Astat assState = null;
    public static Astat makeFunc(String id,Lstat para,Astat state ){
        Astat statement = new Astat();
        
        statement.assVariable = id;        
        statement.asslist = para;
        statement.assState = state;
        
        HashMap<String,Object> func = new HashMap();
        func.put("func", statement);
        
        SymbolTable.setValue(id,func);
        
        return statement;
    }
    
    public static Astat param(String id,String type){
        Astat statement = new Astat();
        statement.statementType = param;
        statement.assVariable = id;
        
        statement.assType = type;
        
        return statement;
    }
    
    public static Astat makeFunc(String id,Astat state ){
        Astat statement = new Astat();
        
        statement.assVariable = id;
        
        statement.assState = state;
        
        HashMap<String,Object> func = new HashMap();
        func.put("func", statement);
        
        SymbolTable.setValue(id,func);
        
        return statement;
    }
    
    
    public static Astat assignment(String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = assignment;

        statement.assVariable = Variable;
        statement.assExpr = expr;

        return statement;

    }
    
    public static Astat setarg(Aexp e){
        Astat statement = new Astat();
        statement.assExpr = e;
        
        return statement;
    }
    
    Lstat assArgs;
    
    public static Astat callFunction(String id,Lstat args){
        
        HashMap<String,Object> func = SymbolTable.getValue(id);
        
        if(func.isEmpty()){
            System.out.println("function not found");
            System.exit(0);
        }
        
        Astat statement = null;
        
        if(func.entrySet().iterator().next().getKey().equals("func")){
            statement = (Astat)func.get("func");
            statement.statementType = function;
            
            statement.asslist.execute();
            statement.assArgs = args;
        }
        
        if(statement == null){
           System.out.println("calling problem");
           System.exit(0);
       }
        
        return statement;
    }
    public static Astat callFunction(String id){
        HashMap<String,Object> func = SymbolTable.getValue(id);
        
        if(func.isEmpty()){
            System.out.println("function not found");
            System.exit(0);
        }
                
        Astat statement = null;
        
         if(func.entrySet().iterator().next().getKey().equals("func")){
            statement = (Astat)func.get("func");
            
            if(!(statement.asslist == null)){
                System.out.println("args mismatch");
                System.exit(0);
            }   
        }else{
            System.out.println("function not declared");
            System.exit(0);
        }
       if(statement == null){
           System.out.println("calling problem");
           System.exit(0);
       }
        return statement;
    }
    /*
     * while loop: while whileCondition begin whileBody end
     *
     */
    Aexp whileCondition;
    Astat whileBody;

    public static Astat whileloop(Aexp condition, Astat WhileBody) {
        Astat statement = new Astat();
        statement.statementType = whileloop;
        statement.whileCondition = condition;
        statement.whileBody = WhileBody;
        
        return statement;

    }
    
  
    Aexp forCondition;
    Astat forDeclaration,forUpdate;
    Astat ForBody;
    public static Astat forloop(Astat declaration, Aexp condition,Astat update, Astat forbody){
        Astat statement = new Astat();
        statement.statementType = forloop;
        statement.forDeclaration=declaration;
        statement.forUpdate = update;
        statement.forCondition = condition;
        statement.ForBody = forbody;
        
        return statement;
    }
    
    /*
     * if then statement: if ifcondition then ifbody
     *
     */
    Aexp ifcondition;
    Astat ifbody;
    Astat elsebody;
    
    public static Astat ifthen(Aexp Condition, Astat Ifbody, Astat elsebody) {
        Astat statement = new Astat();
        statement.statementType = ifthen;
        statement.ifcondition = Condition;
        statement.ifbody = Ifbody;
        statement.elsebody = elsebody;
        
        return statement;
    }
    
    public static Astat ifthen(Aexp Condition, Astat Ifbody) {
        Astat statement = new Astat();
        statement.statementType = ifthen;
        statement.ifcondition = Condition;
        statement.ifbody = Ifbody;
        statement.elsebody = null;
        
        return statement;
    }

    /*
     * print statement: print e
     */
    Aexp printE;

    public static Astat print(Aexp expr) {

        Astat statement = new Astat();
        statement.statementType = print;
        statement.printE = expr;
        return statement;

    }
    
       Aexp echoE;

    public static Astat echo(Aexp expr) {

        Astat statement = new Astat();
        statement.statementType = echo;
        statement.echoE = expr;
        return statement;

    }
    public static Astat newline(){
        Astat statement = new Astat();
        statement.statementType = newline;
        return statement;
    }

    /*
     * block statement: begin statement_list end
     */
    Lstat blockBody;

    public static Astat block(Lstat l) {
        Astat statement = new Astat();
        statement.statementType = block;
        statement.blockBody = l;
        
        return statement;
    }

    public String getstat() {
        if (statementType == assignment) {
            return assVariable + "=" + assExpr.getexp();
        } else if (statementType == ifthen) {
            return "if " + ifcondition.getexp() + " " + ifbody.getstat();
        } 
        else if (statementType == echo) {
         
            return "echo " + echoE.getexp();
            
        }
        else if (statementType == print) {
            return "print " + printE.getexp();
        } else if (statementType == whileloop) {
            return "while " + whileCondition.getexp() + whileBody.getstat();
        } else if (statementType == block) {
            return "block";
        } else {
            return "unknown";
        }
    }

    public void execute() {
        if (statementType == assignment) {
            HashMap<String,Object> ht1;
            ht1 = SymbolTable.getValue(assVariable);
            HashMap<String,Object> ht2 = assExpr.getValue();
            
            String ht1t = ht1.entrySet().iterator().next().getKey();
            String ht2t = ht2.entrySet().iterator().next().getKey();
            
            switch(ht1t){
                case "int":
                    if(ht2t.equals("int")){
                        SymbolTable.setValue(assVariable,ht2);
                    }
                   break;
                case "float":
                    if(ht2t.equals("int") | ht2t.equals("float")){
                        SymbolTable.setValue(assVariable,ht2);
                    }
                    break;
                case "bool":
                    if(ht2t.equals("bool")){
                        SymbolTable.setValue(assVariable, ht2);
                    }
                    break;
          
                default:
                    System.out.println("Type mismatch");
                    System.exit(0);
                    break;
            }
        } 
        else if(statementType == newline){
            System.out.println("");
        } 
        else if(statementType == varDeclarationAndType){
            for(Astat s: asslist.statementList)
            {   
                HashMap<String,Object> ht = new HashMap();
                ht.put(assType, null);
                
                SymbolTable.setValue(s.assVariable,ht);
            }
        }else if(statementType == varDeclarationAndTypeAndValue){
           
            for(Astat s:asslist.statementList){
                HashMap<String,Object> st = new HashMap();
                switch(assExpr.getValue().entrySet().iterator().next().getKey()){
                    case "int":
                        if(assType.equals("int") || assType.equals("float")){
                            
                            int x = (Integer)assExpr.getValue().get("int");
                            st.put(assType, x);
                            
                            SymbolTable.setValue(s.assVariable,st);
                            
                        }else{
                            System.out.println("Cannot assign "+ assExpr +"to" +s.assVariable);
                            System.exit(0);
                        }
                        break;
                    case "float":
                         if(assType.equals("float")){
                            st.put(assType, assExpr.getValue().get("float"));
                            SymbolTable.setValue(s.assVariable,st);
                        }else{
                            System.out.println("Cannot assign to "+ s.assVariable);
                            System.exit(0);
                        }
                        break;
                    case "bool":
                        if(assType.equals("bool")){
                            
                            st.put(assType, assExpr.getValue().get("bool"));
                            SymbolTable.setValue(s.assVariable,st);
                        }else{
                            System.out.println("Cannot assign "+ assExpr +"to" +s.assVariable);
                            System.exit(0);
                        }
                        break;
                    default:
                        
                        break;
                }
            }
        }else if(statementType == function){
            
            if(asslist.statementList.size() == assArgs.statementList.size()){
                int size = asslist.statementList.size();
                
                for(int i =0 ;i<size;i++){
                    Astat s1 = asslist.statementList.get(i);
                    Astat s2 = assArgs.statementList.get(i);
                    
                    if(s1.assType == s2.assExpr.getValue().entrySet().iterator().next().getKey()){
                        switch(s1.assType){
                            case "int":
                                asslist.statementList.get(i).assExpr = new Aexp((Integer)assArgs.statementList.get(i).assExpr.getValue().get("int"));
                                asslist.statementList.get(i).statementType = assignment;
                                break;
                            case "float":
                                asslist.statementList.get(i).assExpr = new Aexp((Float)assArgs.statementList.get(i).assExpr.getValue().get("float"));
                                asslist.statementList.get(i).statementType = assignment;
                                break;
                            case "bool":
                                asslist.statementList.get(i).assExpr = new Aexp((Boolean)assArgs.statementList.get(i).assExpr.getValue().get("bool"));
                                asslist.statementList.get(i).statementType = assignment;
                                break;
                            default:
                                System.out.println("some stupid problem on assigning args to para");
                                System.exit(0);
                                break;
                        }
                    }
                }
            }else {
                 System.out.println("Args mismatch");
                 System.exit(0);
             }
            
            if(asslist != null){
                for(Astat dec:asslist.statementList){
                    dec.execute();
                }
            }
            assState.execute();
        }else if(statementType == param){
            
           HashMap<String,Object> ht = new HashMap();
           ht.put(assType, null);
                
           SymbolTable.setValue(assVariable,ht);
            
        }else if (statementType == ifthen) {
                
            if (ifcondition.getValue().get("bool") == java.lang.Boolean.TRUE) {
                ifbody.execute();
            }else{
                if(elsebody != null)
                    elsebody.execute();
            }

        } else if (statementType == whileloop) {
            for (;;) {
                if (whileCondition.getValue().get("bool") == java.lang.Boolean.TRUE) {
                    whileBody.execute();
                } else {
                    break;
                }
            }
        } 
        else if (statementType == forloop) {
            forDeclaration.execute();
            for (;;) {
                
                if (forCondition.getValue().get("bool") == java.lang.Boolean.TRUE) {
                    ForBody.execute();
                    forUpdate.execute();
                } else {
                    break;
                }
               
            }
            
        }else if (statementType == print) {
            HashMap<String,Object> ht;
            
            ht = printE.getValue();
            
            if(ht.get("int") != null){
                System.out.print(ht.get("int"));
            }else if(ht.get("float") != null){
                System.out.print(ht.get("float"));
            }else if(ht.get("bool") != null){
                System.out.print(ht.get("bool"));
            }
            

        } 
        else if (statementType == echo) {
            HashMap<String,Object> ht;
            
            ht = echoE.getValue();
           
            if(ht.get("int") != null){
                System.out.print(ht.get("int"));
            }else if(ht.get("float") != null){
                System.out.print(ht.get("float"));
            }else if(ht.get("bool") != null){
                System.out.print(ht.get("bool"));
            }else if(ht.get("newline")!= null){
                System.out.print(ht.get("newline"));
            }
            

        }else if (statementType == block) {
            for (Astat s : blockBody.statementList) {
                s.execute();
            }
        }
    }
}
