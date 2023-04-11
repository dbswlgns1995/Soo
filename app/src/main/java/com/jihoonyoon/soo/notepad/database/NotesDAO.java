package com.jihoonyoon.soo.notepad.database;


import com.jihoonyoon.soo.notepad.models.Folder;
import com.jihoonyoon.soo.notepad.models.Note;
import com.jihoonyoon.soo.notepad.models.Note_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;


/**
 * Created by MohMah on 8/21/2016.
 */
public class NotesDAO{
	public static List<Note> getLatestNotes(Folder folder){
		if (folder == null)
			return SQLite.select().from(Note.class).orderBy(Note_Table.createdAt, false).queryList();
		else
			return FolderNoteDAO.getLatestNotes(folder);
	}

	public static Note getNote(int noteId){
		return SQLite.select().from(Note.class).where(Note_Table.id.is(noteId)).querySingle();
	}
}
