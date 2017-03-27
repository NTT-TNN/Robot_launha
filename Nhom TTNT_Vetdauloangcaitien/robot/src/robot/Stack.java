/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author ifletyougo
 */
public class Stack {
    int[][] a;
    int top;
    public Stack(){
        a = new int[10000][2];
        top = -1;
    }
    public void push(int i,int j){
        top++;
        a[top][0] = i;
        a[top][1] = j;
    }
    public void pop(){
        top--;
    }
    
    public boolean check(int k){
        if(k>top)
            return(false);
        else
            return(true);
    }
    
    public boolean isEmpty(){
        if(top==-1) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void empty(){
        top = -1;
    }
    
    public void display(){
        int i;
        for(i=0;i<=top;i++){
            System.out.println(a[i][0]+"-"+a[i][1]+" ");
        }
        System.out.println();        
    }
}
