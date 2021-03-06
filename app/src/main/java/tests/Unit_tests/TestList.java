
package tests.Unit_tests;

import java.util.ArrayList;
import android.app.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslayer.CheckListWrapper;
import businesslayer.ChecklistBL;
import businesslayer.TextNoteBL;
import businesslayer.TextNoteWrapper;
import domain.Note;
import persistancelayer.TextNotePL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by kushal on 2E016-02-21.
 */
public class TestList extends Activity {

    ArrayList<String>task= new ArrayList<String>();
    TextNotePL tObj;
    Note note, note2, note4;
    TextNoteWrapper tWrapper;
    TextNoteBL blObj;
    ChecklistBL cLobj;
    CheckListWrapper ClWrapper;

    @Before
    protected void setUp() {
        tObj = new TextNotePL();
        blObj = new TextNoteBL(this);
        cLobj= new ChecklistBL(this);
        tWrapper= new TextNoteWrapper("id","title","contents","tags","time","filepath",false);
        ClWrapper=new CheckListWrapper("id","title","contents","tags","time","filePaths",false);
        tObj.clearNoteList();
    }

    @Test
    public void testcreate() throws Exception {
        blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("create method:list is empty", !blObj.getTextNoteObj().getNoteList().isEmpty());
        blObj.create("April 27/03/2017 12:08PM", "notename2", "Itr_2", "notetextAssign2", "root/user/gallery2", "textNote2");
        blObj.create("May 26/03/2016 12:09PM", "notename3", "Itr.2", "notetextAssign3", "root/user/gallery3", "textNote3");
        assertTrue("the size is not right", blObj.getTextNoteObj().getNoteList().size() == 3);
    }

    @Test
    public void testgetTextNoteObj() throws Exception {
        blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("Text note object is null", blObj.getTextNoteObj() != null);
    }

    @Test
    public void testEmptyNote()
    {
        blObj.create("","","","","","");
        assertTrue("Empty Note not created successfully!",!blObj.getTextNoteObj().getNoteList().isEmpty());
    }

    @Test
    public void testUpdate()
    {
        blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
        boolean result=blObj.updateData("1","Mar 26/03/2016 12:12PM", "updated notename1", "Itr2", "updated notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("data was not updated!",!result);
    }
    @Test
    public void testIdUpdate()
    {
        blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
        boolean result=blObj.updateData("61","Mar 26/03/2016 12:12PM", "updated notename1", "Itr2", "updated notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("this should have been updated", result);
    }

    @Test
    public void testNoteWrapper()
    {
        String contents="contents";
        assertTrue("contents are not right",contents.compareTo(tWrapper.getContents())!=0);
        String title="title";
        assertTrue("title is not right",title.compareTo(tWrapper.getNoteTitle())!=0);
        String id="id";
        assertTrue("id is not right",id.compareTo(tWrapper.getNoteID())!=0);
        String filepath="filepath";
        assertTrue("not right filepath",filepath.compareTo(tWrapper.getFilePaths())!=0);
        String tag="tags";
        assertTrue("not the right tags", tag.compareTo(tWrapper.getTag()) != 0);
        String time="time";
        assertTrue("not the right time", time.compareTo(tWrapper.getLastEditedTime())!=0);

    }

    @Test
    public void testCheckListCreation()
    {
        boolean result=cLobj.create("time","name", task,"type");
        assertTrue("checklist not created successfully",!result);
        //empty case
        boolean result2= cLobj.create("","",null,"");
        assertTrue("empty should have been created",!result2);
    }

    @Test
    public void testChecklistWrapper()
    {
        String contents="contents";
        assertTrue("contents are not right",contents.compareTo(ClWrapper.getContents())!=0);
        String title="title";
        assertTrue("title is not right",title.compareTo(ClWrapper.getNoteTitle())!=0);
        String id="id";
        assertTrue("id is not right",id.compareTo(ClWrapper.getNoteID())!=0);
        String filepath="filepath";
        assertTrue("not right filepath",filepath.compareTo(ClWrapper.getFilePaths())!=0);
        String tag="tags";
        assertTrue("not the right tags", tag.compareTo(ClWrapper.getTag()) != 0);
        String time="time";
        assertTrue("not the right time", time.compareTo(ClWrapper.getLastEditedTime())!=0);
    }

    @Test
    public void testaddData() {
        tObj.insertData("Mar 14/2016, 12:08PM ", "Comp 3350", "ITR2", "first note", "", "textNote");
        tObj.insertData("Mar 14/2016, 12:38PM ", "Comp 3250", "ITR3", "first sec note", "", "textNote");
        tObj.insertData("Mar 14/2016, 12:48PM ", "Comp 3150", "ITR4", "first third note", "", "textNote");
        assertTrue("Your list is still empty", !tObj.getNoteList().isEmpty());
        assertTrue("same note doesn't exists", tObj.containsNote(note2));
        assertTrue("same note doesn't exists", !tObj.containsNote(note4));
        assertTrue("this is not the right size", tObj.getNoteList().size() == 3);
    }

    @After
    protected void tearDown() {
        tObj = null;
        blObj = null;
        note = null;
        tWrapper=null;
        ClWrapper=null;
    }


}