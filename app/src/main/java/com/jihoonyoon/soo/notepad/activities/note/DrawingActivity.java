package com.jihoonyoon.soo.notepad.activities.note;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.jihoonyoon.soo.R;
import com.jihoonyoon.soo.notepad.App;
import com.jihoonyoon.soo.notepad.database.NotesDAO;
import com.jihoonyoon.soo.notepad.jobs.SaveDrawingJob;
import com.jihoonyoon.soo.notepad.models.Note;
import com.jihoonyoon.soo.notepad.utils.Utils;
import com.jihoonyoon.soo.notepad.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder
public class DrawingActivity extends AppCompatActivity{
	private static final String TAG = "DrawingActivity";
	@Extra
	Integer noteId;
	Note note;

	private boolean hasDrawnSomething = false;

	@BindView(R.id.drawing_pad) SignaturePad drawingPad;
	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.redButton) ImageButton redButton;
	@BindView(R.id.whiteButton) ImageButton whiteButton;
	@BindView(R.id.blackButton) ImageButton blackButton;
	@BindView(R.id.yellowButton) ImageButton yellowButton;
	@BindView(R.id.blueButton) ImageButton blueButton;
	@BindView(R.id.refreshButton) ImageButton refreshButton;
	@BindView(R.id.penButton) ImageButton penButton;
	@BindView(R.id.stateTextView) TextView stateTextView;

	private Drawable checkedIcon;
	private int checkedItemId = -1;

	private Float penSize = 1F;
	private PopupMenu popupMenu;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		DrawingActivityIntentBuilder.inject(getIntent(), this);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationIcon(ViewUtils.tintDrawable(R.drawable.ic_arrow_back_white_24dp, R.color.md_blue_grey_400));
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				onBackPressed();
			}
		});

		note = NotesDAO.getNote(noteId);
		Log.e(TAG, "onCreate: noteId= " + noteId + ", note= " + note);
		drawingPad.setOnSignedListener(new SignaturePad.OnSignedListener(){
			@Override public void onStartSigning(){
			}

			@Override public void onSigned(){
				hasDrawnSomething = true;
			}

			@Override public void onClear(){
			}
		});

		refreshButton.setOnClickListener(view -> {
			drawingPad.clear();
			hasDrawnSomething = false;
		});

		setColor();

		checkedIcon = getResources().getDrawable(R.drawable.ic_baseline_check_24);

		drawingPad.setMinWidth(1F);
		drawingPad.setMaxWidth(1F);

		setPopupMenu();

		penButton.setOnClickListener(view -> {
			popupMenu.show();
		});
	}

	private void setPopupMenu(){
		// 팝업 메뉴 생성
		popupMenu = new PopupMenu(this, penButton, Gravity.END);
		popupMenu.getMenuInflater().inflate(R.menu.pen_menu, popupMenu.getMenu());

		// 메뉴 아이템 클릭 이벤트 리스너 등록
		popupMenu.setOnMenuItemClickListener(item -> {
			// 클릭된 아이템의 ID 확인
			int itemId = item.getItemId();
			penSize = Integer.parseInt(item.getTitle().toString()) * 1F;
			stateTextView.setText(item.getTitle().toString());
			drawingPad.setMinWidth(penSize);
			drawingPad.setMaxWidth(penSize);
			return true;
		});
	}


	private void setColor(){

		redButton.setOnClickListener(view -> {
			drawingPad.setPenColor(Color.RED);
			stateTextView.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
		});

		whiteButton.setOnClickListener(view -> {
			drawingPad.setPenColor(Color.WHITE);
			stateTextView.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
		});

		blackButton.setOnClickListener(view -> {
			drawingPad.setPenColor(Color.BLACK);
			stateTextView.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
		});

		blueButton.setOnClickListener(view -> {
			drawingPad.setPenColor(Color.BLUE);
			stateTextView.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
		});

		yellowButton.setOnClickListener(view -> {
			drawingPad.setPenColor(Color.YELLOW);
			stateTextView.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
		});
	}

	@Override protected void onStop(){
		super.onStop();
		if (hasDrawnSomething)
			App.JOB_MANAGER.addJobInBackground(new SaveDrawingJob(drawingPad, note.getId()));
		else
			App.JOB_MANAGER.addJobInBackground(new SaveDrawingJob(null, note.getId()));
	}

	@Override protected void onStart(){
		super.onStart();
		try{
			drawingPad.setSignatureBitmap(Utils.getImage(note.getDrawing().getBlob()));
		}catch (NullPointerException e){
			Log.i(TAG, "Empty Drawing onStart: ", e);
		}
	}
}