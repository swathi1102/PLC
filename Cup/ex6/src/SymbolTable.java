package src;

import java.util.HashMap;

public class SymbolTable extends HashMap<String,Object>{

    static SymbolTable globalTable;
    
    static {globalTable = new SymbolTable();}
    
    //var or proc
    static void setValue(String id,HashMap<String,Object> value){
       
        HashMap<String,Object> v = (HashMap<String,Object>) globalTable.get(id);
        if(v == null){
            globalTable.put(id, value);
        }else {
         globalTable.replace(id, value);   
        }
    }
    
    static void setValue(String id,Integer value){
        globalTable.put(id, value);
    }
    
    static void setValue(String id,Float value){
        globalTable.put(id, value);
    }
    
    static void setValue(String id,Boolean value){
        globalTable.put(id, value);
    }
    
    static HashMap<String,Object> getValue(String id){
      
      HashMap<String,Object> ht = (HashMap<String,Object>)globalTable.get(id);
      
      return ht;
    }
}