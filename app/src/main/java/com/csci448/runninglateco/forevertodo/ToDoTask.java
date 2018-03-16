package com.csci448.runninglateco.forevertodo;

import java.util.Date;
import java.util.UUID;

/**
 * Created by quintero on 3/15/18.
 */

public class ToDoTask {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDueDate;
    private Date mCompleteDate;

    public ToDoTask() {
        mId = UUID.randomUUID();
    }

    public  UUID getId() { return mId; }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public Date getCompleteDate() {
        return mCompleteDate;
    }

    public void setCompleteDate(Date completeDate) {
        mCompleteDate = completeDate;
    }
//TODO: how do we want to store dates and times?

    //TODO: do we want to create a Builder for this class?  I don't know how practical that'd be but they look cool
    // We probably don't actually need to make this class for the Alpha release
}
