package com.test;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lipengd@zbj.com on 2017/9/11.
 */
public class KillNetKeeper{
    private static final String COMMOND_PREFIX = "taskkill -f -im ";
    private static final String FILE_PATH = "C:\\Program Files (x86)\\NetKeeper\\run\\C6516439-5E45-4ABF-9D1A-87556AC3184F";
    private static final String OUTPUT_FILE_PATH = "C:\\Users\\Administrator.O3N4E19WTVWX6C1\\Desktop\\useless\\killnetkeeper.bat";
    private static final String WIFI_EXE = "D:\\Program Files\\wifi\\kwifi\\mywifi";
    private static final Long TIME = 10 * 1000l;  ///10秒钟
    //获取进程信息
    public List<String> Tasklist()
    {
        List<String> list=new ArrayList<String>();
        try
        {
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader= new BufferedReader(new InputStreamReader(process.getInputStream()));
            while(reader.read() != -1){
                String p=reader.readLine();
                System.out.println(p);
                list.add(p);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return list;
    }


    public Set<String> rtTasklist(List<String> list)
    {
        Set<String> array = new HashSet<>();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).indexOf(" ") > 0){
                array.add(list.get(i).substring(0, list.get(i).indexOf(" ")));
            }
        }
        return array;
    }

    //获取进程名
    public static String getName(String fileName){
        File file = new File("C:\\Program Files (x86)\\NetKeeper\\run\\C6516439-5E45-4ABF-9D1A-87556AC3184F");
        if(file.exists()){
            File[] files = file.listFiles();
            for(File file1 : files){
                if(file1.isFile()){
                    String name = file1.getName();
                    if(match(name)){
                        return name ;
                    }
                }
            }
        }
        return null;
    }

    //正则匹配
    public static boolean match(String name){
        Pattern pattern = Pattern.compile("\\w{7,8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    //输出到文件
    public static void output(String content) throws IOException {
        File file = new File(OUTPUT_FILE_PATH);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    //结束进程
    public static void main(String[] args) throws IOException, InterruptedException {
        String processName = getName(FILE_PATH);
        if(processName != null){
            String commond = COMMOND_PREFIX + processName;
            output(commond);
            Thread.sleep(TIME);
            Runtime.getRuntime().exec(WIFI_EXE);
        }

    }
}