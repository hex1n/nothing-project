package com.hexin.demo.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class GenerateShellScript {
 
    private static void createShell(String path, String... strings) throws Exception {
        if (strings == null) {
            System.out.println("strings is null");
            return;
        }
        File sh = new File(path);
        boolean createResult = sh.createNewFile();
        if (!createResult){
            System.out.println("create failed");
        }
        boolean executeResult = sh.setExecutable(true);
        if (!executeResult){
            System.out.println("execute failed");
        }
        FileWriter fw = new FileWriter(sh);
        BufferedWriter bf = new BufferedWriter(fw);
 
        for (int i = 0; i < strings.length; i++) {
            bf.write(strings[i]);
            if (i < strings.length - 1) {
                bf.newLine();
            }
        }
        bf.flush();
        bf.close();
    }
 
    private static void checkAndCreate(String jobName){
        File file = new File("D:\\test\\" + jobName + ".sh");
        if (!file.exists()){
            String command1 = "#!/bin/bash";
            String command2 = "source /opt/xxx/spoc.cfg";
            String command3 = "/opt/venv/bin/python /opt/xxx/xxx/jobs/xxxx.py" +
                    " " + "'" + jobName + "'";
            String command4 = "echo \"End!\"";
            String path = "D:\\test\\" + jobName + ".sh";
            String[] strings = {command1, command2, command3, command4};
            try {
                createShell(path, strings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("File exist");
        }
 
    }
 
    public static void main(String[] args) {
        String jobName = "Exception Sites for xxxx";
        checkAndCreate(jobName);
    }
}