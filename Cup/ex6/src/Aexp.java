package src;

import java.util.HashMap;
import java.util.Objects;

public class Aexp {

    private enum AexpType {
        INTEGER,
        FLOAT,
        BOOLEAN,
        ID,
        EXP,NEWLINE
    }
    
    private final AexpType eType;
    private Integer inum = null;
    private Float fnum = null;
    private Boolean bnum = null;
    private String id;
    private Args operands;
    private int operator;

    Aexp(Integer x) {
        eType = AexpType.INTEGER;
        inum = x;        
    }
    Aexp(Float x){
        eType = AexpType.FLOAT;
        fnum = x;
    }
    Aexp(Boolean x){
        eType = AexpType.BOOLEAN;
        bnum = x;
    }
    
    Aexp(String x) {
        eType = AexpType.ID;
        id = x;
    }

    Aexp(Args x, int op) {
        eType = AexpType.EXP;
      
        operands = x;
        operator = op;
        
    }
    
    
    public String getexp() {

        String s = "";
        switch (this.eType) {
            case INTEGER: s = "" + inum; break;
           
            case ID: s = id; break;
           
            case EXP:
                switch (operator) {
                    case sym.PLUS:
                        s = "PLUS(" +operands.getfi().getexp() + "," +operands.getse().getexp() + ")";
                        break;
                    case sym.MINUS:
                        s = "MINUS(" +operands.getfi().getexp() + "," +operands.getse().getexp() + ")";
                        break;
                    case sym.TIMES:
                        s = "TIMES(" +operands.getfi().getexp() + "," +operands.getse().getexp() + ")";
                        break;
                    case sym.DIVIDE:
                        s = "DIVIDE(" +operands.getfi().getexp() + "," +operands.getse().getexp() + ")";
                        break;
                    default: break;
                } break;
            default: break;
        }

        return s;
    }

    public HashMap<String,Object> getValue() {
        HashMap<String,Object> val = new SymbolTable();
        
        switch (this.eType) {
            case INTEGER:
                // expression is a number
                val.put("int", inum); break;
            case BOOLEAN:
                // expression is a number
                val.put("bool", bnum); break;
            case FLOAT:
                // expression is a number
                val.put("float",fnum); break;
            case ID:
                //expression is a variable
                
                HashMap<String,Object> ht = SymbolTable.getValue(id);
                
                if(ht != null){
                val = ht;
                    
                        switch(val.entrySet().iterator().next().getKey()){
                        case "int":
                            if(val.get("int") == null){
                                System.out.println(id + " was not initialized");
                                System.exit(0);
                            }
                        break;
                        case "float":
                            if(val.get("float") == null){
                                System.out.println(id + " was not initialized");
                                System.exit(0);
                            }
                        break;
                        case "bool":
                            if(val.get("bool") == null){
                                System.out.println(id + " was not initialized");
                                System.exit(0);
                            }
                            break;
                        }
  
                }else{
                        System.out.println(id+ " was not Declared");
                        System.exit(0);           
                }
                break;
            case EXP:
                Aexp xE = null;
                Aexp yE = null;
                
                //expression is a math expresi
                HashMap<String,Object> fi; 
                HashMap<String,Object> se;
                
                if(operands.getfi().eType == AexpType.ID){
                    fi = SymbolTable.getValue(operands.getfi().id);
                    
                    switch(fi.entrySet().iterator().next().getKey()){
                        case "int":
                            Integer x = (Integer)fi.get("int");
                            xE = new Aexp(x);
                            
                            break;
                        case "float":
                            Float y = (Float)fi.get("float");
                            xE = new Aexp(y);
                                                        
                            break;
                        case "bool":
                            
                            Boolean z = (Boolean)fi.get("bool");
                            xE = new Aexp(z);
                            
                            break;
                    }
                }else if(operands.getfi().eType == AexpType.EXP){
                    HashMap<String,Object> hm;
                    
                    hm = operands.getfi().getValue();
                    
                    switch(hm.entrySet().iterator().next().getKey()){
                        case "int":
                            Integer x = (Integer)hm.get("int");
                            xE = new Aexp(x);
                            
                            break;
                        case "float":
                            Float y = (Float)hm.get("float");
                            xE = new Aexp(y);
                            
                            
                            break;
                        case "bool":
                            
                            Boolean z = (Boolean)hm.get("bool");
                            xE = new Aexp(z);
                            
                            break;
                    }
                }else if(operands.getfi().eType == AexpType.BOOLEAN){
                        xE = new Aexp(operands.getfi().bnum);
                }else if(operands.getfi().eType == AexpType.INTEGER){
                        xE = new Aexp(operands.getfi().inum);
                }else if(operands.getfi().eType == AexpType.FLOAT){
                        xE = new Aexp(operands.getfi().fnum);
                }
                 if(operands.getse() != null){   
                    if(operands.getse().eType == AexpType.ID){
                        se = SymbolTable.getValue(operands.getse().id);

                        switch(se.entrySet().iterator().next().getKey()){
                            case "int":
                                Integer x = (Integer)se.get("int");
                                yE = new Aexp(x);

                                break;
                            case "float":
                                Float y = (Float)se.get("float");
                                yE = new Aexp(y);


                                break;
                            case "bool":

                                Boolean z = (Boolean)se.get("bool");
                                yE = new Aexp(z);

                                break;
                        }
                    }else if(operands.getse().eType == AexpType.EXP){
                    HashMap<String,Object> hm;

                        hm = operands.getse().getValue();

                        switch(hm.entrySet().iterator().next().getKey()){
                            case "int":
                                Integer x = (Integer)hm.get("int");
                                yE = new Aexp(x);

                                break;
                            case "float":
                                Float y = (Float)hm.get("float");
                                yE = new Aexp(y);


                                break;
                            case "bool":

                                Boolean z = (Boolean)hm.get("bool");
                                yE = new Aexp(z);

                                break;
                        }
                    }else if(operands.getse().eType == AexpType.BOOLEAN){
                            yE = new Aexp(operands.getse().bnum);
                    }else if(operands.getse().eType == AexpType.INTEGER){
                            yE = new Aexp(operands.getse().inum);
                    }else if(operands.getse().eType == AexpType.FLOAT){
                            yE = new Aexp(operands.getse().fnum);
                    }
                }
                
                switch (operator) {
                    case sym.PLUS:
                        
                        if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.INTEGER){
                            
                            int f = xE.inum;
                            int s = yE.inum;
                           
                            val.put("int", s+f);
                        }else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.FLOAT){
                            
                            float f = (Float)xE.fnum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f+s);
                        }
                        else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.INTEGER){
                            
                            float f = (Float)xE.fnum;
                            float s = (Integer)yE.inum;
                            
                            val.put("float", f+s);
                        }else if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.FLOAT){
                            
                            float f = (Integer)xE.inum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f+s);
                        }
                        break;
                    case sym.MINUS:
                        //val =xE.getValue(1) -yE.getValue(1);
                        if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.INTEGER){
                            int f = (Integer)xE.inum;
                            int s = (Integer)yE.inum;
                            
                            val.put("int", s-f);
                        }else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.FLOAT){
                            
                            float f = (Float)xE.fnum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f-s);
                        }
                        else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.INTEGER){
                            
                            float f = (Float)xE.fnum;
                            float s = (Integer)yE.inum;
                            
                            val.put("float", f-s);
                        }else if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.FLOAT){
                            
                            float f = (Integer)xE.inum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f-s);
                        }
                        break;
                    case sym.TIMES:
                        //val =xE.getValue() *yE.getValue();
                        if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.INTEGER){
                            int f = (Integer)xE.inum;
                            int s = (Integer)yE.inum;
                            
                            val.put("int", s*f);
                        }else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.FLOAT){
                            
                            float f = (Float)xE.fnum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f*s);
                        }
                        else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.INTEGER){
                            
                            float f = (Float)xE.fnum;
                            float s = (Integer)yE.inum;
                            
                            val.put("float", f*s);
                        }else if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.FLOAT){                           
                            float f = (Integer)xE.inum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f*s);
                        }
                        break;
                    case sym.DIVIDE:
                        //val =xE.getValue(1) /yE.getValue(1);
                        if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.INTEGER){
                            
                            int f = (Integer)xE.inum;
                            int s = (Integer)yE.inum;
                            
                            val.put("int", s/f);
                        }else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.FLOAT){
                            
                            float f = (Float)xE.fnum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f/s);
                        }
                        else if(xE.eType == AexpType.FLOAT && yE.eType == AexpType.INTEGER){
                            
                            float f = (Float)xE.fnum;
                            float s = (Integer)yE.inum;
                            
                            val.put("float", f/s);
                        }else if(xE.eType == AexpType.INTEGER && yE.eType == AexpType.FLOAT){
                            
                            float f = (Integer)xE.inum;
                            float s = (Float)yE.fnum;
                            
                            val.put("float", f/s);
                        }
                        break;
                    case sym.AND:
                        if(xE.eType == AexpType.BOOLEAN && yE.eType == AexpType.BOOLEAN){
                            Boolean f = (Boolean)xE.bnum;
                            Boolean s = (Boolean)yE.bnum;
                            
                            val.put("bool", f&&s);
                        }else{
                            System.out.println("Cannot Use And operator here");
                            System.exit(0);
                        }
                        break;
                    case sym.OR:
                        if(xE.eType == AexpType.BOOLEAN && yE.eType == AexpType.BOOLEAN){
                            Boolean f = (Boolean)xE.bnum;
                            Boolean s = (Boolean)yE.bnum;
                            
                            val.put("bool", f|s);
                        }else{
                            System.out.println("Cannot Use OR operator here");
                            System.exit(0);
                        }
                        break;
                    case sym.LT:
                        
                        if((xE.eType == AexpType.INTEGER | xE.eType == AexpType.FLOAT) && (yE.eType == AexpType.INTEGER | yE.eType == AexpType.FLOAT)){
                            Float f = null;
                            Float s = null;
                            
                            if(xE.inum != null){
                                f = ((Number)xE.inum).floatValue();
                            }else if(xE.fnum != null){
                                f = (Float)xE.fnum;
                            }
                            
                            if(yE.inum != null){
                                s = ((Number)yE.inum).floatValue();
                            }else if(yE.fnum != null){
                                s = (Float)yE.fnum;
                            }
                           
                            val.put("bool", f < s);
                            
                        }
                        break;
                        case sym.NOT:
                    if(xE.eType == AexpType.BOOLEAN){
                            Boolean f = (Boolean)xE.bnum;
                            //Boolean s = (Boolean)yE.bnum;
                            
                            val.put("bool", !f);
                        }else{
                            System.out.println("Cannot Use Not operator here");
                            System.exit(0);
                        }
                        break;
                    case sym.LTE:
                        if((xE.eType == AexpType.INTEGER | xE.eType == AexpType.FLOAT) && (yE.eType == AexpType.INTEGER |yE.eType == AexpType.FLOAT)){
                            Float f = null;
                            Float s = null;
                            
                            if(xE.inum != null){
                                f = ((Number)xE.inum).floatValue();
                            }else if(xE.fnum != null){
                                f = xE.fnum;
                            }
                            
                            if(yE.inum != null){
                                s = ((Number)yE.inum).floatValue();
                            }else if(xE.fnum != null){
                                s = yE.fnum;
                            }
                            
                            val.put("bool", f <= s);
                        }
                        break;
                    case sym.GT:
                        if((xE.eType == AexpType.INTEGER |xE.eType == AexpType.FLOAT) && (yE.eType == AexpType.INTEGER |yE.eType == AexpType.FLOAT)){
                            Float f = null;
                            Float s = null;
                            
                            if(xE.inum != null){
                                f = ((Number)xE.inum).floatValue();
                            }else if(xE.fnum != null){
                                f = xE.fnum;
                            }
                            
                            if(yE.inum != null){
                                s = ((Number)yE.inum).floatValue();
                            }else if(xE.fnum != null){
                                s = yE.fnum;
                            }
                            
                            val.put("bool", f > s);
                        }
                        break;
                    case sym.GTE:
                    if((xE.eType == AexpType.INTEGER |xE.eType == AexpType.FLOAT) && (yE.eType == AexpType.INTEGER |yE.eType == AexpType.FLOAT)){
                            Float f = null;
                            Float s = null;
                            
                            if(xE.inum != null){
                                f = ((Number)xE.inum).floatValue();
                            }else if(xE.fnum != null){
                                f = xE.fnum;
                            }
                            
                            if(yE.inum != null){
                                s = ((Number)yE.inum).floatValue();
                            }else if(xE.fnum != null){
                                s = yE.fnum;
                            }
                            
                            val.put("bool", f >= s);
                    }
                        break;
                    case sym.EQEQ:
                    if((xE.eType == AexpType.INTEGER |xE.eType == AexpType.FLOAT) && (yE.eType == AexpType.INTEGER |yE.eType == AexpType.FLOAT)){
                            Float f = null;
                            Float s = null;
                            
                            if(xE.inum != null){
                                f = ((Number)xE.inum).floatValue();
                            }else if(xE.fnum != null){
                                f = xE.fnum;
                            }
                            
                            if(yE.inum != null){
                                s = ((Number)yE.inum).floatValue();
                            }else if(xE.fnum != null){
                                s = yE.fnum;
                            }
                            
                            val.put("bool", Objects.equals(f, s));
                    }
                        break;
                    case sym.NEQ:
                    if((xE.eType == AexpType.INTEGER |xE.eType == AexpType.FLOAT) && (yE.eType == AexpType.INTEGER |yE.eType == AexpType.FLOAT)){
                            Float f = null;
                            Float s = null;
                            
                            if(xE.inum != null){
                                f = ((Number)xE.fnum).floatValue();
                            }else if(xE.fnum != null){
                                f = xE.fnum;
                            }
                            
                            if(yE.inum != null){
                                s = ((Number)yE.inum).floatValue();
                            }else if(xE.fnum != null){
                                s = yE.fnum;
                            }
                            
                            val.put("bool", !Objects.equals(f, s));
                        break;
                    }   
                    default:
                        break;
                } break;
            default: break;
        }
        return val;
    }
}
