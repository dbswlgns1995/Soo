package com.jihoonyoon.soo.notepad.events;

import com.jihoonyoon.soo.notepad.models.Folder;

/**
 * Created by MohMah on 8/19/2016.
 */
public class FolderDeletedEvent{
	Folder folder;

	public FolderDeletedEvent(Folder folder){
		this.folder = folder;
	}

	public Folder getFolder(){
		return folder;
	}
}
