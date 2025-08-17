package com.tiocloud.chat;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        getFun(1);
    }

    public static class StepBean{
        int stepId;
        int parentStepId;
        int bushu;
        int jieshu;
        int lian23xuShu;
        String bushuTree = "";
        StepBean parentStep;
        List<StepBean> childStepList = new ArrayList<>();
    }

    private static int[][] getFun(int n){
        StepBean rootStepBean = new StepBean();
        rootStepBean.stepId = 0;
        rootStepBean.parentStepId = -1;
        rootStepBean.bushu = 0;
        rootStepBean.jieshu = 0;
        rootStepBean.lian23xuShu = 0;

        for (int i = 0; i < n; i++){
            List<StepBean> stepBeanList = getStepN(rootStepBean, i);
            for (StepBean pStepBean : stepBeanList){
                if (pStepBean.jieshu == n){
                    //阶数已经满了，不需要再走了
                    System.out.println("得到方案："+pStepBean.bushuTree);
                    continue;
                }
                //-1的情况
                if (pStepBean.jieshu > 0 && pStepBean.lian23xuShu >= 2){
                    StepBean stepBean1 = new StepBean();
                    stepBean1.bushu = pStepBean.bushu + 1;
                    stepBean1.jieshu = pStepBean.jieshu - 1;
                    stepBean1.parentStepId = pStepBean.stepId;
                    stepBean1.parentStep = pStepBean;
                    stepBean1.lian23xuShu = 0;
                    stepBean1.bushuTree = pStepBean.bushuTree + ",-1";
                    pStepBean.childStepList.add(stepBean1);
                }
                //1步的情况
                StepBean stepBean1 = new StepBean();
                stepBean1.bushu = pStepBean.bushu + 1;
                stepBean1.jieshu = pStepBean.jieshu + 1;
                stepBean1.parentStepId = pStepBean.stepId;
                stepBean1.parentStep = pStepBean;
                stepBean1.lian23xuShu = 0;
                stepBean1.bushuTree = pStepBean.bushuTree + ",1";
                pStepBean.childStepList.add(stepBean1);
                //2步的情况
                if (pStepBean.jieshu + 2 <= n){
                    StepBean stepBean2 = new StepBean();
                    stepBean2.bushu = pStepBean.bushu + 1;
                    stepBean2.jieshu = pStepBean.jieshu + 2;
                    stepBean2.parentStepId = pStepBean.stepId;
                    stepBean2.parentStep = pStepBean;
                    stepBean2.lian23xuShu = pStepBean.lian23xuShu + 1;
                    stepBean2.bushuTree = pStepBean.bushuTree + ",2";
                    pStepBean.childStepList.add(stepBean2);
                }

                //2步的情况
                if (pStepBean.jieshu + 3 <= n){
                    StepBean stepBean3 = new StepBean();
                    stepBean3.bushu = pStepBean.bushu + 1;
                    stepBean3.jieshu = pStepBean.jieshu + 3;
                    stepBean3.parentStepId = pStepBean.stepId;
                    stepBean3.parentStep = pStepBean;
                    stepBean3.lian23xuShu = pStepBean.lian23xuShu + 1;
                    stepBean3.bushuTree = pStepBean.bushuTree + ",3";
                    pStepBean.childStepList.add(stepBean3);
                }
            }
        }


        return null;
    }

    /**
     * 获取第N步的所有bean
     * @return
     */
    private static List<StepBean> getStepN(StepBean rootBean, int n){
        List<StepBean> rootList = new ArrayList<>();
        if (n == 0){
            rootList.add(rootBean);
            return rootList;
        }
        if (rootBean.bushu == n - 1){
            return rootBean.childStepList;
        }
        List<StepBean> childStepList = rootBean.childStepList;
        List<StepBean> stepBeanList = new ArrayList<>();
        for (StepBean stepBean : childStepList){
            stepBeanList.addAll(getStepN(stepBean, n));
        }
        return stepBeanList;
    }
}
