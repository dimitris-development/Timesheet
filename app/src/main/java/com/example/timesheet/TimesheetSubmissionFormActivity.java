package com.example.timesheet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class TimesheetSubmissionFormActivity extends AppCompatActivity {

    static final int PICK_FILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesheet_submission_form);

        final EditText Date       = findViewById(R.id.DateEdit);
        final EditText StartTime  = findViewById(R.id.StartTimeEdit);
        final EditText EndTime    = findViewById(R.id.EndTimeEdit);
        TextView SelectedFile   = findViewById(R.id.SelectedFile);
        TextView FileSize       = findViewById(R.id.FileSize);
        Button   ButtonSubmit   = findViewById(R.id.ButtonSubmit);

        SelectedFile.setVisibility(View.GONE);
        FileSize.setVisibility(View.GONE);
        ButtonSubmit.setVisibility(View.GONE);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                int CurDay      = calendar.get(Calendar.DAY_OF_MONTH);
                int CurMonth    = calendar.get(Calendar.MONTH);
                int CurYear     = calendar.get(Calendar.YEAR);

                DatePickerDialog DatePickerD = new DatePickerDialog(TimesheetSubmissionFormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datepicker, int Year, int Month, int Day) {
                                Date.setText(getString(R.string.edit_date, Day, (Month + 1), Year));
                            }
                        }, CurYear, CurMonth, CurDay
                );
                DatePickerD.show();
            }
        });

        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                int CurHour = calendar.get(Calendar.HOUR_OF_DAY);
                int CurMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog TimePickerD = new TimePickerDialog(TimesheetSubmissionFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timepicker, int Hour, int Minute) {
                                StartTime.setText(getString(R.string.edit_time, Hour, Minute));
                            }
                        }, CurHour, CurMinute, true
                );
                TimePickerD.show();
            }
        });

        EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                int CurHour = calendar.get(Calendar.HOUR_OF_DAY);
                int CurMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog TimePickerD = new TimePickerDialog(TimesheetSubmissionFormActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timepicker, int Hour, int Minute) {
                                EndTime.setText(getString(R.string.edit_time, Hour, Minute));
                            }
                        }, CurHour, CurMinute, true
                );
                TimePickerD.show();
            }
        });
    }

    public void AttachFile(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICK_FILE_RESULT_CODE); //Pick file result code
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == PICK_FILE_RESULT_CODE){
            if (resultCode == RESULT_OK){
                Uri selectedFileURI = data.getData();

                String NameOfFile = getFileName(getApplicationContext(),selectedFileURI);
                String StrSizeOfFile = getFileSize(getApplicationContext(), selectedFileURI);
                Integer SizeOfFile = Integer.valueOf(StrSizeOfFile);

                Button   ButtonAttachFile = findViewById(R.id.ButtonAttachFile);
                TextView SelectedFile     = findViewById(R.id.SelectedFile);
                TextView FileSize         = findViewById(R.id.FileSize);
                Button   ButtonSubmit     = findViewById(R.id.ButtonSubmit);

                ButtonAttachFile.setVisibility(View.GONE);

                SelectedFile.setVisibility(View.VISIBLE);
                SelectedFile.setText(getString(R.string.selected_file, NameOfFile));

                FileSize.setVisibility(View.VISIBLE);

                if (SizeOfFile >= 1024 && SizeOfFile < 1024*1024){
                    FileSize.setText(getString(R.string.file_size, (float)SizeOfFile/1024, "KB"));
                } else if (SizeOfFile >= 1024*1024 && SizeOfFile < 1024*1024*1024) {
                    FileSize.setText(getString(R.string.file_size, ((float)SizeOfFile/1024)/1024, "MB"));
                } else if (SizeOfFile >= 1024*1024*1024){
                    FileSize.setText(getString(R.string.file_size, (((float)SizeOfFile/1024)/1024)/1024, "GB"));
                } else {
                    FileSize.setText(getString(R.string.file_size, (float)SizeOfFile, "B"));
                }

                ButtonSubmit.setVisibility(View.VISIBLE);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }
        }
    }
    public static String getFileName(Context context, Uri uri) {
        String fileName = null;
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                // get file name
                fileName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            cursor.close();
        }
        return fileName;
    }

    public static String getFileSize(Context context, Uri uri) {
        String fileSize = null;
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {

                // get file size
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (!cursor.isNull(sizeIndex)) {
                    fileSize = cursor.getString(sizeIndex);
                }
            }
        } finally {
            cursor.close();
        }
        return fileSize;
    }
}


