package mesa.raydel.com.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class InputDialog extends DialogFragment {

        private OnInputEnteredListener mListener;

        public InputDialog() {
            // Required empty public constructor
        }

        public static InputDialog newInstance() {
            InputDialog fragment = new InputDialog();
            return fragment;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.fragment_input_dialog, null);

            final EditText name = (EditText) view.findViewById(R.id.name);
            // set dialog icon
            builder.setIcon(android.R.drawable.ic_dialog_info)
                    // set Dialog Title
                    .setTitle("Input Dialog")
                    // Set Dialog Message
                    .setView(view)
                    // positive button
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (mListener != null) {
                                mListener.greeting(name.getText().toString());
                            }
                            dialog.dismiss();
                        }
                    })
                    // negative button
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            if (context instanceof OnInputEnteredListener) {
                mListener = (OnInputEnteredListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnInputEnteredListener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }


        public interface OnInputEnteredListener {
            void greeting(String name);
        }
    }
