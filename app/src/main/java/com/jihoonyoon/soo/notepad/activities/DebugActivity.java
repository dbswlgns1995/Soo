package com.jihoonyoon.soo.notepad.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jihoonyoon.soo.R;
import com.jihoonyoon.soo.notepad.database.AppDatabase;
import com.jihoonyoon.soo.notepad.database.FoldersDAO;
import com.jihoonyoon.soo.notepad.models.Folder;
import com.jihoonyoon.soo.notepad.models.FolderNoteRelation;
import com.jihoonyoon.soo.notepad.models.Note;
import com.jihoonyoon.soo.notepad.models.Note_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MohMah on 8/20/2016.
 */
public class DebugActivity extends AppCompatActivity{
	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.assign_to_folders) void assignToFolders(){
		Note note = SQLite.select().from(Note.class).orderBy(Note_Table.createdAt, false).querySingle();
		List<Folder> folders = FoldersDAO.getLatestFolders();
		for (Folder folder : folders){
			FolderNoteRelation fnr = new FolderNoteRelation();
			fnr.setFolder(folder);
			fnr.setNote(note);
			fnr.save();
		}
	}

	@OnClick(R.id.create_5_notes) void create5Notes(){
		AppDatabase.Utils.createSomeNotes(5);
	}
}
