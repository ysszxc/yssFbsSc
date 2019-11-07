package com.qf.controller;

/**
 * @author Yss
 * @Date 2019/10/31 0031
 */
public class text {

    public static void main(String[] args) {
        int[] a = {10,1,8,26,2,22,4};
        int[] b = new int[a.length];
        int m,n;
        m=n=0;
        for(int i = 1 ;i< a.length; i++){
            if (a[i] < a[0]){
                m++;
            }else {
                n++;
            }
        }

        System.out.println(m+"--"+n);
        b[m+1] = a[0];
        int x,y;
        x=0;
        y=m+1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[0]) {
                b[++x] = a[i];
            }
            else {
                b[++y] = a[i];
            }
        }

        System.out.println(b);
    }
}
