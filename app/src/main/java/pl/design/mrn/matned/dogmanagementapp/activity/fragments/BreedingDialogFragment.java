package pl.design.mrn.matned.dogmanagementapp.activity.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Breeding;

import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_INFO;

public class BreedingDialogFragment extends DialogFragment {


    private String usageFlag;
    private Breeding breeding;
    private Button ok;
    private Button cancel;

    private TextView breedingName;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView description;



    public BreedingDialogFragment(String usageFlag, Breeding breeding) {
        this.usageFlag = usageFlag;
        this.breeding = breeding;
    }

    @SuppressLint({"ResourceType", "InflateParams"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.breeding_dialog, null);
        builder.setView(root);
        initFields(root);
        setOnUsageView(root);
        getCancelButton(root);
        return builder.create();
    }

    private void initFields(View root) {
        breedingName = root.findViewById(R.id.breedingName);
        address = root.findViewById(R.id.breedingAddress);
        phone = root.findViewById(R.id.breedingPhone);
        email = root.findViewById(R.id.breedingEmail);
        description = root.findViewById(R.id.breedingDescription);
    }

    private void setOnUsageView(View root) {
        ok = root.findViewById(R.id.okBreedingDialog);
        switch (usageFlag) {
            case USAGE_ADD:
            case USAGE_EDIT:
                getCancelButton(root);
                ok.setOnClickListener(v -> {
                    breeding.setName(breedingName.getText().toString());
                    breeding.setAddress(address.getText().toString());
                    breeding.setPhone(phone.getText().toString());
                    breeding.setEmail(email.getText().toString());
                    breeding.setDescription(description.getText().toString());
                    Objects.requireNonNull(BreedingDialogFragment.this.getDialog()).hide();
                });
                break;
            case USAGE_INFO:
                cancel = root.findViewById(R.id.cancelBreedingDialog);
                cancel.setVisibility(View.GONE);
                ok.setOnClickListener(v -> {
                    Objects.requireNonNull(BreedingDialogFragment.this.getDialog()).cancel();
                });
                break;
        }
    }

    private void getCancelButton(View root) {
        cancel = root.findViewById(R.id.cancelBreedingDialog);
        cancel.setOnClickListener(b-> Objects.requireNonNull(BreedingDialogFragment.this.getDialog()).cancel());
    }
}
