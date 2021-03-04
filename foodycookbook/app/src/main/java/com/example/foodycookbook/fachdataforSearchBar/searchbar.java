package com.example.foodycookbook.fachdataforSearchBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class searchbar {
    List<String> list;
    public searchbar(List<String> list){
        Collections.sort(list);
        this.list=list;
    }public int search(String str,int l,int u){
        int mid=l+(u-l)/2;
        if(u<l) return -1;
        if(list.get(mid).substring(0,3).equals(str)) return mid;
        if (grater(list.get(mid),str)) return search(str,mid+1, u);
        return search( str,l, mid-1);
    }public boolean grater(String str,String tar){
        String[] arr=new String[2];
        arr[0]=str;
        arr[1]=tar;
        Arrays.sort(arr);
        return str.equals(arr[0]);
    }public List<String> findlist(String tar){
        ArrayList<String> res=new ArrayList<>();
        int t=search(tar,0, list.size());
        int s=t,e=t;
        if(t>-1){
            res.add(list.get(t));
            while(--s>=0&&list.get(s).substring(0,3).equals(tar)&&res.size()<2) res.add(list.get(s));
            while(++e<list.size()&&list.get(e).substring(0,3).equals(tar)&&res.size()<2) res.add(list.get(e));
            return res;
        }
        return res;
    }public HashSet<String> filter(String target){
        HashSet<String> ress=new HashSet<>();
        int t=search(target,0, list.size());
        int s=t,e=t;
        if(t>-1){
            ress.add(list.get(t));
            while(--s>=0&&list.get(s).substring(0,target.length()).equals(target)&&ress.size()<2) ress.add(list.get(s));
            while(++e<list.size()&&list.get(e).substring(0, target.length()).equals(target)&&ress.size()<2) ress.add(list.get(e));
            return ress;
        }
        return  ress;
    }
}