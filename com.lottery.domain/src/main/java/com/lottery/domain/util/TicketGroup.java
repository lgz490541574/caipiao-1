package com.lottery.domain.util;

import com.lottery.domain.TicketInfo;

import java.util.*;

/**
 * 分组算法
 */
public class TicketGroup {
    private Stack<String> stack;

    private List<List<String>> group;
    public List<List<String>> doAnalysis(String[] items,int size){
        stack = new Stack<>();
        group=new ArrayList<>();
        doData(items,size,0,0);
        return group;
    }


    public static void main(String[] args) {
        TicketGroup ticket=new TicketGroup();
        List<List<String>> lists = ticket.doAnalysis("1,2,3,4,5,6,7,8,9".split(","), 5);
        System.out.println(lists);
    }

    public void doAnalysis(List<String[]> datas){
        stack = new Stack<>();
        group=new ArrayList<>();
    }

    /**
     * 执行运算
     * @param items
     * @param targ
     * @param has
     * @param cur
     */
    private void doData(String[] items,int targ,int has,int cur){
        if(has==targ){
            group.add(Arrays.asList(stack.toArray(new String[stack.size()])));
            return;
        }
        for(int i=cur;i<items.length;i++) {
            if(!stack.contains(items[i])) {
                stack.add(items[i]);
                doData(items, targ, has+1, i);
                stack.pop();
            }
        }
    }
}
