/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author jaelse
 */
public class Var {
    String type;
    Integer iVal = null;
    Float fVal = null;
    Boolean bVal = null;
    Lstat funcPara = null;
    Astat funcState = null;
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setVal(int iVal){
        this.iVal = iVal;
    }
    public void setVal(float fVal){
        this.fVal = fVal;
    }
    public void setVal(Boolean bVal){
        this.bVal = bVal;
    }
    public void setVal(Lstat funcpara,Astat funcState){
        this.funcPara = funcpara;
        this.funcState = funcState;
    }
    
}
