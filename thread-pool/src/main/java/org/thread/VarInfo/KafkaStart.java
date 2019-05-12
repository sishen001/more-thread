package org.thread.VarInfo;

import util.TlUtil;

public class KafkaStart {
  private static volatile boolean isStart = false;

  public  synchronized   void start(){
    if (isStart) {
      throw new RuntimeException();
    }
    System.out.println("初始完成");
    isStart=true;
  }

  public static void main(String[] args) {
    KafkaStart xxxStart = new KafkaStart();
    TlUtil.timeTasks(10, 1, new Runnable() {
      @Override
      public void run() {
//        KafkaStart xxxStart=new KafkaStart();  //测试下
        xxxStart.start();
      }
    });
  }

  }
