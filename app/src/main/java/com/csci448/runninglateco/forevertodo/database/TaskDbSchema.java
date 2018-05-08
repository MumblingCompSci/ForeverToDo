package com.csci448.runninglateco.forevertodo.database;

/**
 * Created by tkarol on 5/8/18.
 */

public class TaskDbSchema {
    public static final class TaskTable{
        public static final String NAME = "tasks";
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DUEDATE = "duedate";
            public static final String COMPLETEDATE = "completedate";
        }
    }
}
