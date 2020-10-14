package ru.alextsy.springvaadinapp.service;

import com.vaadin.flow.component.notification.Notification;

import java.io.*;
import java.util.ArrayList;

public class NumberTaskService {

    private int number;

    public NumberTaskService() {
    }

    public String calculate (int number){
            String str = number + "";
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < str.length(); i++){
                if(str.charAt(i) != '0'){
                    if(sb.length() > 0){
                        sb.append(" + ");
                    }
                    sb.append(str.charAt(i));
                    for(int j = i + 1; j < str.length(); j++){
                        sb.append("0");
                    }
                }
            }
            return sb.toString();
    }



    public void save(String number, Integer param){
        try{
            File file = new File("file2.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            pw.println(number);
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
            BufferedReader br = new BufferedReader(new FileReader("file2.txt"));
            String strng;

            ArrayList<String> list = new ArrayList<>();
            while ((strng = br.readLine()) != null) {
                if (!strng.isEmpty()) {
                    list.add(strng);
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
