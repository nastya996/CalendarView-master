package com.example.calendarview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button mChooseStartDate;
    private Button mChooseEndDate;
    private CalendarView mStartDateCalendar;
    private CalendarView mEndDateCalendar;
    private Button mBtnOK;

    private String startDateTxt;
    private String endDateTxt;

    private GregorianCalendar startDate;
    private GregorianCalendar endDate;

    public final static int START_DATE = 0;
    public final static int END_DATE = 1;

    private Locale locale = Locale.forLanguageTag("ru");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mChooseStartDate = findViewById(R.id.chooseStartDate);
        mChooseEndDate = findViewById(R.id.chooseEndDate);
        mStartDateCalendar = findViewById(R.id.startDateCalendar);
        mEndDateCalendar = findViewById(R.id.endDateCalendar);
        mBtnOK = findViewById(R.id.btnOK);

        // Скроем календари при запуске приложения
        mStartDateCalendar.setVisibility(View.GONE);
        mEndDateCalendar.setVisibility(View.GONE);

        mChooseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartDateCalendar.setVisibility(View.VISIBLE);
                mEndDateCalendar.setVisibility(View.GONE);
            }
        });

        mChooseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEndDateCalendar.setVisibility(View.VISIBLE);
                mStartDateCalendar.setVisibility(View.GONE);
            }
        });

        mStartDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                setDate(START_DATE, calendarView, y, m, d);
            }
        });

        mEndDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                setDate(END_DATE, calendarView, y, m, d);
            }
        });

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startDate.compareTo(endDate) > 0){
                    Toast.makeText(
                            MainActivity.this,
                            getString(R.string.date_error),
                            Toast.LENGTH_LONG
                    ).show();
                    mChooseStartDate.setText(getString(R.string.datetime_start));
                    mChooseEndDate.setText(getString(R.string.datetime_end));
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            "старт: " + startDateTxt + "\nокончаниe: " + endDateTxt,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    @NonNull private String getDateTxt(@NonNull GregorianCalendar gregorianCalendar) {
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH)
                + " " + gregorianCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
                + " " + gregorianCalendar.get(Calendar.YEAR);
    }

    private void setEndDate(int y, int m, int d) {
        endDate = new GregorianCalendar(y, m, d);
        endDateTxt = getDateTxt(endDate);
        String btnTxt = getString(R.string.datetime_end)
                + "\n" + endDateTxt;

        mChooseEndDate.setText(btnTxt);
    }

    private void setStartDate(int y, int m, int d) {
        startDate = new GregorianCalendar(y, m, d);
        startDateTxt = getDateTxt(startDate);
        String btnTxt = getString(R.string.datetime_start)
                + "\n" + startDateTxt;

        mChooseStartDate.setText(btnTxt);
    }

    private void setDate(int field, CalendarView calendarView, int y, int m, int d) {
        if (field == END_DATE) {
            setEndDate(y, m, d);
        } else {
            setStartDate(y, m, d);
        }
        calendarView.setVisibility(View.GONE);
    }
}
