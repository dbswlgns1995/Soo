package com.jihoonyoon.soo.notepad.events;

import com.jihoonyoon.soo.notepad.database.NotesDAO;
import com.jihoonyoon.soo.notepad.models.Note;

/**
 * Created by MohMah on 8/22/2016.
 */
public class NoteFoldersUpdatedEvent{

	int noteId;

	public NoteFoldersUpdatedEvent(int noteId){
		this.noteId = noteId;
	}

	public int getNoteId(){
		return noteId;
	}

	public Note getNote(){
		return NotesDAO.getNote(noteId);
	}
}
