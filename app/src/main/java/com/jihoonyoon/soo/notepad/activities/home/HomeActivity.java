package com.jihoonyoon.soo.notepad.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jihoonyoon.soo.R;
import com.jihoonyoon.soo.notepad.models.Folder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MohMah on 8/17/2016.
 */
public class HomeActivity extends AppCompatActivity{
	private static final String TAG = "HomeActivity";
	private static final int ALL_NOTES_MENU_ID = -1;
	private static final int EDIT_FOLDERS_MENU_ID = -2;
	private static final int SAVE_DATABASE_MENU_ID = -3;
	private static final int IMPORT_DATABASE_MENU_ID = -4;

	@BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	List<Folder> latestFolders;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		mDrawerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
			@Override public void onGlobalLayout(){
				mDrawerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				setFragment(null);
			}
		});
	}

	@Override protected void onStart(){
		super.onStart();
	}

	public void setFragment(Folder folder){
		// Create a new fragment and specify the fragment to show based on nav item clicked
		Fragment fragment = new NoteListFragment();
		if (folder != null){
			Bundle bundle = new Bundle();
			bundle.putParcelable(NoteListFragment.FOLDER, folder);
			fragment.setArguments(bundle);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == BackupRestoreDelegate.PICK_RESTORE_FILE_REQUEST_CODE){
//			backupRestoreDelegate.handleFilePickedWithFilePicker(resultCode, data);
//		}
	}
}
