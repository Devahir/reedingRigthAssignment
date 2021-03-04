package com.example.foodycookbook.fachdataforSearchBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class searchbyFirstChar {
    HashMap<String,List<String>> firstcharList;
    public searchbyFirstChar(){
        firstcharList=new HashMap<>();
    }public void addList(List<String> l,String s){
        if(!firstcharList.containsKey(s)){
            Collections.sort(l);
            firstcharList.put(s,l);
        }
    }public List<String> filterbyname(String target){
        List<String> checklist=new ArrayList<>();
        if(firstcharList.containsKey(target.substring(0,1))){
            return findlist(target,firstcharList.get(target.substring(0,1)));
        }
        return checklist;
    }public int search(List<String> lista,String str,int l,int u) {
        int mid = l + (u - l) / 2;
        if (u < l) return -1;
        if (lista.get(mid).substring(0, 3).equals(str)) return mid;
        if (grater(lista.get(mid), str)) return search(lista, str, mid + 1, u);
        return search(lista,str, l, mid - 1);
    }public boolean grater(String str,String tar){
        String[] arr=new String[2];
        arr[0]=str;
        arr[1]=tar;
        Arrays.sort(arr);
        return str.equals(arr[0]);
    }public List<String> findlist(String tar,List<String> list){
        ArrayList<String> res=new ArrayList<>();
        int t=search(list,tar,0, list.size());
        int s=t,e=t;
        if(t>-1){
            res.add(list.get(t));
            while(--s>=0&&list.get(s).substring(0,3).equals(tar)&&res.size()<2) res.add(list.get(s));
            while(++e<list.size()&&list.get(e).substring(0,3).equals(tar)&&res.size()<2) res.add(list.get(e));
            return res;
        }
        return res;
    }public boolean containskey(String ss){
        return firstcharList.containsKey(ss);
    }
}
