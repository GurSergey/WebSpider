package com.company;

import javax.swing.*;
import java.awt.*;


public class MainForm extends JDialog {
    private JTextField addressField;
    private JPanel panel1;
    private JTextField countField;
    private JButton startButton;
    private JList linkList;
    private JList errorLinkList;
    private JButton stopButton;
    private JProgressBar progressBar;
    private JTextField portField;
    private JLabel errorLabel;
    private JLabel okLabel;
    private JLabel errorLabelList;
    private JCheckBox isSecure;
    private View view;

    MainForm(View view )
    {
        this.view = view;

        this.setSize(new Dimension(700,800));
        setContentPane(panel1);
        setVisible(true);
        stopButton.addActionListener(e->{
            view.stopThread();
        });
        startButton.addActionListener((e -> {
        view.clickButton(addressField.getText(),portField.getText(), countField.getText(),isSecure.isSelected());
        startButton.setEnabled(false);}));
    }
    void errorLinkList(String[] arr)
    {
        //System.out.
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i =0; i < arr.length; i++)
        {
            listModel.add(i, arr[i]);
        }
        errorLabelList.setText("Битые ссылки: "+arr.length);
        errorLinkList.setModel(listModel);
    }
    void LinkList(String[] arr, int count)
    {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i =0; i < arr.length; i++)
        {
            listModel.add(i, arr[i]);
        }

        linkList.setModel(listModel);
        progressBar.setMaximum(0);
        progressBar.setMaximum(count);
        okLabel.setText("Обработанные ссылки: "+arr.length);
        progressBar.setValue(listModel.size());
    }
    void endSearch()
    {
        startButton.setEnabled(true);
        errorLabel.setText("Поиск завершен");
    }
    void SetError(String error)
    {
        System.out.println(error);
        startButton.setEnabled(true);
        errorLabel.setText(error);
    }

}
