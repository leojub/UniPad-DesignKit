package com.kimjisub.unipad.designkit;

import android.widget.CheckBox;

import java.util.ArrayList;

public class SyncCheckBox {
	
	private boolean isChecked = false;
	private boolean isLocked = false;
	
	// =========================================================================================
	
	ArrayList<CheckBox> checkBoxes = new ArrayList();
	
	public SyncCheckBox(CheckBox... cbs) {
		for (CheckBox cb : cbs)
			addCheckBox(cb);
	}
	
	public void addCheckBox(CheckBox checkBox) {
		checkBox.setOnCheckedChangeListener((compoundButton, b) -> setChecked(b));
		checkBox.setOnLongClickListener(view -> {
			longClick();
			return false;
		});
		
		checkBoxes.add(checkBox);
	}
	
	
	// ========================================================================================= setChecked
	
	
	public void setChecked(boolean b) {
		if (!isLocked)
			forceSetChecked(b);
		else {
			for (CheckBox checkBox : checkBoxes) {
				if (checkBox.isChecked() != isChecked) {
					checkBox.setOnCheckedChangeListener(null);
					checkBox.setChecked(isChecked);
					checkBox.setOnCheckedChangeListener((compoundButton, b2) -> setChecked(b2));
				}
			}
		}
	}
	
	public void forceSetChecked(boolean b) {
		isChecked = b;
		
		for (CheckBox checkBox : checkBoxes) {
			if (checkBox.isChecked() != b) {
				checkBox.setOnCheckedChangeListener(null);
				checkBox.setChecked(b);
				checkBox.setOnCheckedChangeListener((compoundButton, b2) -> setChecked(b2));
			}
		}
		
		onCheckedChange(b);
	}
	// ========================================================================================= checkedChange
	
	OnCheckedChange onCheckedChange;
	
	public interface OnCheckedChange {
		void onCheckedChange(boolean b);
	}
	
	public void setOnCheckedChange(OnCheckedChange listener) {
		onCheckedChange = listener;
	}
	
	public void onCheckedChange(boolean bool) {
		if (onCheckedChange != null)
			onCheckedChange.onCheckedChange(bool);
	}
	
	// ========================================================================================= longClick
	
	OnLongClick onLongClick;
	
	public interface OnLongClick {
		void onLongClick();
	}
	
	public void setOnLongClick(OnLongClick listener) {
		onLongClick = listener;
	}
	
	public void onLongClick() {
		if (onLongClick != null)
			onLongClick.onLongClick();
	}
	
	public void longClick() {
		onLongClick();
	}
	
	
	// ========================================================================================= etc...
	
	/*public void setTextColor(int color) {
		for (CheckBox checkBox : checkBoxes)
			checkBox.setTextColor(color);
	}
	
	public void setButtonTintList(ColorStateList colorStateList) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			for (CheckBox checkBox : checkBoxes)
				checkBox.setButtonTintList(colorStateList);
		}
	}*/
	
	public void setVisibility(int visibility) {
		for (CheckBox checkBox : checkBoxes)
			checkBox.setVisibility(visibility);
	}
	
	public void toggleChecked() {
		if (!isLocked)
			forceToggleChecked();
	}
	
	public void forceToggleChecked() {
		forceSetChecked(!isChecked);
	}
	
	public void setLocked(boolean b) {
		isLocked = b;
		for (CheckBox checkBox : checkBoxes)
			checkBox.setEnabled(!b);
	}
	
	// ========================================================================================= getter
	
	public boolean isChecked() {
		return isChecked;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
}
