package com.bins.springboot.dubbo.consumer.Test;

/**
 * @author hex1n
 * @date 2021/1/13 9:41
 * @description
 */
public class StringTest {

    public static void main(String[] args) {
       /* HashMap<String, String> map = Maps.newHashMap();
        map.put("A","B");*/
//        HashSet<String> set = Sets.newHashSet();
//        set.add("A");
//        set.add("A");
      /*  ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId()+ threadInfo.getThreadName());
        }*/
//        System.out.println(set);
        char c[]={'h'};
        for (int i = 0;;) {
            System.out.println("55555");
        }

//        System.out.println("h".equals(c));
    }


    public static synchronized void syncMethod(){
        System.out.println("hhhhhhh");
    }
}
