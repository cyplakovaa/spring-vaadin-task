package ru.alextsy.springvaadinapp.service;

import com.vaadin.flow.component.notification.Notification;

import java.io.*;
import java.util.*;

public class ArrayTaskService {

    private String[] a1;
    private String[] a2;
    private Integer param;

    public ArrayTaskService() {
    }

    public String[] calculate (String[] a1, String[] a2){
            Set<String> result = new TreeSet<>();
            String array[];

            for (String str : a1)
                for (String i : a2)
                    if (i.contains(str))
                        result.add(str);

            array = new String[result.size()];
            return result.toArray(array);
    }

    public void save(String[] a1, String[] a2, Integer param){
        try{
            File file = new File("file1.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
                PrintWriter pw = new PrintWriter(file);
                pw.println(Arrays.toString(a1));
                pw.println(Arrays.toString(a2));
                pw.println(param);
                pw.close();
                Notification saveSuccessNtf = new Notification("Успешно сохранено");
                saveSuccessNtf.setDuration(2000);
                saveSuccessNtf.open();
        } catch (
            IOException ioException) {
            ioException.printStackTrace();
            Notification saveNoSuccessNtf = new Notification("Не сохранено");
            saveNoSuccessNtf.open();
        }
    }


    public String[] download() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("file1.txt"));
            String str;

            ArrayList<String> list = new ArrayList<>();
            while ((str = br.readLine()) != null) {
                if (!str.isEmpty()) {
                    list.add(str);
                }
            }
            String[] result = list.toArray(new String[0]);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
