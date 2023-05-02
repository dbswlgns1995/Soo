package com.jihoonyoon.soo.notepad.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jihoonyoon.soo.R;
import com.jihoonyoon.soo.notepad.activities.note.NoteActivityIntentBuilder;
import com.jihoonyoon.soo.notepad.models.Folder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MohMah on 8/21/2016.
 */
public class NoteListFragment extends Fragment{
	public static final String FOLDER = "FOLDER";

	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	@BindView(R.id.new_note) FloatingActionButton mNewNoteFAB;
	@BindView(R.id.zero_notes_view) View zeroNotesView;
	@BindView(R.id.backButton) ImageButton imageButton;
	Adapter adapter;
	Folder folder;

	@Nullable @Override public View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
	){
		View view = inflater.inflate(R.layout.fragment_note_list, container, false);
		ButterKnife.bind(this, view);
		folder = getArguments() == null ? null : (Folder) getArguments().getParcelable(NoteListFragment.FOLDER);
		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		slm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
		mRecyclerView.setLayoutManager(slm);
		adapter = new Adapter(zeroNotesView, folder);
		mRecyclerView.setAdapter(adapter);
		adapter.loadFromDatabase();
	}

	@OnClick(R.id.new_note) void clickNewNoteButton(){
		Intent intent = new NoteActivityIntentBuilder().build(getContext());
		this.startActivity(intent);
	}

	@OnClick(R.id.backButton) void clickBackButton(){
		getActivity().finish();
	}

	@Override public void onStart(){
		super.onStart();
		adapter.registerEventBus();
	}

	@Override public void onStop(){
		super.onStop();
		adapter.unregisterEventBus();
	}
}
