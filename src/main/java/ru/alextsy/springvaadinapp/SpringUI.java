package ru.alextsy.springvaadinapp;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.alextsy.springvaadinapp.service.ArrayTaskService;
import ru.alextsy.springvaadinapp.service.NumberTaskService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("")
public class SpringUI extends AppLayout{

    VerticalLayout mainLayout;
    HorizontalLayout horizontalBtnLayout;
    TextField conditionOne;
    TextField conditionTwo;
    TextField conditionNum;
    TextField answerFld;
    ComboBox comboBoxTask;
    Button culcBtn;
    Button saveFileBtn;
    Button downloadFileBtn;


    public SpringUI() {
        buildMainView();
        setuplisteners();
    }

    private void buildMainView(){
        mainLayout = new VerticalLayout();
        conditionOne = new TextField("Введите первое условие");
        conditionOne.setPlaceholder("пример: а а а");
        conditionTwo = new TextField("Введите второе условие");
        conditionTwo.setPlaceholder("пример: а а");
        conditionNum = new TextField("Введите число");
        conditionNum.setPlaceholder("пример: 234");

        comboBoxTask = new ComboBox("Выберите номер задачи");
        comboBoxTask.setPlaceholder("выберите задачу");
        comboBoxTask.isRequired();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        comboBoxTask.setItems(list);
        answerFld = new TextField("Ответ");
        culcBtn = new Button("Посчитать");
        saveFileBtn = new Button("Сохранить");
        downloadFileBtn = new Button("Загрузить");
        horizontalBtnLayout = new HorizontalLayout();
        horizontalBtnLayout.add(culcBtn, saveFileBtn, downloadFileBtn);
        mainLayout.add(conditionOne, conditionTwo, conditionNum, comboBoxTask, horizontalBtnLayout, answerFld);
        addToNavbar(new H3("Задачи"));
        setContent(mainLayout);
    }

    private void setuplisteners(){
        culcBtn.addClickListener(e -> {
            String[] a1 = conditionOne.getValue().split(" ");
            String[] a2 = conditionTwo.getValue().split(" ");
            Integer param = (Integer) comboBoxTask.getValue();
            if(param == null || param == 1) {
                ArrayTaskService arrayTaskService = new ArrayTaskService();
                String[] s = arrayTaskService.calculate(a1, a2);
                answerFld.setValue(Arrays.toString(s));
            } else {
                NumberTaskService numberTaskService = new NumberTaskService();
                String s = numberTaskService.calculate(Integer.parseInt(conditionNum.getValue()));
                answerFld.setValue(s);
            }
        });

        saveFileBtn.addClickListener(e ->{
            String[] a1 = conditionOne.getValue().split(" ");
            String[] a2 = conditionTwo.getValue().split(" ");
            String number = conditionNum.getValue();
            Integer param = (Integer) comboBoxTask.getValue();
            if(param == null || param == 1) {
                ArrayTaskService arrayTaskService = new ArrayTaskService();
                arrayTaskService.save(a1, a2, param);
            }else{
                NumberTaskService numberTaskService = new NumberTaskService();
                numberTaskService.save(number, param);
            }
        });

        downloadFileBtn.addClickListener(e -> {
            Integer param = (Integer) comboBoxTask.getValue();
            if(param == null || param == 1) {
                ArrayTaskService arrayTaskService = new ArrayTaskService();
                String[] res = arrayTaskService.download();
                conditionOne.setValue(res[0]);
                conditionTwo.setValue(res[1]);
                comboBoxTask.setValue(res[2]);
            }else{
                NumberTaskService numberTaskService = new NumberTaskService();
                String[] res = numberTaskService.download();
                conditionNum.setValue(res[0]);
                comboBoxTask.setValue(res[1]);
            }
        });
    }
}
