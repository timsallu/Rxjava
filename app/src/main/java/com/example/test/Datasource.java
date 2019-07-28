package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class Datasource  {

    private static Datasource ds=null;

    private Datasource()
    {

    }

    private static synchronized Datasource getInstance()
    {
        if(ds==null)
        {
            ds=new Datasource();
        }
        return ds;
    }

    public static List<Task> CreatList()
    {
        List<Task> taskList =new ArrayList<>();

        taskList.add(new Task("Salman is Best 1",true,3));
        taskList.add(new Task("Salman is Best 2",false,2));
        taskList.add(new Task("Salman is Best 3",true,1));
        taskList.add(new Task("Salman is Best 4",false,0));
        taskList.add(new Task("Salman is Best 5",true,5));
        return taskList;
    }
}
