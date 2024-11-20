// Generated by view binder compiler. Do not edit!
package com.example.encryptednotes.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.encryptednotes.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LayoutNewMemoBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button confirmButton;

  @NonNull
  public final EditText editText1;

  @NonNull
  public final EditText editText2;

  @NonNull
  public final EditText editText3;

  @NonNull
  public final LinearLayout newMemoLayout;

  private LayoutNewMemoBinding(@NonNull LinearLayout rootView, @NonNull Button confirmButton,
      @NonNull EditText editText1, @NonNull EditText editText2, @NonNull EditText editText3,
      @NonNull LinearLayout newMemoLayout) {
    this.rootView = rootView;
    this.confirmButton = confirmButton;
    this.editText1 = editText1;
    this.editText2 = editText2;
    this.editText3 = editText3;
    this.newMemoLayout = newMemoLayout;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LayoutNewMemoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayoutNewMemoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.layout_new_memo, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayoutNewMemoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.confirmButton;
      Button confirmButton = ViewBindings.findChildViewById(rootView, id);
      if (confirmButton == null) {
        break missingId;
      }

      id = R.id.editText1;
      EditText editText1 = ViewBindings.findChildViewById(rootView, id);
      if (editText1 == null) {
        break missingId;
      }

      id = R.id.editText2;
      EditText editText2 = ViewBindings.findChildViewById(rootView, id);
      if (editText2 == null) {
        break missingId;
      }

      id = R.id.editText3;
      EditText editText3 = ViewBindings.findChildViewById(rootView, id);
      if (editText3 == null) {
        break missingId;
      }

      LinearLayout newMemoLayout = (LinearLayout) rootView;

      return new LayoutNewMemoBinding((LinearLayout) rootView, confirmButton, editText1, editText2,
          editText3, newMemoLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}